package scoutinghub.trainingImport

/**
 * User: eric
 * Date: 3/27/11
 * Time: 1:16 PM
 */
class ImportedRecord {
    String scoutingId
    String firstName
    String lastName
    String email
    String unitNumber
    String unitType
    String leaderPosition

    Map<String, Date> certificationDataMap = new HashMap<String, Date>();

    public void addCertificationData(String key, Date date) {
        if(certificationDataMap.containsKey(key) && certificationDataMap.get(key).after(date)) {
            // do nothing
        } else {
            certificationDataMap.put(key,date);
        }
    }


    // created to support initial import that BSA is no longer capable of creating
    Date yptDate
    Date thisIsScoutingDate
    Date fastStartDate
    Date leaderSpecificDate
    Date outdoorSkillsDate
    Date y02CrewSkillsDate
    Date effectiveDate

}
