package scoutcert

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
