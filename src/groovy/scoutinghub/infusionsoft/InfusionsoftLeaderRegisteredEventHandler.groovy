package scoutinghub.infusionsoft

import org.springframework.context.ApplicationListener
import scoutinghub.events.LeaderRegisteredEvent

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/24/11
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */
class InfusionsoftLeaderRegisteredEventHandler implements ApplicationListener<LeaderRegisteredEvent> {

    void onApplicationEvent(LeaderRegisteredEvent e) {
        //Is synced with infusionsoft
        //If so, remove from sequences (maybe from all registration sequences?)
        //If not synced with Infusionsoft, do so now
        //Add to appropriate "Welcome" sequence, based on org heirarchy

    }


}
