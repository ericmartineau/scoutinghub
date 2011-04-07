package scoutcert

class LeaderCertification implements Serializable{
    Date dateEarned;
    Certification certification
    Leader leader
    Leader enteredBy
    Date dateEntered
    LeaderCertificationEnteredType enteredType

    Date createDate;
    Date updateDate;

    static belongsTo = [Certification, Leader]
    static constraints = {
        createDate nullable: true
        updateDate nullable: true
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
}
