package scoutinghub

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.web.multipart.commons.CommonsMultipartFile
import scoutinghub.trainingImport.ImportSheet
import scoutinghub.trainingImport.ImportUnitError
import scoutinghub.trainingImport.ImportUnitsJob
import org.apache.poi.ss.usermodel.*

@Secured(["ROLE_ADMIN"])
class UnitAdminController {
    SpringSecurityService springSecurityService
    def importUnitsService

    def index = {
        forward(action: 'tools')
    }

    def tools = { }

    def reports = {}

    def importStatus = {
    }


    def importUnits = {
        if (session.importJob) {
            if (session.importJob.alive) {
                flash.message = "unitAdmin.importUnit.alreadyRunning"
                redirect(action: "importStatus")
            } else {
                session.removeAttribute "importJob"
            }

        }
    }

    def processImportUnits = {
        if (session.importJob) {
            flash.message = "unitAdmin.importUnit.alreadyRunning"
            redirect(action: "importStatus")
        } else {
            CommonsMultipartFile importFile = request.getFile("spreadsheet")

            if (importFile?.size == 0) {
                flash.message = "unitAdmin.importUnit.fileRequired"
                redirect(action: "importTraining")
            } else if (!importFile?.fileItem?.name?.endsWith(".xls")) {
                flash.message = "unitAdmin.importUnit.xlsRequired"
                redirect(action: "importUnits")
            } else {
                def validationErrors = []

                def headerMap = importUnitsService.headerMap

                Workbook xlsFile = WorkbookFactory.create(importFile.inputStream);
                //First, let's verify that each sheet has the correct values
                for (int i = 0; i < xlsFile.numberOfSheets; i++) {
                    Sheet currentSheet = xlsFile.getSheetAt(i)
                    def foundFields = []
                    Row row = importUnitsService.locateHeaderRow(currentSheet)
                    row.each {
                        Cell cell ->
                        if (cell.stringCellValue ?: "" != "") {
                            foundFields << cell.stringCellValue
                        }
                    }

                    headerMap.keySet().each {
                        String mappedValue = headerMap[it]
                        boolean foundMappedValue = foundFields.find { headerMap[it] == mappedValue}
                        if (!foundMappedValue) {
                            validationErrors << [code: "unitAdmin.importUnit.missingField", data: [xlsFile.getSheetName(i), it]]
                        }
                    }
                }

                if (validationErrors.size() > 0) {
                    flash.fileErrors = validationErrors
                    redirect(action: "importUnits")
                } else {
                    def importJob = new ImportUnitsJob(xlsFile, importUnitsService, springSecurityService.currentUser)
                    session.importJob = importJob
                    importJob.start()
                    redirect(action: "importStatus")
                }
            }
        }

    }

    def importStatusPoll = {
        Map rtn = [:]
        if (session.importJob) {
            ImportUnitsJob importJob = session.importJob
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
            ImportUnitsJob importJob = session.importJob
            int sheetIndex = Integer.parseInt(params.sheetIndex)
            ImportSheet importSheet = importJob.sheetsToImport.get(sheetIndex)
            importSheet.errors.each {
                ImportUnitError importError ->
                List errorMessages = []
                errors[importError.record] = errorMessages
                importError.messages?.each {
                    errorMessages << it
                }
            }
        }
        return [errors: errors]
    }

    def processImportUnitsOLD = {
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

}
