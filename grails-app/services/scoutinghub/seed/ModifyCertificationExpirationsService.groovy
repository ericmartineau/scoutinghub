package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.Certification
import scoutinghub.LeaderCertification

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 7/31/11
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
class ModifyCertificationExpirationsService implements SeedScript {
    int getOrder() {
        return 6
    }

    void execute() {
        Certification.findAllByDurationInDays(1780)?.each {
            Certification certification->
            certification.setDurationInDays(0)
            certification.save(flush:true)

            LeaderCertification.findAllByCertification(certification).each{
                LeaderCertification leaderCertification->
                leaderCertification.expirationDate = null
                leaderCertification.save(failOnError:true)
            }
        }
    }

    String getName() {
        return "modifyCertificationExpiration"
    }

}
