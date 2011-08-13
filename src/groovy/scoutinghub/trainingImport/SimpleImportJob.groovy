package scoutinghub.trainingImport

import org.apache.poi.ss.usermodel.Workbook
import scoutinghub.Leader

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/10/11
 * Time: 8:53 PM
 * To change this template use File | Settings | File Templates.
 */
class SimpleImportJob extends Thread {
    def columnIndexToCertificationMap = [:]
    int totalToProcess
    int totalCompleted
    Workbook workbook
    Leader importedBy

    def simpleImportTrainingService

    @Override
    void run() {
        simpleImportTrainingService.setupSimpleImportJob(this)
        simpleImportTrainingService.processSimpleImportJob(this)
    }


}