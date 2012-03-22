package scoutinghub.infusionsoft

import org.springframework.context.ApplicationListener
import scoutinghub.events.LeaderInvitedEvent
import scoutinghub.Leader
import org.springframework.beans.factory.annotation.Autowired
import scoutinghub.ScoutGroup
import scoutinghub.ScoutGroupType

/**
 * User: ericm
 * Date: 8/24/11
 * Time: 9:18 PM
 */
class LeaderInvitedEventHandler implements ApplicationListener<LeaderInvitedEvent> {

    @Autowired
    private InfusionsoftService infusionsoftService


    void onApplicationEvent(LeaderInvitedEvent e) {
        Leader.withTransaction {

            Leader invitee = e.invitee?.merge()
            int infusionsoftContactId = infusionsoftService.syncLeader(invitee)

            def foundRecords = [:]
            invitee.groups.each {
                ScoutGroup parent = it.scoutGroup
                while (parent != null) {
                    def foundInfo = InfusionsoftFollowUpInfo.findByScoutGroup(parent)
                    if (foundInfo) {
                        foundRecords[parent.groupType] = foundInfo
                    }
                    parent = parent.parent
                }
            }

            InfusionsoftFollowUpInfo foundInfo
            ScoutGroupType.values().each {
                if (foundInfo == null && foundRecords[it]) {
                    foundInfo = foundRecords[it]
                }
            }

            if (foundInfo && foundInfo.invitationFollowUpSequenceId > 0) {
                infusionsoftService.apiService.ContactService.addToCampaign(infusionsoftContactId, foundInfo.invitationFollowUpSequenceId)
            }

        }

    }
}
