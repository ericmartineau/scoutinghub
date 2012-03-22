package scoutinghub.events

import org.springframework.context.ApplicationEvent
import scoutinghub.Leader

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/24/11
 * Time: 9:15 PM
 * To change this template use File | Settings | File Templates.
 */
class LeaderInvitedEvent extends ApplicationEvent {

    Leader invited
    Leader invitee
    LeaderInvitedEvent(Leader source, Leader invited) {
        super(source)
        this.invited = invited
        this.invitee = source
    }
}
