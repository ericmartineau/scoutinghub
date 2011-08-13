package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.CertificationType
import scoutinghub.LeaderPositionType
import scoutinghub.CertificationCode
import scoutinghub.Certification

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/12/11
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
class AddExtraCertificationCodesService implements SeedScript {


    SeedService seedService

    int getOrder() {
        return 11
    }

    void execute() {
        seedService.addCertification("Chartered Org Rep Fast Start", ["D62"], CertificationType.FastStart, [LeaderPositionType.CharterRep], 0, false, true)

        Certification commissionerFastStart = CertificationCode.findByCode("WDUC").certification
        commissionerFastStart.addToCertificationCodes(new CertificationCode([code: "DFS"]))
        commissionerFastStart.save(failOnError:true)

    }

    String getName() {
        return "addExtraCertificationCodes"
    }


}
