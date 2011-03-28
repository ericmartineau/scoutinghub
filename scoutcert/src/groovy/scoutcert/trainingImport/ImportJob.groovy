package scoutcert.trainingImport

import org.apache.poi.ss.usermodel.Workbook

/**
 * User: eric
 * Date: 3/25/11
 * Time: 5:18 PM
 */
class ImportJob extends Thread {
    List<ImportSheet> sheetsToImport = []
    ImportSheet currentSheet

    def importTrainingService

    Workbook workbook

    ImportJob(Workbook workbook, def importTrainingService) {
        this.workbook = workbook
        this.importTrainingService = importTrainingService
    }

    @Override
    void run() {
        importTrainingService.setupImportJob(this)
        importTrainingService.processImportJob(this)
    }


}
