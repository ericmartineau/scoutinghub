package scoutinghub

class LeaderGroup implements Serializable {

    static searchable = {
        scoutGroup component: true
    }

    Leader leader
    ScoutGroup scoutGroup
    boolean admin
    LeaderPositionType position
    double pctTrained

    Date createDate;
    Date updateDate;

    static constraints = {
        createDate(nullable: true)
        updateDate(nullable: true)
        scoutGroup(unique: 'leader')
    }

    def beforeInsert = {
        createDate = new Date()
        updateDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }

}
