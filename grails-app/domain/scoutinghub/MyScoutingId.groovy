package scoutinghub

/**
 * Record that links a BSA Id to a Leader record.
 */
class MyScoutingId implements Serializable {

    static searchable = {
        root false
    }

    Leader leader
    String myScoutingIdentifier

    Date createDate;
    Date updateDate;

    static belongsTo = [Leader]

    static constraints = {
        myScoutingIdentifier(unique: true, blank: false)
        createDate nullable: true
        updateDate nullable: true
    }

    static fetchMode = [leader: 'eager']

    def beforeInsert = {
        createDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }


    public String toString() {
        return myScoutingIdentifier
    }
}
