package scoutinghub

/**
 * User: ericm
 * Date: 10/27/11
 * Time: 10:39 PM
 */
class MergedLeaderGroup {

    static belongsTo = [mergedLeader:MergedLeader]

    MergedLeaderGroup() {

    }

    MergedLeaderGroup(ScoutGroup scoutGroup, boolean admin, LeaderPositionType leaderPosition) {
        this.scoutGroup = scoutGroup
        this.admin = admin
        this.leaderPosition = leaderPosition
    }

    ScoutGroup scoutGroup
    boolean admin
    LeaderPositionType leaderPosition
}
