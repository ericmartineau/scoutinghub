package scoutcert

class LeaderCertification {
    Date dateEarned;
    Certification certification
    Leader leader

    static belongsTo = [Certification, Leader]
    static constraints = {
    }
}
