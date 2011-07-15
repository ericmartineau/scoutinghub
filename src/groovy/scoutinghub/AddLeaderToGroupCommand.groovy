package scoutinghub

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 7/14/11
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
class AddLeaderToGroupCommand {
    String firstName
    String lastName
    String email
    Leader foundLeader
    String scoutid
    ScoutGroup unit
    LeaderPositionType unitPosition

    static constraints = {
        scoutid(nullable:true)
        firstName(blank:false)
        lastName(blank:false)
        email(email:true,blank:false)
        unit(nullable:false)
        unitPosition(nullable:false)
        foundLeader(nullable:true)

    }



}
