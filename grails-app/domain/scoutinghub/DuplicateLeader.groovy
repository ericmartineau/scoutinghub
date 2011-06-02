package scoutinghub

/**
 * A record that flags two leader records as being duplicates of each other.  This is useful when a person identifies duplicate
 * records but doesn't have the permission to merge them.
 */
class DuplicateLeader implements Serializable {

    Leader leader1
    Leader leader2

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
}
