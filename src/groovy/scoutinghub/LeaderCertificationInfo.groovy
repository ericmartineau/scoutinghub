package scoutinghub

/**
 * User: eric
 * Date: 4/2/11
 * Time: 2:04 PM
 */
class LeaderCertificationInfo {
    Leader leader
    Certification certification
    LeaderCertification leaderCertification
    CertificationStatus certificationStatus

    LeaderCertificationInfo(Leader leader, Certification certification) {
        this.leader = leader
        this.certification = certification
        this.leaderCertification = leader.findCertification(certification)

        if(leader.certificationExpired(certification)) {
            certificationStatus = CertificationStatus.Expired
        } else if(leader.hasCertification(certification)) {
            certificationStatus = CertificationStatus.Current
        } else {
            certificationStatus = CertificationStatus.Missing
        }
    }
}
