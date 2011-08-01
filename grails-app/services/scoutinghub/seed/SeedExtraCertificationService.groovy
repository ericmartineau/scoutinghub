package scoutinghub.seed

import scoutinghub.LeaderPositionType
import scoutinghub.ScoutUnitType
import scoutinghub.SeedScript

/**
 * User: ericm
 * Date: 7/31/11
 * Time: 9:00 PM
 */
class SeedExtraCertificationService implements SeedScript {

    SeedService seedService

    int getOrder() {
        return 5
    }

    void execute() {
        LeaderPositionType[] allPositionTypes = LeaderPositionType.values()

        seedService.addCertification("BALOO - Basic Adult Leader Outdoor Orientation", ["C32"], null,
            allPositionTypes.findAll {it.scoutUnitTypes?.contains(ScoutUnitType.Pack)}, 0, true, false)

        seedService.addCertification("Wood Badge", ["A90"], null, allPositionTypes as List, 0, false, false)

        seedService.addCertification("Weather Hazards", ["WS81"], null, allPositionTypes.findAll {it.scoutUnitTypes?.size() > 0}, 730, true, false)
        seedService.addCertification("Safe Swim Defense", ["SSD"], null, allPositionTypes.findAll {it.scoutUnitTypes?.size() > 0}, 730, true, false)
        seedService.addCertification("Safety Afloat", ["SA"], null, allPositionTypes.findAll {it.scoutUnitTypes?.size() > 0}, 730, true, false)
        seedService.addCertification("Climb On Safely", ["S74"], null, allPositionTypes.findAll {it.scoutUnitTypes?.size() > 0}, 730, true, false)
        seedService.addCertification("CPR Certification", ["N05"], null, allPositionTypes.findAll {it.scoutUnitTypes?.size() > 0}, 730, true, false)
        seedService.addCertification("Physical Wellness", ["SPW"], null, allPositionTypes.findAll {it.scoutUnitTypes?.size() > 0}, 730, true, false)
    }

    String getName() {
        return "seedExtraCertifications"
    }
}
