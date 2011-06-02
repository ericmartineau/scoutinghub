package scoutinghub

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * Assignment of a leader to a role (for permissions)
 */
class LeaderRole implements Serializable {

	Leader leader
	Role role

    Date createDate;
    Date updateDate;

    static constraints = {
        createDate nullable: true
        updateDate nullable: true
    }

	boolean equals(other) {
		if (!(other instanceof LeaderRole)) {
			return false
		}

		other.leader?.id == leader?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (leader) builder.append(leader.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static LeaderRole get(long leaderId, long roleId) {
		find 'from LeaderRole where leader.id=:leaderId and role.id=:roleId',
			[leaderId: leaderId, roleId: roleId]
	}

	static LeaderRole create(Leader leader, Role role, boolean flush = false) {
		new LeaderRole(leader: leader, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(Leader leader, Role role, boolean flush = false) {
		LeaderRole instance = LeaderRole.findByLeaderAndRole(leader, role)
		instance ? instance.delete(flush: flush) : false
	}

	static void removeAll(Leader leader) {
		executeUpdate 'DELETE FROM LeaderRole WHERE leader=:leader', [leader: leader]
	}

	static void removeAll(Role role) {
		executeUpdate 'DELETE FROM LeaderRole WHERE role=:role', [role: role]
	}

	static mapping = {
		id composite: ['role', 'leader']
		version false
	}

    def beforeInsert = {
        createDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }

}
