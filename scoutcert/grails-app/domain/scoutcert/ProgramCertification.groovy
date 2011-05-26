package scoutcert

class ProgramCertification {
    ScoutUnitType unitType
    LeaderPositionType positionType;
    Certification certification
    boolean required

    Date startDate;
    Date endDate;

    Date createDate;
    Date updateDate;

    static constraints = {
        unitType nullable:true
        required blank:false
        certification blank:false
        positionType nullable:true, validator: {val,ProgramCertification obj->
            if((obj.positionType && obj.unitType) || ((obj.positionType || obj.unitType)==false)) {
                return ["programCertification.leaderType.exclusiveOr"]
            }
        }
        startDate nullable: false
        endDate nullable: false

        createDate nullable: true
        updateDate nullable: true
    }

    def beforeInsert = {
        createDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }
}
