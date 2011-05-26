package scoutcert.trainingImport

import org.apache.poi.ss.usermodel.Workbook
import scoutcert.Leader

/**
 * User: eric
 * Date: 3/25/11
 * Time: 5:18 PM
 */
class ImportJob extends Thread {
    List<ImportSheet> sheetsToImport = []
    ImportSheet currentSheet
    Leader importedBy

    def importTrainingService

    Workbook workbook

    ImportJob(Workbook workbook, def importTrainingService, Leader importedBy) {
        this.workbook = workbook
        this.importTrainingService = importTrainingService
        this.importedBy = importedBy
    }

    @Override
    void run() {
        importTrainingService.setupImportJob(this)
        importTrainingService.processImportJob(this)
    }


}
