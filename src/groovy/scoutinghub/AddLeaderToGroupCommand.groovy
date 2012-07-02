package scoutinghub

import grails.validation.Validateable

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 7/14/11
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Validateable
class AddLeaderToGroupCommand {
    String firstName
    String lastName
    String email
    Leader foundLeader
    String scoutid
    String phone
    String address1
    String city
    String state
    String postalCode
    ScoutGroup unit
    LeaderPositionType unitPosition

    static constraints = {
        scoutid(nullable:true)
        firstName(blank:false)
        lastName(blank:false)
        email(email:true,nullable:true)

        phone(nullable:true)
        address1(nullable:true)
        city(nullable:true)
        state(nullable:true)
        postalCode(nullable:false)

        unit(nullable:false)
        unitPosition(nullable:false)
        foundLeader(nullable:true)

    }



}
