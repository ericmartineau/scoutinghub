package scoutinghub

/**
 * User: ericm
 * Date: 10/27/11
 * Time: 10:35 PM
 */
class MergedLeaderRole {
    Role role

    static belongsTo = [mergedLeader:MergedLeader]

    MergedLeaderRole() {

    }

    MergedLeaderRole(Role role) {
        this.role = role
    }
}
