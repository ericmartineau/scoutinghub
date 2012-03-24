package scoutinghub

/**
 * User: ericm
 * Date: 10/27/11
 * Time: 10:25 PM
 */
class MergedLeaderCertification {

    static belongsTo = [mergedLeader:MergedLeader]

    MergedLeaderCertification() {
    }

    MergedLeaderCertification(Date dateEarned, Certification certification) {
        this.dateEarned = dateEarned
        this.certification = certification
    }

    Date dateEarned
    Certification certification

}
