package scoutinghub

/**
 * User: ericm
 * Date: 10/27/11
 * Time: 10:35 PM
 */
class MergedLeaderRole {
    Integer roleId

    static transients = ['role']

    Role getRole() {
        return Role.get(roleId)
    }

    void setRole(Role role) {
        this.roleId = role?.id
    }

    static belongsTo = [mergedLeader:MergedLeader]

    MergedLeaderRole() {

    }

    MergedLeaderRole(Role role) {
        this.role = role
    }
}
