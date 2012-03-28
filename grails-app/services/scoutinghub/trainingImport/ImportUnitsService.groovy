package scoutinghub.trainingImport

import grails.validation.ValidationException
import java.text.DecimalFormat
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.hibernate.Session
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.validation.ObjectError
import scoutinghub.Leader
import scoutinghub.ScoutGroup
import scoutinghub.ScoutGroupService
import scoutinghub.ScoutGroupType
import scoutinghub.ScoutUnitType

class ImportUnitsService {
    static transactional = true
    ScoutGroupService scoutGroupService
    def searchableService

    def headerMap = [
            "District Name": "districtName",
            "Unit Type": "unitType",
            "Unit No": "unitNumber",
            "Program Name": "programName",
            "Sponsoring Org": "sponseringOrg"
    ]

    /**
     * The POI libraries don't provide an easy way to retrieve the value of a cell as the underlying object it represents (like
     * and rst.getObject call).  Because of this, we'll need to know what type of data we're expecting, and this closure provides
     * the logic to retrieve the data correctly
     *
     * @todo Research whether the cellType logic changes for the newer xml-based formats
     */
    def stringClosure = {
        def rtn
        if (it?.cellType == 0) { //A number
            rtn = new DecimalFormat("############################").format(it?.numericCellValue)
        } else {
            rtn = it?.stringCellValue
        }
        rtn = rtn?.trim()
        if (rtn == "") rtn = null
        return rtn
    }

    /**
     * This determines what type of data will be returned for each column
     */
    def dataTypeMap = [
            "District Name": stringClosure,
            "Unit Type": stringClosure,
            "Unit No": stringClosure,
            "Program Name": stringClosure,
            "Sponsoring Org": stringClosure
    ]

    /**
     * Called right when the job is configured.  It does a few things:
     *
     * 1.  Figures out which sheets will be imported
     * 2.  Maps the header indexes (which column contains which data for each sheet)
     * 3.  How many rows of importable data exist on each sheet
     */
    void setupImportJob(ImportUnitsJob importJob) {
        importJob.sheetsToImport.clear()
        importJob.currentSheet = null
        for (int i = 0; i < importJob.workbook.numberOfSheets; i++) {

            Sheet currentSheet = importJob.workbook.getSheetAt(i)

            //Map header indexes
            def headerIndex = [:]
            Row headerRow = locateHeaderRow(currentSheet)
            if (!headerRow) throw new IllegalStateException("This sheet doesn't appear to contain the headers")
            //Let's verify the headers
            for (Cell cell: headerRow) {
                String cellValue = cell.getStringCellValue()
                if (cellValue != "" && headerMap.containsKey(cellValue)) {
                    headerIndex[cellValue] = cell.columnIndex
                }
            }

            //Figure out how many importable rows there are
            int orgIdIndex = headerIndex["Sponsoring Org"]
            int rowCount = 0;
            for (Row row: currentSheet) {
                if (containsDataToImport(row, orgIdIndex)) {
                    rowCount++
                }
            }

            //Schedule a sheet to be imported with the parent job
            ImportSheet sheet = new ImportSheet(
                    sheetName: importJob.workbook.getSheetName(i),
                    importStatus: ImportStatus.Waiting,
                    totalToImport: rowCount,
                    workbookSheet: currentSheet,
                    index: i,
                    headerRow: headerRow,
                    pidIndex: orgIdIndex,
                    headerIndex: headerIndex
            )
            importJob.sheetsToImport << sheet
        }
    }

    /**
     * Determines whether or not a row has importable data.  Currently, Sponsoring Org contains critical data
     * without which no import can proceed.
     *
     * @param row
     * @param pidIndex
     * @return
     */
    boolean containsDataToImport(Row row, int pidIndex) {
        Cell pidCell = row?.getCell(pidIndex)
        return pidCell && pidCell.cellType != HSSFCell.CELL_TYPE_BLANK;
    }

    /**
     * Scans down a sheet looking for a row that appears to contain header data.  Will give up after 5 rows, and determines
     * success when at least 5 of the desired headers are found.  Didn't require all headers to be there because there is
     * we want to be able to report that the sheet is missing a couple of headers (instead of failing altogether)
     * @todo This method returns null when no header row is found.  Make sure places that call it are handling appropriately
     * @param sheet
     * @return
     */
    Row locateHeaderRow(Sheet sheet) {

        Row rtn = null
        for (i in 0..5) { //Don't look at more than 5 rows
            Row row = sheet.getRow(i)
            try {

                //Look for mapped headers in the row (found more than 5 headers??)
                int foundCells = 0
                for (Cell cell: row) {
                    if (cell.cellType != HSSFCell.CELL_TYPE_BLANK) { //Ignore blank cells
                        String cellValue = cell.getStringCellValue()
                        if (cellValue != "" && headerMap.containsKey(cellValue)) {
                            foundCells++;
                        }
                    }
                }
                if (foundCells >= 5) {
                    rtn = row;
                    break;
                }
            } catch (Exception e) {
                //Most common exception would be when looking for string data (headers) when the row contains numerical
                //data.
                //@todo: eric: That case should probably be checked above.
                //At the end of the day, an error will cause this to continue - eventually
                e.printStackTrace()
            }
        }
        return rtn;
    }

