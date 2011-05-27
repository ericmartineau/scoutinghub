package scoutinghub

class InactiveLeaderGroup {

    Leader leader
    ScoutGroup scoutGroup
    Date createDate

    static belongsTo = [Leader, ScoutGroup]

    def beforeInsert = {
        createDate = new Date()
    }


    static constraints = {
        createDate(nullable: true)
    }
}
