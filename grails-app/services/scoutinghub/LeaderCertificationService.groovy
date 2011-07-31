package scoutinghub

import grails.plugins.springsecurity.SpringSecurityService

class LeaderCertificationService {

    static transactional = false

    SpringSecurityService springSecurityService
    TrainingService trainingService

    Date parseCertificationDate(String dateStr) {
        Date rtn = null;
        ["MM/dd/yyyy", "MM-dd-yyyy"].each {
            String format ->
            try {
                if (!rtn) {
                    rtn = Date.parse(format, dateStr)
                }

            } catch (Exception e) {
                log.warn("Error parsing", e)

            }
        };
        return rtn;
    }

    boolean saveLeaderCertification(LeaderCertification leaderCertification) {

        leaderCertification.dateEntered = new Date()
        leaderCertification.enteredBy = springSecurityService.currentUser
        leaderCertification.enteredType = LeaderCertificationEnteredType.ManuallyEntered

        boolean rtn = false;

        Leader leader = leaderCertification.leader

        //Let's delete any prior certification record.
        if (leaderCertification.leader && leaderCertification.certification) {
            leader.certifications?.findAll {
                LeaderCertification leaderCert ->
                leaderCert.certification.id == leaderCertification.certification.id
            }?.each {
                leader.removeFromCertifications(it)
                it.delete(flush: true)
            }
        }

        leaderCertification.clearErrors()
        if (leaderCertification.validate()) {
            leaderCertification.save()
            if (leaderCertification.hasErrors()) {
                rtn = false
            } else {
                leader.addToCertifications(leaderCertification)
                trainingService.recalculatePctTrained(leader);
                rtn = true
            }


        } else {
            rtn = false;
        }

        return rtn;

    }

}
