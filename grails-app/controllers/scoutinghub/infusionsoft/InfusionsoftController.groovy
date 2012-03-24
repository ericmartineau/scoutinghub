package scoutinghub.infusionsoft

import scoutinghub.Leader
import grails.plugins.springsecurity.Secured
import grails.converters.JSON
import scoutinghub.LeaderGroup

/**
 * User: eric
 * Date: 6/19/11
 * Time: 11:06 AM
 */
@Secured(["ROLE_ADMIN"])
class InfusionsoftController {

    InfusionsoftService infusionsoftService
    def syncLeader = {
        Leader leader = Leader.get(params.id)
        int infusionsoftContactId = infusionsoftService.syncLeader(leader)
        render "Done: ${infusionsoftContactId}"
    }

    def syncAllLeaders = {
        infusionsoftService.syncAllLeaders(Integer.parseInt(params.id))
        render "Done"
    }

//    @Secured(["ROLE_ANONYMOUS"])
//    def remote = {
//        int infusionsoftId = Integer.parseInt(params.id)
//        InfusionsoftLeaderInfo leaderInfo = InfusionsoftLeaderInfo.findByInfusionsoftContactId(infusionsoftId)
//        def rtn = [infusionsoftId: infusionsoftId]
//
//        if(leaderInfo) {
//            if(leaderInfo.leader?.myScoutingIds?.size() > 0) {
//                rtn.myScoutingId = leaderInfo.leader?.myScoutingIds.iterator().next().myScoutingIdentifier;
//            }
//
//            if (leaderInfo.leader.groups?.size() > 0) {
//                LeaderGroup leaderGroup = leaderInfo.leader.groups.iterator().next()
//                rtn.position = message(code: leaderGroup.leaderPosition.name())
//                rtn.unit = leaderGroup.scoutGroup.toString()
//            }
//
//            rtn.loginDate = leaderInfo.leader.createDate
//            rtn.pctTrained = leaderInfo.leader?.groups?.iterator().next().pctTrained
//        }
//
//        render rtn as JSON
//    }

    def syncTags = {
        infusionsoftService.syncTags()
        render "Done"
    }
}
