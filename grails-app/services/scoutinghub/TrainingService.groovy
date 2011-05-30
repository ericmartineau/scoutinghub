package scoutinghub

class TrainingService {

    static transactional = true

    void processExpiredTrainings() {
        //find all certifications that have expirations in the past
        def criteria = LeaderCertification.withCriteria {
            lt('expirationDate', new Date())
            leader {
                or {
                    isNull('lastCalculationDate')

                }
            }
        }
    }

    void recalculatePctTrained(Leader leader) {

        int numCompletedCertifications;

        def requiredCertifications

        for (LeaderGroup group: leader.groups) {
            numCompletedCertifications = 0
            requiredCertifications = getRequiredCertifications(group);

            requiredCertifications?.each {ProgramCertification progCert ->
                if (leader.certifications.find {it.certification.id == progCert.certification.id && !it.hasExpired()}) {
                    numCompletedCertifications++
                }
            }

            if (requiredCertifications?.size() > 0) {
                group.pctTrained = 100 * numCompletedCertifications / requiredCertifications?.size()
            } else {
                group.pctTrained = 100
            }
        }



        leader.save(failOnError: true)
    }

    List<ProgramCertification> getRequiredCertifications(LeaderGroup leaderGroup) {
        List<ProgramCertification> requiredCertifications = []
        def c = ProgramCertification.createCriteria()
        Date now = new Date()

        if (leaderGroup.scoutGroup.unitType) {


            requiredCertifications = c.list {
                and {
                    or {
                        inList('unitType', leaderGroup.scoutGroup.unitType)
                        inList('positionType', leaderGroup.leaderPosition)
                    }
                    eq('required', true)
                }
                eq('required', true)
                lt('startDate', now)
                gt('endDate', now)

                certification {
                    sort: 'name'
                }
            }
        }


        return requiredCertifications;
    }

}
