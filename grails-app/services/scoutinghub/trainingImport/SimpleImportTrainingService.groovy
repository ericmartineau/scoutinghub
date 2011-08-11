package scoutinghub.trainingImport

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import scoutinghub.Certification
import scoutinghub.CertificationCode
import scoutinghub.Leader
import scoutinghub.MyScoutingId
import java.text.DecimalFormat
import scoutinghub.LeaderCertification
import scoutinghub.LeaderCertificationEnteredType

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/10/11
 * Time: 8:50 PM
 * To change this template use File | Settings | File Templates.
 */
class SimpleImportTrainingService {

    void processSimpleImportJob(SimpleImportJob simpleImportJob) {
        //Scan column headers for certification mappings
        def firstSheet = simpleImportJob.workbook.getSheetAt(0)
        firstSheet.each {Row row ->
            simpleImportJob.totalCompleted++
            def personIdCell = row.getCell(0)
            String personId
            if(personIdCell) {
                if (personIdCell.cellType == Cell.CELL_TYPE_STRING) personId = personIdCell.stringCellValue
                if (personIdCell.cellType == Cell.CELL_TYPE_NUMERIC) personId = new DecimalFormat("#0").format(personIdCell.numericCellValue)
            }

            if (personId && personId != "person_id") {

                Leader foundLeader = MyScoutingId.findByMyScoutingIdentifier(personId)?.leader
                if (foundLeader) {
                    simpleImportJob.columnIndexToCertificationMap.each {entry ->
                        int cellId = entry.key
                        Certification certification = entry.value

                        Cell cell = row.getCell(cellId)
                        if (cell && cell.cellType == Cell.CELL_TYPE_NUMERIC) {

                            Date trainingDate = cell.dateCellValue
                            if (trainingDate) {
                                //Check to make sure there's not a newer training date on the record
                                LeaderCertification existing = foundLeader.certifications.find {return it.certification.id == certification.id}

                                if (existing && trainingDate.after(existing.dateEarned)) {
                                    existing.dateEarned = trainingDate
                                    existing.save(failOnError: true)
                                } else if (!existing) {
                                    LeaderCertification leaderCertification = new LeaderCertification()
                                    leaderCertification.leader = foundLeader
                                    leaderCertification.certification = certification
                                    leaderCertification.dateEarned = trainingDate
                                    leaderCertification.dateEntered = new Date()
                                    leaderCertification.enteredType = LeaderCertificationEnteredType.Imported
                                    leaderCertification.enteredBy = simpleImportJob.importedBy
                                    leaderCertification.save(failOnError: true)
                                    foundLeader.addToCertifications(leaderCertification)
                                    foundLeader.save(failOnError: true)

                                }
                            }
                        }
                    }
                }
            }

        }
    }

    def setupSimpleImportJob(SimpleImportJob simpleImportJob) {
        //Scan column headers for certification mappings
        def firstSheet = simpleImportJob.workbook.getSheetAt(0)
        def row = firstSheet?.getRow(0)
        if (!row) {
            throw new Exception("simpleImport.noHeaderRow")
        }
        row.each {Cell cell ->
            if (cell.cellType == Cell.CELL_TYPE_STRING) {
                Certification foundCertification = CertificationCode.findByCode(cell.stringCellValue)?.certification
                if (foundCertification) {
                    log.info "Mapped ${cell.stringCellValue} to ${foundCertification.name}"
                    simpleImportJob.columnIndexToCertificationMap[cell.columnIndex] = foundCertification
                }
            }
        }

        //Calculate total number of rows to process
        simpleImportJob.totalToProcess = firstSheet.lastRowNum
    }
}
