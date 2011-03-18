package scoutcert

class Leader implements Serializable {

    String firstName
    String lastName
    String username
    String password
    String email
    String verifyHash
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static belongsTo = ScoutUnit
    static hasMany = [openIds: OpenID, myScoutingIds: MyScoutingId, units: ScoutUnit]

    static constraints = {
        username blank: false, unique: true
        password blank: true
        email nullable: true
        verifyHash nullable:true
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        LeaderRole.findAllByLeader(this).collect { it.role } as Set
    }
}
