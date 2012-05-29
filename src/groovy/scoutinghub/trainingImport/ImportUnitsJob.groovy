package scoutinghub.trainingImport

import org.apache.poi.ss.usermodel.Workbook
import scoutinghub.Leader

/**
 * User: eric
 * Date: 3/25/11
 * Time: 5:18 PM
 */
class ImportUnitsJob extends Thread {
    List<ImportSheet> sheetsToImport = []
    ImportSheet currentSheet
    Leader importedBy

    def importUnitsService

    Workbook workbook

    ImportUnitsJob(Workbook workbook, def importUnitsService, Leader importedBy) {
        this.workbook = workbook
        this.importUnitsService = importUnitsService
        this.importedBy = importedBy
    }

    @Override
    void run() {
        importUnitsService.setupImportJob(this)
        importUnitsService.processImportJob(this)
    }


}
