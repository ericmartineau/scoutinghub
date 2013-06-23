package scoutinghub

/**
 * User: ericm
 * Date: 10/27/11
 * Time: 10:39 PM
 */
class MergedLeaderGroup {

    static transients = ['scoutGroup']

    static belongsTo = [mergedLeader:MergedLeader]

    Integer scoutGroupId

    MergedLeaderGroup() {

    }

    MergedLeaderGroup(ScoutGroup scoutGroup, boolean admin, LeaderPositionType leaderPosition) {
        this.scoutGroupId = scoutGroup?.id
        this.admin = admin
        this.leaderPosition = leaderPosition
    }

    boolean admin
    LeaderPositionType leaderPosition

    ScoutGroup getScoutGroup() {
        ScoutGroup.get(scoutGroupId)
    }

    void setScoutGroup(ScoutGroup scoutGroup) {
        this.scoutGroupId = scoutGroup?.id
    }
}