    /**
     * Actually performs the entire import, by:
     *
     * Looping sheet
     * @param importJob
     */
    void processImportJob(ImportUnitsJob importJob) {
        Set<ImportedUnitRecord> processedRecordSet = new HashSet<ImportedUnitRecord>()
        searchableService.stopMirroring()
        //This happens in a thread, so manual transaction and session boundaries are required
        Leader.withTransaction {

            try {
                Leader.withSession {Session session ->

                    for (ImportSheet currentSheet: importJob.sheetsToImport) {
                        importJob.currentSheet = currentSheet
                        currentSheet.importStatus = ImportStatus.Processing

                        int startRowNumber = currentSheet.headerRow.getRowNum()
                        int endRowNumber = currentSheet.workbookSheet.physicalNumberOfRows
                        for (int i = startRowNumber + 1; i < endRowNumber; i++) {
                            Row row = currentSheet.workbookSheet.getRow(i)
                            if (containsDataToImport(row, currentSheet.pidIndex)) {
                                ImportedUnitRecord record = null
                                try {
                                    record = new ImportedUnitRecord()
                                    currentSheet.headerIndex.each {
                                        int cellIndex = it.value
                                        String spreadsheetFieldName = it.key
                                        Cell cell = row.getCell(cellIndex)
                                        def dataClosure = dataTypeMap[spreadsheetFieldName]
                                        if (!dataClosure) {
                                            throw new IllegalStateException("Closure mapping missing for field ${spreadsheetFieldName}")
                                        }
                                        def spreadSheetData = dataClosure(cell)

                                        String importRecordPropertyName = headerMap[spreadsheetFieldName]
                                        record.setProperty(importRecordPropertyName, spreadSheetData)
                                    }

                                    if (!record.districtName) {
                                        throw new IllegalStateException("Missing district name")
                                    }
                                    if (!record.unitNumber) {
                                        throw new IllegalStateException("Missing unit number")
                                    }
                                    if (!record.unitType) {
                                        throw new IllegalStateException("Missing unit type")
                                    }
                                    if (!record.programName) {
                                        throw new IllegalStateException("Missing program name")
                                    }
                                    if (!record.sponseringOrg) {
                                        throw new IllegalStateException("Missing sponsering org")
                                    }
                                    // xsl from council tends to have multiple duplicate entries
                                    // skip if previously handled
                                    if(!processedRecordSet.contains(record)) {
                                        System.out.println(record);
                                        processedRecordSet.add(record);

                                        // find district
                                        def districtLabel = parseDistrictLabel(record)
                                        ScoutGroup district = findByIdAndOrgType(convertToGroupIdentifier(districtLabel),ScoutGroupType.District)

                                        if(district) {
                                            System.out.println("District " + district);
                                        } else {
                                            List<ScoutGroup> councils = ScoutGroup.findAllByGroupType(ScoutGroupType.Council)
                                            if(councils.size() != 1) {
                                                throw new IllegalStateException("No matching district found, and a council could not be determined.  Please precreate a district for with a Unit ID " + convertToGroupIdentifier(districtLabel));
                                            }

                                            district = new ScoutGroup()
                                            district.groupIdentifier = convertToGroupIdentifier(districtLabel)
                                            district.groupLabel = districtLabel
                                            district.parent = councils.get(0)
                                            district.groupType = ScoutGroupType.District
                                            district.save(flush: true)
                                        }

                                        ScoutGroup stakeOrGroup;
                                        if(isLds(record)) {
                                            def label = parseLdsStakeLabel(record)
                                            def id = convertToGroupIdentifier(label)
                                            stakeOrGroup = findByIdAndOrgTypeAndParent(id, ScoutGroupType.Stake, district);
                                            if(!stakeOrGroup) {
                                                stakeOrGroup = new ScoutGroup()
                                                stakeOrGroup.groupIdentifier = id
                                                stakeOrGroup.groupLabel = label
                                                stakeOrGroup.parent = district
                                                stakeOrGroup.groupType = ScoutGroupType.Stake
                                                stakeOrGroup.save(flush: true)
                                            }
                                        } else {
                                            List<ScoutGroup> nonLdsGroups = findAllByOrgTypeAndParent(ScoutGroupType.Group, district);
                                            if(nonLdsGroups.size() == 1) {
                                                stakeOrGroup = nonLdsGroups.get(0);
                                            } else if(nonLdsGroups.size() == 0) {
                                                stakeOrGroup = new ScoutGroup()
                                                stakeOrGroup.groupIdentifier = "beefSteak"
                                                stakeOrGroup.groupLabel = "Beef Steak (Non LDS)"
                                                stakeOrGroup.parent = district
                                                stakeOrGroup.groupType = ScoutGroupType.Group
                                                stakeOrGroup.save(flush: true)
                                            } else {
                                                throw new IllegalStateException("More than one Non LDS Group found for district " + district + " and spreadsheet does not permit importer to handle");
                                            }

                                        }

                                        // find stake/group
                                        if(stakeOrGroup) {
                                            System.out.println("Stake Or Group " + stakeOrGroup);
                                        }
                                            // missing then create
                                        // find sponsoring org
                                        ScoutGroup charteringOrg
                                        def label = record.getSponseringOrg()
                                        if(isLds(record)) {
                                             label = parseLdsWardLabel(record)
                                        }
                                        def id = convertToGroupIdentifier(label)
                                        charteringOrg = findByIdAndOrgTypeAndParent(id, ScoutGroupType.CharteringOrg, stakeOrGroup);

                                        if(!charteringOrg) {
                                            charteringOrg = new ScoutGroup()
                                            charteringOrg.groupIdentifier = id
                                            charteringOrg.groupLabel = label
                                            charteringOrg.parent = stakeOrGroup
                                            charteringOrg.groupType = ScoutGroupType.CharteringOrg
                                            charteringOrg.save(flush: true)
                                        }

                                        ScoutGroup unit = ScoutGroup.findByGroupIdentifierAndUnitType(record.unitNumber, ScoutUnitType.valueOf(record.unitType))

                                        if(!unit) {
                                            unit = new ScoutGroup()
                                            unit.groupIdentifier = record.unitNumber
                                            unit.groupLabel = charteringOrg.groupLabel + " - " + record.unitType + " " + record.unitNumber
                                            unit.parent = charteringOrg
                                            unit.groupType = ScoutGroupType.Unit
                                            unit.unitType = ScoutUnitType.valueOf(record.unitType)
                                            unit.save(flush: true)
                                            System.out.println("Adding Unit " + unit)
                                        }
                                    } else {
                                        System.out.println("SKIPPED " + record);
                                    }
                                } catch (Exception e) {
                                    if (e instanceof ValidationException) {
                                        ValidationException ve = (ValidationException) e;
                                        ImportUnitError importError = new ImportUnitError(record: record)
                                        ve.errors.allErrors.each {   ObjectError err ->
                                            importError.addMessage(err)
                                        }
                                        currentSheet.errors << importError
                                    } else {
                                        System.out.println(e.message);
                                        currentSheet.errors << new ImportError(record: record).addMessage(new DefaultMessageSourceResolvable(null, e.message))
                                    }

                                    currentSheet.totalErrors++

                                    //Make sure we don't attempt to save data in the session
                                    session.clear()
                                } finally {
                                    currentSheet.totalComplete++
                                }
                            }

                        }
                        currentSheet.totalComplete = currentSheet.totalToImport
                        currentSheet.importStatus = ImportStatus.Complete
                    }


                }
            } catch (Exception e) {
                e.printStackTrace()
            } finally {
                searchableService.startMirroring()
                scoutGroupService.reindex();
                searchableService.reindex()

            }
        }


    }

