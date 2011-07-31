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

        calculateStatus()
    }

    LeaderCertificationInfo(LeaderCertification leaderCertification) {
        this.leader = leaderCertification.leader
        this.certification = leaderCertification.certification
        this.leaderCertification = leaderCertification

        calculateStatus()
    }

    private calculateStatus() {
        if(leader.certificationExpired(certification)) {
            certificationStatus = CertificationStatus.Expired
        } else if(leader.hasCertification(certification)) {
            certificationStatus = CertificationStatus.Current
        } else {
            certificationStatus = CertificationStatus.Missing
        }
    }


    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o instanceof LeaderCertificationInfo)) return false;

        LeaderCertificationInfo that = (LeaderCertificationInfo) o;

        if (leaderCertification?.id != that.leaderCertification?.id) return false;

        return true;
    }

    int hashCode() {
        return leaderCertification.id
    }
}
