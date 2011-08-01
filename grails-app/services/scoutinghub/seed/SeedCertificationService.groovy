package scoutinghub.seed

import scoutinghub.SeedScript

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 7/31/11
 * Time: 8:56 PM
 * To change this template use File | Settings | File Templates.
 */
class SeedCertificationService implements SeedScript{

    SeedService seedService

    int getOrder() {
        return 4
    }

    void execute() {
        seedService.seedCertifications()
    }

    String getName() {
        return "seedCertification"
    }
}
