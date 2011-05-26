package scoutinghub

class Certification {
    String externalId;
    String name;
    String description;
    int durationInDays;
    boolean tourPermitRequired;

    Date createDate;
    Date updateDate;


    static hasMany = [leaderCertifications: LeaderCertification]
    static constraints = {
        externalId blank: false, unique: true
        name blank: false, unique: true
        description blank: true, maxSize: 1000
        durationInDays blank: true
        tourPermitRequired blank: false
        createDate nullable: true
        updateDate nullable: true
    }

    static mapping = {
        cache(true)
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
