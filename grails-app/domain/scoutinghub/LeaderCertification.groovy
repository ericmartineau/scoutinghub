package scoutinghub

class LeaderCertification implements Serializable {

    transient TrainingService trainingService

    static transients = ["trainingService"]
    Date dateEarned
    Date expirationDate
    Certification certification
    Leader leader
    Leader enteredBy
    Date dateEntered
    LeaderCertificationEnteredType enteredType

    Date createDate;
    Date updateDate;

    static belongsTo = [certification: Certification, leader: Leader]
    static constraints = {
        createDate nullable: true
        updateDate nullable: true
        expirationDate nullable:true
        certification(unique: 'leader')
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
}
