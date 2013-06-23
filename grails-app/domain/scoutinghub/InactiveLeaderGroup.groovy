package scoutinghub

/**
 * This record is created when a LeaderGroup record is deleted.  It's used during import to make sure that leaders don't get reassigned
 * back into units they were removed from (in the case the ScoutNet's records lag)
 */
class InactiveLeaderGroup {

    static mapWith = "neo4j"

    Leader leader
    ScoutGroup scoutGroup

    /**
     * The date this record was created - which is technically the date the LeaderGroup record was deleted
     */
    Date createDate

    static belongsTo = [Leader, ScoutGroup]

    def beforeInsert = {
        createDate = new Date()
    }

    static constraints = {
        createDate(nullable: true)
    }
}
