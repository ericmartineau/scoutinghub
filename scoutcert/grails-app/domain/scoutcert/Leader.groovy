package scoutcert

class Leader implements Serializable {

    String username
    String password
    String email
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static hasMany = [openIds: OpenID, myScoutingIds: MyScoutingId]

    static constraints = {
        username blank: false, unique: true
        password blank: true
        email blank: true
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        LeaderRole.findAllByLeader(this).collect { it.role } as Set
    }
}
