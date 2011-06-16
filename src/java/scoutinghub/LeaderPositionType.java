package scoutinghub;

import org.apache.commons.io.filefilter.FalseFileFilter;

import static scoutinghub.ScoutGroupType.*;
import static scoutinghub.ScoutUnitType.*;

/**
 * User: eric
 * Date: 3/30/11
 * Time: 10:39 PM
 */
public enum LeaderPositionType {
    Scoutmaster("SM", true, true, Troop),
    AssistantScoutMaster("SA", true, false, Troop),
    Cubmaster("CM", true, true, Pack),
    AssistantCubmaster("CA", true, false, Pack),
    TigerLeader("TL", true, false, Pack),
    DenLeader("DL", true, false, Pack),
    AssistantDenLeader("DA", true, false, Pack),
    WebelosLeader("WL", true, false, Pack),
    AssistantWebelosLeader("WA", true, false, Pack),
    PackTrainer("PT", false, false, Pack),
    VarsityCoach("VC", true, true, Team),
    AssistantVarsityCoach("VA", true, false, Team),
    CrewAdvisor("NL", true, true, Crew),
    AssistantCrewAdvisor("NA", true, false, Crew),
    CharterRep("CR", false, false, Troop, Pack, Crew, Team),
    CommitteeChair("CC", false, false, Troop, Pack, Crew, Team),
    CommitteeMember("MC", false, false, Troop, Pack, Crew, Team),
    ScoutParentsUnitCoordinator("PC", false, false, Troop, Pack, Crew, Team),
    Executive("EX", false, Council),
    Professional("PF", false, Council),
    Chairman("DistrictChairman", false, District),
    Commissioner("DistrictCommissioner", false, District),
    DistrictExecutive("DistrictExecutive", false, District),
    DistrictRelations("DistrictRelations", false, District),
    DistrictCubScoutProgram("DistrictCubScoutProgram", false, District),
    District11YrOldScoutProgram("District11YrOldScoutProgram", false, District),
    DistrictBoyScoutProgram("DistrictBoyScoutProgram", false, District),
    DistrictVarsityScoutProgram("DistrictVarsityScoutProgram", false, District),
    DistrictVenturingProgram("DistrictVenturingProgram", false, District),
    DistrictFinanceCommittee("DistrictFinanceCommittee", false, District),
    DistrictRoundtableCommittee("DistrictRoundtableCommittee", false, District),
    DistrictMembershipCommittee("DistrictMembershipCommittee", false, District),
    DistrictNominationCommittee("DistrictNominationCommittee", false, District),
    DistrictCommunicationsCommittee("DistrictCommunicationsCommittee", false, District),
    DistrictAdvancementCommittee("DistrictAdvancementCommittee", false, District),
    DistrictActivitiesCivicServiceCommittee("DistrictActivitiesCivicServiceCommittee", false, District),
    DistrictCampingOutdoorCommittee("CampingOutdoorCommittee", false, District),
    DistrictTrainingCommittee("DistrictTrainingCommittee", false, District),
    AssistantDistrictCommissioner("ADC", false, Stake),
    UnitCommissioner("UC", false, Stake),
    RelationsCommittee("RC", false, Stake),

    Volunteer("VO", false, Council, District, CharteringOrg, Stake, Group),
    Administrator("AD", false, Council, District, CharteringOrg, Stake, Group);


    LeaderPositionType(String code, boolean directContact, boolean keyLeaderPosition, ScoutUnitType... scoutUnitTypes) {
        this.code = code;
        this.directContact = directContact;
        this.scoutUnitTypes = scoutUnitTypes;
        this.keyLeaderPosition = keyLeaderPosition;
        this.scoutGroupTypes = new ScoutGroupType[0];

    }

    LeaderPositionType(String code, boolean directContact, ScoutGroupType... scoutUnitTypes) {
        this.code = code;
        this.directContact = directContact;
        this.keyLeaderPosition = false;
        this.scoutGroupTypes = scoutUnitTypes;
        this.scoutUnitTypes = new ScoutUnitType[0];
    }

    LeaderPositionType(String code, boolean directContact) {
        this.code = code;
        this.directContact = directContact;
        this.keyLeaderPosition = false;
        this.scoutGroupTypes = new ScoutGroupType[0];
        this.scoutUnitTypes = new ScoutUnitType[0];
    }

    public final ScoutUnitType[] scoutUnitTypes;
    public final ScoutGroupType[] scoutGroupTypes;
    public final String code;
    public final boolean directContact;
    public final boolean keyLeaderPosition;
}
