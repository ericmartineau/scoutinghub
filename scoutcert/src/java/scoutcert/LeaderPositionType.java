package scoutcert;

/**
 * User: eric
 * Date: 3/30/11
 * Time: 10:39 PM
 */
public enum LeaderPositionType {
    CharterRep("CR", null),
    CommitteeChair("CC", null),
    CommitteeMember("MC", null),
    Scoutmaster("SM", ScoutUnitType.Troop),
    AssistantScoutMaster("SA", ScoutUnitType.Troop),
    Cubmaster("CM", ScoutUnitType.Pack),
    AssistantCubmaster("CA", ScoutUnitType.Pack),
    TigerLeader("TL", ScoutUnitType.Pack),
    DenLeader("DL", ScoutUnitType.Pack),
    WebelosLeader("WL", ScoutUnitType.Pack),
    AssistantDenLeader("DA", ScoutUnitType.Pack),
    AssistantWebelosLeader("WA", ScoutUnitType.Pack),
    VarsityCoach("VC", ScoutUnitType.Team),
    AssistantVarsityCoach("VA", ScoutUnitType.Team),
    CrewAdvisor("NL", ScoutUnitType.Crew),
    AssistantCrewAdvisor("NA", ScoutUnitType.Crew);


    LeaderPositionType(String code, ScoutUnitType scoutUnitType) {
        this.code = code;
        this.scoutUnitType = scoutUnitType;
    }

    public final ScoutUnitType scoutUnitType;
    public final String code;
}
