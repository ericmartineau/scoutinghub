package scoutinghub.events

import org.springframework.context.ApplicationEvent
import scoutinghub.Leader

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/24/11
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */
class LeaderRegisteredEvent extends ApplicationEvent {

    LeaderRegisteredEvent(Leader source) {
        super(source)
    }
}
