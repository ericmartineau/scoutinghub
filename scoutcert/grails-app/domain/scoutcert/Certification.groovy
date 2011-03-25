package scoutcert

class Certification {
    String externalId;
    String name;
    String description;
    int durationInDays;
    boolean tourPermitRequired;


    static hasMany = [leaderCertifications: LeaderCertification]
    static constraints = {
        externalId blank: false, unique: true
        name blank: false, unique: true
        description blank: true, maxSize: 1000
        durationInDays blank: true
        tourPermitRequired blank: false

    }
}
