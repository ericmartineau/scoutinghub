package scoutinghub

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.springframework.context.MessageSource
import org.springframework.web.multipart.commons.CommonsMultipartFile
import scoutinghub.trainingImport.ImportError
import scoutinghub.trainingImport.ImportJob
import scoutinghub.trainingImport.ImportSheet
import scoutinghub.trainingImport.ImportTrainingService
import org.apache.poi.ss.usermodel.*
import grails.plugins.springsecurity.SpringSecurityService

@Secured(["ROLE_LEADER"])
class TrainingController {

    ImportTrainingService importTrainingService
    SpringSecurityService springSecurityService
    MessageSource messageSource
    ScoutGroupService scoutGroupService

    def index = {
        forward(action:'trainingReport')
    }

    def importTraining = {
        if (session.importJob) {
            if (session.importJob.alive) {
                flash.message = "training.importTraining.alreadyRunning"
                redirect(action: "importStatus")
            } else {
                session.removeAttribute "importJob"
            }

        }
    }

    def trainingReport = {

        if (params.filterName != null) {
            session.filterName = params.filterName
        }
        Leader currentUser = springSecurityService.currentUser;
        def adminGroups
        ScoutGroup reportGroup
        if (Integer.parseInt(params.id ?: "0") > 0) {
            reportGroup = ScoutGroup.get(params.id)
            if (!reportGroup?.canBeAdministeredBy(currentUser)) {
                render(view: "/accessDenied")
            }
            adminGroups = ScoutGroup.findAllByParent(reportGroup)
        } else {
            adminGroups = LeaderGroup.findAllByLeaderAndAdmin(currentUser, true)?.collect {it.scoutGroup}
        }

        def scoutGroupFilter
        def leaderFilter

        if (session.filterName) {
            scoutGroupFilter = scoutGroupService.filters.find {
                it.value.containsKey(session.filterName)
            }?.value?.get(session.filterName)
            leaderFilter = scoutGroupService.leaderFilters.find {
                it.value.containsKey(session.filterName)
            }?.value?.get(session.filterName)
        }

        def filteredLeaderList = LeaderGroup.withCriteria {
            eq('scoutGroup', reportGroup)
            if (leaderFilter) {
                leaderFilter.delegate = delegate
                leaderFilter.resolveStrategy = Closure.DELEGATE_ONLY
                leaderFilter();
            }
        }
        def reports = []
        adminGroups.each {ScoutGroup scoutGroup ->

            def results = ScoutGroup.withCriteria {
                ge('leftNode', scoutGroup.leftNode)
                le('rightNode', scoutGroup.rightNode)
                if (scoutGroupFilter) {
                    scoutGroupFilter.delegate = delegate
                    scoutGroupFilter.resolveStrategy = Closure.DELEGATE_ONLY
                    scoutGroupFilter()
                }
                leaderGroups {
                    projections {
                        avg('pctTrained')
                        countDistinct('id')
                    }
                }
            }

            CertificationReport report = new CertificationReport(scoutGroup: scoutGroup, reportDate: new Date())
            def first = results?.first()
            if (first) {
                report.pctTrained = first[0] ?: 0
                report.count = first[1] ?: 0
            }
            reports << report

        }

        def allFilters = [:]
        allFilters["nofilter"] = ["nofilter": message(code: "training.report.nofilter")]
        scoutGroupService.filters.each {
            def vals = [:]
            def key = message(code: "${it.key}.label")
            allFilters[key] = vals
            it.value.each {
                def itemKey = message(code: "${it.key}.label")
                vals[it.key] = itemKey
            }
        }


        return [reportGroup: reportGroup, reports: reports, filteredLeaderList: filteredLeaderList, allFilters:allFilters]
    }

    def getFilters = {

        render rtn as JSON
    }


    def importStatus = {

    }


    def importStatusPoll = {
        Map rtn = [:]
        if (session.importJob) {
            ImportJob importJob = session.importJob
            rtn.sheets = []
            importJob.sheetsToImport.each {ImportSheet importSheet ->
                rtn.sheets << [index: importSheet.index,
                        totalToImport: importSheet.totalToImport,
                        totalComplete: importSheet.totalComplete,
                        totalErrors: importSheet.totalErrors,
                        importStatus: importSheet.importStatus.name()
                ]
            }
            rtn.alive = importJob.alive
        }

        render rtn as JSON
    }

    def getImportErrors = {

        def errors = [:]
        if (session.importJob) {
            ImportJob importJob = session.importJob
            int sheetIndex = Integer.parseInt(params.sheetIndex)
            ImportSheet importSheet = importJob.sheetsToImport.get(sheetIndex)
            importSheet.errors.each {
                ImportError importError ->
                List errorMessages = []
                errors[importError.record] = errorMessages
                importError.messages?.each {
                    errorMessages << it
                }
            }
        }
        return [errors: errors]
    }

    def importUnits = {

    }

    def processImportUnits = {
        CommonsMultipartFile importFile = request.getFile("importFile")
        Workbook xlsFile = WorkbookFactory.create(importFile.inputStream);
        for (int i = 1; i < xlsFile.numberOfSheets - 1; i++) {
            Sheet currentSheet = xlsFile.getSheetAt(i)
            println xlsFile.getSheetName(i) + ":" + currentSheet.getRow(0)?.getCell(0)?.stringCellValue
            String currCharter = null
            for (int r = 5; r < currentSheet.lastRowNum; r++) {
                Row row = currentSheet.getRow(r)
                if (row?.getCell(0)?.stringCellValue) {
                    println "\t${row?.getCell(0)?.stringCellValue}"
                    currCharter = row?.getCell(0)?.stringCellValue
                }

            }
        }
    }

