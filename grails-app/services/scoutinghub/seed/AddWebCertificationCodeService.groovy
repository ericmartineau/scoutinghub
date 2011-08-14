package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.CertificationCode
import scoutinghub.Certification

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/13/11
 * Time: 10:56 PM
 * To change this template use File | Settings | File Templates.
 */
class AddWebCertificationCodeService implements SeedScript {
    int getOrder() {
        return 13
    }

    void execute() {
        [
                "CF3": "WCF3",
                "C70": "WCF5",
                "CFS": "WCF7",
                "S74": "WS74"
        ].each {entry ->
            Certification certification = CertificationCode.findByCode(entry.key).certification
            if (!certification.certificationCodes.find {it.code == entry.value}) {
                certification.addToCertificationCodes(new CertificationCode([code: entry.value]))
                certification.save(failOnError: true)
            }

        }
    }

    String getName() {
        return "addWebCertificationCodeService"
    }

}