    String convertToGroupIdentifier(String originalValue) {
        originalValue = originalValue?.replaceAll("\\s", "") ?: ""
        return originalValue[0].toLowerCase() + originalValue.substring(1)
    }

    String parseDistrictLabel(ImportedUnitRecord record){
        return record.districtName.tokenize('-').get(1) + " District";
    }

    String parseLdsStakeLabel(ImportedUnitRecord record){
        List<String> tokens = record.sponseringOrg.tokenize('-')


        return tokens.get(tokens.size()-1)
    }

    String parseLdsWardLabel(ImportedUnitRecord record){
        List<String> tokens = record.sponseringOrg.tokenize('-')

        String retVal;
        if(tokens.size() == 3) {
            retVal = tokens.get(1);
        } else {
            retVal = tokens.get(1) + " Special Needs"
        }

        return retVal
    }

    boolean isLds(ImportedUnitRecord record) {
        return record.sponseringOrg.startsWith("LDS");
    }



    // search based on id
    ScoutGroup findByIdAndOrgType(String groupIdentifier, ScoutGroupType groupType) {

        ScoutGroup retVal = ScoutGroup.findByGroupIdentifierAndGroupType(groupIdentifier, groupType);

        return retVal
    }


    // it is known that a ward name is only unique within a stake, not within the district.  lazona
    ScoutGroup findByIdAndOrgTypeAndParent(String groupIdentifier, ScoutGroupType groupType, ScoutGroup myParent) {

        List<ScoutGroup> scoutGroups = ScoutGroup.findAllByGroupIdentifierAndGroupType(groupIdentifier, groupType);

        ScoutGroup retVal = scoutGroups.find{s->s.parent == myParent}
        return retVal
    }

    List<ScoutGroup> findAllByOrgTypeAndParent(ScoutGroupType groupType, ScoutGroup myParent) {

        List<ScoutGroup> scoutGroups = ScoutGroup.findAllByGroupType(groupType);

        scoutGroups = scoutGroups.findAll{s->s.parent == myParent}
        return scoutGroups
    }


}
