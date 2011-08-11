package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.LeaderPositionType
import scoutinghub.ScoutGroupType
import scoutinghub.Certification
import scoutinghub.ProgramCertification

class AddAllPositionTypesToAdminPositionsService implements SeedScript {

    static transactional = false

    int getOrder() {
        return 8
    }

    void execute() {
        def nonUnitPositions = LeaderPositionType.values().findAll {it.scoutUnitTypes?.size() == 0}
        Certification.list()?.each {Certification cert->
            nonUnitPositions.each {LeaderPositionType leaderPositionType->
                if(!cert.programCertifications?.find {it.positionType == leaderPositionType}) {
                    //We need to add it:
                    ProgramCertification newProgCert = new ProgramCertification()
                    newProgCert.positionType = leaderPositionType
                    newProgCert.certification = cert
                    newProgCert.required = false

                    Date startDate = Date.parse('yyyy/MM/dd', '1973/01/01')
                    Date endDate = Date.parse('yyyy/MM/dd', '2030/01/01')

                    newProgCert.startDate = startDate
                    newProgCert.endDate = endDate

                    cert.addToProgramCertifications(newProgCert)
                }
            }
            cert.save(failOnError:true)
        };

    }

    String getName() {
        return "addAllPositionTypesToAdminPositions"
    }


}
