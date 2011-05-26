package scoutinghub.trainingImport

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Row

/**
 * User: eric
 * Date: 3/25/11
 * Time: 5:20 PM
 */
class ImportSheet {

    List<ImportError> errors = []
    String sheetName
    ImportStatus importStatus
    Sheet workbookSheet
    def headerIndex
    Row headerRow
    int pidIndex
    int totalToImport
    int totalComplete
    int totalErrors
    int index
}
