package scoutcert

class Leader implements Serializable {

    static searchable = {
            only: ['firstName', 'lastName', 'email']
            myScoutingIds component: true
            groups component: true
    }

    String firstName
    String lastName
    String username
    String password
    String email
    String verifyHash
    Date setupDate
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static belongsTo = ScoutGroup
    static constraints = {
        username(nullable: true, unique: true)
        password(nullable: true)
        email(nullable: true, email:true)
        verifyHash(nullable:true)
        setupDate(nullable:true)
    }

    static hasMany = [certifications: LeaderCertification, openIds: OpenID, myScoutingIds: MyScoutingId, groups: LeaderGroup]
    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        LeaderRole.findAllByLeader(this).collect { it.role } as Set
    }

    boolean hasAuthority(Role role) {
        return LeaderRole.findByLeaderAndRole(this, role) != null
    }

    boolean hasScoutingId(String scoutId) {
        boolean hasScoutingId = false
        myScoutingIds?.each{
            MyScoutingId myScoutingId->
            if(myScoutingId.myScoutingIdentifier == scoutId) {
                hasScoutingId = true
            }
        }
        return hasScoutingId
    }
    
    Set<LeaderCertification> getLeaderCertifications() {
         LeaderCertification.findAllByLeader(this).collect {it.leaderCertification} as Set
    }

    @Override
    String toString() {
        return firstName + " " + lastName
    }


}
