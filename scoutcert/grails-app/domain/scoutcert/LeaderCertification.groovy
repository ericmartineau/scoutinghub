package scoutcert

class LeaderCertification implements Serializable{

    transient TrainingService trainingService

    static transients = ["trainingService"]
    Date dateEarned;
    Certification certification
    Leader leader
    Leader enteredBy
    Date dateEntered
    LeaderCertificationEnteredType enteredType

    Date createDate;
    Date updateDate;

    static belongsTo = [certification:Certification, leader:Leader]
    static constraints = {
        createDate nullable: true
        updateDate nullable: true
    }

    boolean hasExpired() {
        return goodUntilDate()?.before(new Date())
    }

    Date goodUntilDate() {
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(dateEarned)
        calendar.add(Calendar.DATE, certification.durationInDays)
        return calendar.time
    }

    def beforeInsert = {
        createDate = new Date()

    }

    def beforeUpdate = {
        updateDate = new Date()
    }

    def afterInsert = {
        trainingService.recalculatePctTrainedIfEnabled(leader)
    }

    def afterUpdate = {
        trainingService.recalculatePctTrainedIfEnabled(leader)
    }
}
