package scoutcert

class ProgramCertification {
    ScoutUnitType unitType
    LeaderPositionType positionType;
    Certification certification
    boolean required

    static constraints = {
        unitType nullable:true
        required blank:false
        certification blank:false
        positionType nullable:true, validator: {val,ProgramCertification obj->
            if((obj.positionType && obj.unitType) || ((obj.positionType || obj.unitType)==false)) {
                return ["programCertification.leaderType.exclusiveOr"]
            }
        }
    }
}
