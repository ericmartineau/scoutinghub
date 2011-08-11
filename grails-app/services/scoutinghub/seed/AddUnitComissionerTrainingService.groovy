package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.LeaderPositionType
import scoutinghub.CertificationCode
import scoutinghub.Certification
import scoutinghub.ProgramCertification

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/9/11
 * Time: 11:04 PM
 * To change this template use File | Settings | File Templates.
 */
class AddUnitComissionerTrainingService implements SeedScript {

    SeedService seedService

    int getOrder() {
        return 7;
    }

    void execute() {

        seedService.addCertification("Commissioner Specific Training", ["D20"], null, [LeaderPositionType.Commissioner], 0, false, true)
        seedService.addCertification("Commissioner Fast Start", ["WDUC"], null, [LeaderPositionType.Commissioner], 0, false, true)

        Certification thisIsScouting = CertificationCode.findByCode("WA01").certification
        Certification youthProtectionTraining = CertificationCode.findByCode("Y01").certification

        //We need to add it:
        ProgramCertification thisIsScoutingProgramCertification = new ProgramCertification()
        thisIsScoutingProgramCertification.positionType = LeaderPositionType.Commissioner
        thisIsScoutingProgramCertification.certification = thisIsScouting
        thisIsScoutingProgramCertification.required = true

        Date startDate = Date.parse('yyyy/MM/dd', '1973/01/01')
        Date endDate = Date.parse('yyyy/MM/dd', '2030/01/01')

        thisIsScoutingProgramCertification.startDate = startDate
        thisIsScoutingProgramCertification.endDate = endDate

        thisIsScouting.addToProgramCertifications(thisIsScoutingProgramCertification)
        thisIsScouting.save(failOnError: true)

    }

    String getName() {
        return "addUnitCommissionerTraining"
    }

}
