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

@Secured(["ROLE_LEADER"])
class TrainingController {

    ImportTrainingService importTrainingService
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
                    def importJob = new ImportJob(xlsFile, importTrainingService)
                    session.importJob = importJob
                    importJob.start()
                    redirect(action: "importStatus")
                }
            }
        }
    }


}

