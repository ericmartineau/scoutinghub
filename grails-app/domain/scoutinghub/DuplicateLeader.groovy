package scoutinghub

/**
 * A record that flags two leader records as being duplicates of each other.  This is useful when a person identifies duplicate
 * records but doesn't have the permission to merge them.
 */
class DuplicateLeader implements Serializable {

    static transients = ['leader1', 'leader2']

    Integer leader1Id
    Integer leader2Id

    Date createDate;
    Date updateDate;

    static constraints = {
        createDate nullable: true
        updateDate nullable: true
    }

    def beforeInsert = {
        createDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }

    Leader getLeader1() {
        return Leader.get(leader1Id)
    }

    Leader getLeader2() {
        return Leader.get(leader2Id)
    }

    void setLeader1(Leader leader) {
        this.leader1Id = leader?.id
    }

    void setLeader2(Leader leader) {
        this.leader2Id = leader?.id
    }
}
