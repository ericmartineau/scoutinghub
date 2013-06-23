package scoutinghub

/**
 * Record that a Leader has completed a given certification.
 */
class LeaderCertification implements Serializable {

    transient TrainingService trainingService
    static transients = ["trainingService", "leader", "enteredBy"]

    Date dateEarned
    Date expirationDate
    Certification certification

    Integer leaderId
    Integer enteredById

    Date dateEntered
    Date lastCalculationDate
    LeaderCertificationEnteredType enteredType

    Date createDate;
    Date updateDate;

    static belongsTo = [certification: Certification]
    static constraints = {
        lastCalculationDate(nullable: true)
        createDate(nullable: true)
        updateDate(nullable: true)
        expirationDate(nullable: true)
        certification(unique: 'leader')
    }

    static mapping = {
        sort 'dateEarned'
    }

    boolean hasExpired() {
        return goodUntilDate()?.before(new Date())
    }

    Date goodUntilDate() {
        Date rtn = null
        if (certification?.durationInDays > 0 && dateEarned) {
            Calendar calendar = Calendar.getInstance()
            calendar.setTime(dateEarned)
            calendar.add(Calendar.DATE, certification.durationInDays)
            rtn = calendar.time
        }

        return rtn
    }

    def beforeInsert = {
        createDate = new Date()
        expirationDate = goodUntilDate();

    }

    def beforeUpdate = {
        updateDate = new Date()
        expirationDate = goodUntilDate();
    }

    @Override
    String toString() {
        return "${leader}: ${certification}"
        //return "leader: certification"
    }

    Leader getLeader() {
        Leader.get(leaderId)
    }

    void setLeader(Leader leader) {
        this.leaderId = leader?.id
    }

    Leader getEnteredBy() {
        Leader.get(enteredById)
    }

    void setEnteredBy(Leader enteredBy) {
        this.enteredById = enteredBy?.id
    }
}
