package scoutinghub

/**
 * User: ericm
 * Date: 10/19/11
 * Time: 5:14 PM
 */
class MergedLeader {

    int originalId

    Leader mergedTo

    MergedLeader oldValues

    static belongsTo = [oldValues:MergedLeader]

    String firstName
    String middleName
    String lastName
    String username
    String password
    String email
    String phone

    String address1
    String address2
    String city
    String state
    String postalCode

    String verifyHash
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Date createDate;
    Date updateDate;

    static constraints = {
        mergedTo(nullable:true)
        oldValues(nullable: true)
        username(nullable: true)
        password(nullable: true)
        email(nullable: true, email: true)
        verifyHash(nullable: true)
        phone(nullable: true)
        createDate nullable: true
        updateDate nullable: true

        middleName(nullable:true)

        address1(nullable:true)
        address2(nullable:true)
        city(nullable:true)
        state(nullable:true)
        postalCode(nullable:true)
    }

    static def hasMany = [
            mergedRecords: MergedRecord,
            mergedLeaderCertifications: MergedLeaderCertification,
            mergedLeaderRoles: MergedLeaderRole,
            mergedScoutingId: MergedScoutingId,
            mergedLeaderGroups: MergedLeaderGroup
    ]

}
