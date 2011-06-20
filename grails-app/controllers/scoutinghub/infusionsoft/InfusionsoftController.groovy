package scoutinghub.infusionsoft

import scoutinghub.Leader
import grails.plugins.springsecurity.Secured

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
        infusionsoftService.syncAllLeaders()
        render "Done"
    }

    def syncTags = {
        infusionsoftService.syncTags()
        render "Done"
    }
}
