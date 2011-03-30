package scoutcert

class LeaderCertification implements Serializable{
    Date dateEarned;
    Certification certification
    Leader leader
    Leader enteredBy
    Date dateEntered
    LeaderCertificationEnteredType enteredType

    static belongsTo = [Certification, Leader]
    static constraints = {

    }

    Date goodUntilDate() {
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(dateEarned)
        calendar.add(Calendar.DATE, certification.durationInDays)
        return calendar.time
    }
}
