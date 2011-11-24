package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.LeaderPositionType
import scoutinghub.ScoutUnitType

/**
 * User: ericm
 * Date: 11/23/11
 * Time: 9:11 PM
 */
class SeedPowderHornService implements SeedScript {

    SeedService seedService

    int getOrder() {
        return 18
    }

    void execute() {
        LeaderPositionType[] allPositionTypes = LeaderPositionType.values()
        seedService.addCertification("Powderhorn", ["P50"], null, allPositionTypes as List, 0, false, false)
    }

    String getName() {
        return "seedPowderHornService"
    }

}