    def processBigFile = {
        File importFile = new File("/Users/eric/Documents/scouts/bigdaddy.xls")
        File outputFile = new File("/Users/eric/Documents/scouts/bigdaddy-converted.csv");
        PrintWriter writer = new PrintWriter(new FileWriter(outputFile))


        def fieldToTypeMap = [:]

        ["yptDate": CertificationType.YouthProtection,
                "thisIsScoutingDate": CertificationType.ThisIsScouting,
                "leaderSpecificDate": CertificationType.LeaderSpecific,
                "fastStartDate": CertificationType.FastStart
        ].each {fieldToTypeMap[it.value] = it.key}


        def codeToCertType = [:]
        CertificationCode.list().each {
            CertificationCode code ->
            if (code.certification.certificationType) {
                codeToCertType[code.code] = fieldToTypeMap[code.certification.certificationType]
            }
        }
        codeToCertType["S11"] = "outdoorSkillsDate"

        Set uniqueTrainingValues = new HashSet()
        uniqueTrainingValues.addAll(fieldToTypeMap.values())
        uniqueTrainingValues.add("outdoorSkillsDate")


        Workbook workbook = WorkbookFactory.create(new FileInputStream(importFile));
        Sheet sheet = workbook.getSheetAt(0);
        writer.print("Unit Type, Unit #, Position Code, First Name, Last Name, Phone, PID #, ");
        uniqueTrainingValues.each {trainingKey ->

            def label = importTrainingService.headerMap.find {it.value == trainingKey}.key
            writer.print("${label},");
        }
        writer.println();
        for (Row row: sheet) {
            String firstCellValue = row?.getCell(0)?.getStringCellValue();
            if (
                firstCellValue?.startsWith("Pack") ||
                        firstCellValue?.startsWith("Team") ||
                        firstCellValue?.startsWith("Crew") ||
                        firstCellValue?.startsWith("Troop")
            ) {
                String[] typeAndNumber = firstCellValue.split(" ")
                String groupId = typeAndNumber[1].replaceAll("^0*", "");
                ScoutGroup existingUnit = ScoutGroup.findByGroupIdentifierAndUnitType(groupId,
                        ScoutUnitType.valueOf(typeAndNumber[0]))
                if (!existingUnit) {
                    continue;
                }

                String positionAndCerts = row.getCell(3)?.getStringCellValue();
                String[] parts = positionAndCerts.split("\n")

                String position = parts[0]?.trim()?.replaceAll("\\.", "")
                if (!importTrainingService.leaderPositionTypeMap.containsKey(position?.replaceAll(" ", ""))) {
                    System.out.println("Unknown Position: " + position);
                    continue;
                }

                writer.print("${typeAndNumber[0]},");
                writer.print("${typeAndNumber[1]},");


                writer.print("${position},");
                String[] name = row.getCell(11)?.getStringCellValue().split(" ");
                writer.print("${name[0]},");
                writer.print("${name[name.length - 1]},");
                String phone = row.getCell(19)?.getStringCellValue();
                writer.print("${phone},");
                String personId = row.getCell(21)?.getStringCellValue();
                writer.print("${personId},");
                String[] completedCerts = parts[1]?.replaceAll("Completed:", "")?.split(", ")
                Set completed = new HashSet()
                completedCerts.each {
                    if (it?.trim()?.size() > 0) {
                        if (codeToCertType.containsKey(it?.trim())) {
                            completed.add(codeToCertType.get(it?.trim()))
                        } else {
                            println "No mapping for ${it}: ${parts[1]}"
                        }
                    }
                }
                uniqueTrainingValues.each {
                    def output = ""

                    if (completed.contains(it)) {
                        output = "01/01/2010"
                    }
                    writer.print("${output},");

                }
                writer.println();
            }
        }

        writer.flush()
        writer.close()
    }

    def processImportTraining = {
        if (session.importJob) {
            flash.message = "training.importTraining.alreadyRunning"
            redirect(action: "importStatus")
        } else {
            CommonsMultipartFile importFile = request.getFile("spreadsheet")

            if (importFile?.size == 0) {
                flash.message = "training.importTraining.fileRequired"
                redirect(action: "importTraining")
            } else if (!importFile?.fileItem?.name?.endsWith(".xls")) {
                flash.message = "training.importTraining.xlsRequired"
                redirect(action: "importTraining")
            } else {
                def validationErrors = []

                def headerMap = importTrainingService.headerMap
                def optionalFields = importTrainingService.optionalFields

                Workbook xlsFile = WorkbookFactory.create(importFile.inputStream);
                //First, let's verify that each sheet has the correct values
                for (int i = 0; i < xlsFile.numberOfSheets; i++) {
                    Sheet currentSheet = xlsFile.getSheetAt(i)
                    def foundFields = []
                    Row row = importTrainingService.locateHeaderRow(currentSheet)
                    row.each {
                        Cell cell ->
                        if (cell.stringCellValue ?: "" != "") {
                            foundFields << importTrainingService.getModifiedHeaderValue(cell.stringCellValue)
                        }
                    }

                    headerMap.keySet().each {
                        String mappedValue = headerMap[it]
                        boolean foundMappedValue = foundFields.find { headerMap[it] == mappedValue}
                        if (!optionalFields.contains(it) && !foundMappedValue) {
                            validationErrors << [code: "training.importTraining.missingField", data: [xlsFile.getSheetName(i), it]]
                        }
                    }
                }

                if (validationErrors.size() > 0) {
                    flash.fileErrors = validationErrors
                    redirect(action: "importTraining")
                } else {
                    def importJob = new ImportJob(xlsFile, importTrainingService, springSecurityService.currentUser)
                    session.importJob = importJob
                    importJob.start()
                    redirect(action: "importStatus")
                }
            }
        }
    }


}

