package scoutcert

class LeaderGroup implements Serializable {

    static searchable = true

    Leader leader
    ScoutGroup scoutGroup
    boolean admin
    LeaderPositionType position

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
