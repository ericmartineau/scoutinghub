package scoutcert

class TrainingService {

    static transactional = true

    boolean recalculateEnabled = true

    void recalculatePctTrainedIfEnabled(Leader leader) {
        if (recalculateEnabled) {
            recalculatePctTrained(leader)
        }
    }

    void recalculatePctTrained(Leader leader) {

        int numCompletedCertifications = 0

        def requiredCertifications

        if (leader.groups) {

            def c = ProgramCertification.createCriteria()
            Date now = new Date()



            requiredCertifications = c.list {
                and {
                    or {
                        inList('unitType', leader.groups?.collect {it.scoutGroup.unitType}?.findAll {it != null})
                        inList('positionType', leader.groups?.collect {it.position})
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

            requiredCertifications?.each {ProgramCertification progCert ->
                if (leader.certifications.find {it.certification.id == progCert.certification.id && !it.hasExpired()}) {
                    numCompletedCertifications++
                }
            }
        }


        if (requiredCertifications?.size() > 0) {
            leader.pctTrained = 100 * numCompletedCertifications / requiredCertifications?.size()
        } else {
            leader.pctTrained = 100
        }
        leader.save(failOnError: true)

    }

    public void disableRecalculation() {
        recalculateEnabled = false;
    }

    public void enableRecalculation() {
        recalculateEnabled = true;
    }
}
