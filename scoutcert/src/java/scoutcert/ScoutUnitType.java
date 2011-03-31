package scoutcert;

/**
 * User: eric
 * Date: 3/28/11
 * Time: 9:45 PM
 */
public enum ScoutUnitType {
    Pack(LeaderPositionType.Cubmaster),
    Troop(LeaderPositionType.Scoutmaster),
    Crew(LeaderPositionType.CrewAdvisor),
    Team(LeaderPositionType.VarsityCoach);

    ScoutUnitType(LeaderPositionType unitLeaderType) {
        this.unitLeaderType = unitLeaderType;
    }

    public final LeaderPositionType unitLeaderType;
}
