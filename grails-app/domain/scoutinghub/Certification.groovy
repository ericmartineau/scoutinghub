package scoutinghub

/**
 * A required training course for an adult scouting leader.  This entity represents only the definition of the certification.
 */
class Certification {
    String name;
    String description;

    String trainingUrl;

    /**
     * How long (in days) before a certification expires
     */
    int durationInDays;

    /**
     * Whether or not a tour permit requires a leader with this certification.
     */
    boolean tourPermitRequired;

    /**
     * An optional field representing a classification of certification.  Many scouting positions have their own version of a
     * certification (leader-specific, fast start), and this field helps locate the correct certification given a position type
     * and a certificationType.
     */
    CertificationType certificationType;

    Date createDate;
    Date updateDate;

    static hasMany = [leaderCertifications: LeaderCertification, certificationCodes: CertificationCode, programCertifications: ProgramCertification]

    static constraints = {
        name blank: false, unique: true
        description blank: true, maxSize: 1000
        durationInDays blank: true
        tourPermitRequired blank: false
        createDate nullable: true
        updateDate nullable: true
        certificationType nullable: true
        trainingUrl blank: true, nullable: true
    }

    static mapping = {
        cache(true)
        sort: 'name'
        certificationCodes(sort: 'code')
    }

    @Override
    String toString() {
        return name
    }

    def beforeInsert = {
        createDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }
}
