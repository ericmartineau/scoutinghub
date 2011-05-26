package scoutinghub

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
    String phone
    String verifyHash
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Date createDate;
    Date updateDate;

    /**
     * The date the user set up their account (and logged in) to the system.
     */
    Date setupDate

    static constraints = {
        username(nullable: true, unique: true)
        password(nullable: true)
        email(nullable: true, email:true)
        verifyHash(nullable:true)
        phone(nullable:true)
        createDate nullable: true
        updateDate nullable: true
        setupDate nullable:true
    }



    static hasMany = [certificationClasses:CertificationClass,
            certifications: LeaderCertification,
            openIds: OpenID,
            myScoutingIds: MyScoutingId,
            groups: LeaderGroup]

    static mapping = {
        password column: '`password`'
        groups cascade:'all-delete-orphan'
        //myScoutingIds cascade: 'all-delete-orphan'
    }

    Set<Role> getAuthorities() {
        LeaderRole.findAllByLeader(this).collect { it.role } as Set
    }

    boolean hasAuthority(Role role) {
        return LeaderRole.findByLeaderAndRole(this, role) != null
    }

    boolean hasCertification(Certification certification) {
        return findCertification(certification) != null
    }

    boolean certificationExpired(Certification certification) {
        return findCertification(certification)?.goodUntilDate()?.before(new Date())
    }

    LeaderCertification findCertification(Certification certification) {
        return certifications?.find{it.certification.id == certification?.id}
    }

    LeaderGroup findScoutGroup(ScoutGroup group) {
        return groups?.find{it.scoutGroup.id == group?.id}
    }

    boolean hasScoutGroup(ScoutGroup group) {
        return findScoutGroup(group) != null
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

    def beforeInsert = {
        createDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }

}
