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

    def index = { }

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
        if(params.filterName != null) {
            session.filterName = params.filterName
        }
        Leader currentUser = springSecurityService.currentUser;
        def adminGroups
        ScoutGroup reportGroup
        if(Integer.parseInt(params.id ?: "0") > 0) {
            reportGroup = ScoutGroup.get(params.id)
            if(!reportGroup?.canBeAdministeredBy(currentUser)) {
                render(view:"/accessDenied")
            }
            adminGroups = ScoutGroup.findAllByParent(reportGroup)
        } else {
            adminGroups = LeaderGroup.findAllByLeaderAndAdmin(currentUser, true)?.collect {it.scoutGroup}
        }

        def filter

        if(session.filterName) {
            filter = scoutGroupService.filters.find{it.value.containsKey(session.filterName)}.value[session.filterName]
        }
        def reports = []
        adminGroups.each {ScoutGroup scoutGroup ->

            def results = ScoutGroup.withCriteria {
                ge('leftNode', scoutGroup.leftNode)
                le('rightNode', scoutGroup.rightNode)
                if(filter) {
                    filter.delegate = delegate
                    filter.resolveStrategy = Closure.DELEGATE_ONLY
                    filter()
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
            if(first) {
                report.pctTrained = first[0] ?: 0
                report.count = first[1] ?: 0
            }
            reports << report

        }
        return [reportGroup: reportGroup, reports: reports]
    }

    def getFilters = {
        def allFilters = scoutGroupService.filters
        def rtn = [:]
        rtn[""] = ["": message(code:"training.report.nofilter")]
        allFilters.each {
            def vals = [:]
            def key = message(code:"${it.key}.label")
            rtn[key] = vals
            it.value.each {
                def itemKey = message(code:"${it.key}.label")
                vals[it.key] = itemKey
            }
        }
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

