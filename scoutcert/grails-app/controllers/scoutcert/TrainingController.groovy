package scoutcert

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.springframework.context.MessageSource
import org.springframework.web.multipart.commons.CommonsMultipartFile
import scoutcert.trainingImport.ImportError
import scoutcert.trainingImport.ImportJob
import scoutcert.trainingImport.ImportSheet
import scoutcert.trainingImport.ImportTrainingService
import org.apache.poi.ss.usermodel.*
import grails.plugins.springsecurity.SpringSecurityService

@Secured(["ROLE_LEADER"])
class TrainingController {

    ImportTrainingService importTrainingService
    SpringSecurityService springSecurityService
    MessageSource messageSource

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
            for(int r=5; r<currentSheet.lastRowNum; r++) {
                Row row = currentSheet.getRow(r)
                if(row?.getCell(0)?.stringCellValue) {
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
                        if (!optionalFields.contains(it) && !foundFields.contains(it)) {
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

