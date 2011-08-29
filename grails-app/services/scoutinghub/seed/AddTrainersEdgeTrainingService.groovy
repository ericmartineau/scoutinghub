package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.LeaderPositionType

/**
 * User: ericm
 * Date: 8/28/11
 * Time: 8:45 PM
 */
class AddTrainersEdgeTrainingService implements SeedScript {

    SeedService seedService

    int getOrder() {
        return 15
    }

    void execute() {
        seedService.addCertification("Trainer's Edge", ["H96"], null, LeaderPositionType.values() as Collection, 0, false, false)
    }

    String getName() {
        return "addTrainersEdgeTraining"
    }


}
