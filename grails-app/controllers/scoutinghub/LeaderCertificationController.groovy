package scoutinghub

import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import grails.plugins.springsecurity.Secured
import java.text.ParseException

@Secured(['ROLE_LEADER'])
class LeaderCertificationController {

    SpringSecurityService springSecurityService
    TrainingService trainingService
    LeaderCertificationService leaderCertificationService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [leaderCertificationInstanceList: LeaderCertification.list(params), leaderCertificationInstanceTotal: LeaderCertification.count()]
    }

    def quickCreate = {
        LeaderCertification leaderCertification = new LeaderCertification(params)
        return [leaderCertification: leaderCertification, postAction: "processQuickCreate"]
    }

    def quickEdit = {
        LeaderCertification leaderCertification = LeaderCertification.get(params.id)
        render(view: "quickCreate", model: [leaderCertification: leaderCertification, postAction: "processQuickEdit"])
    }

    def processQuickEdit = {
        int certId = Integer.parseInt(params['leaderCertification.id'])
        LeaderCertification leaderCertification = LeaderCertification.get(certId)
        Date dateEarned
        try {
            dateEarned = Date.parse("MM/dd/yyyy", params.dateEarned)
        } catch (Exception e) {
            e.printStackTrace()
            flash.error = "leaderCertification.create.invalidDate"
            render(view: "quickCreate", model: [leaderCertification: leaderCertification, postAction: "processQuickEdit"])
            return

        }
        if (dateEarned) {
            try {
                Leader leader = leaderCertification.leader
                leaderCertification.dateEarned = dateEarned
                leaderCertification.enteredType = LeaderCertificationEnteredType.ManuallyEntered
                leaderCertification.enteredBy = springSecurityService.currentUser
                leaderCertification.dateEntered = new Date()
                leader.addToCertifications(leaderCertification)
                leader.save(failOnError: true)
                trainingService.recalculatePctTrained(leader);

            } catch (Exception e) {
                flash.error = "leaderCertification.create.error"
                render(view: "quickCreate", model: [leaderCertification: leaderCertification, postAction: "processQuickEdit"])
                return
            }
        }

        redirect(controller: "leader", action: "view", id: leaderCertification?.leader?.id)


    }

    def processQuickCreate = {
        LeaderCertification leaderCertification = new LeaderCertification(params)
        Leader leader = leaderCertification.leader

        Date dateEarned
        try {
            dateEarned = Date.parse("MM/dd/yyyy", params.dateEarned)
        } catch (Exception e) {
            e.printStackTrace()
            flash.error = "leaderCertification.create.invalidDate"
            render(view: "quickCreate", model: [leaderCertification: leaderCertification, postAction: "processQuickCreate"])
            return

        }
        if (dateEarned) {
            try {
                leaderCertification.dateEarned = dateEarned
                leaderCertification.enteredType = LeaderCertificationEnteredType.ManuallyEntered
                leaderCertification.enteredBy = springSecurityService.currentUser
                leaderCertification.dateEntered = new Date()
                leader.addToCertifications(leaderCertification)
                leader.save(failOnError: true)
                trainingService.recalculatePctTrained(leader);

            } catch (Exception e) {
                flash.error = "leaderCertification.create.error"
                render(view: "quickCreate", model: [leaderCertification: leaderCertification, postAction: "processQuickCreate"])
                return
            }
        }

        redirect(controller: "leader", action: "view", id: leaderCertification?.leader?.id)
    }

    def certificationOptions = {
        String leaderPositionType = LeaderPositionType.valueOf(params.selectedValue)
        def rtn = [:]

        List<ProgramCertification> certifications = ProgramCertification.findAllByPositionType(leaderPositionType)
        certifications.each {ProgramCertification programCertification ->
            long certificationId = programCertification.certification.id
            rtn[certificationId] = programCertification.certification.toString()
        }
        render rtn as JSON
    }

    def create = {
        def leaderCertificationInstance = new LeaderCertification()
        leaderCertificationInstance.properties = params
        return [leaderCertificationInstance: leaderCertificationInstance]
    }

    def createForm = {
        int certificationId = Integer.parseInt(params.certificationId)
        Certification certification = Certification.get(certificationId)

        int leaderId = Integer.parseInt(params.leaderId)
        Leader leader = Leader.get(leaderId)
        return [certification: certification, leader: leader]
    }

    def save = {
        Date dateEarned = leaderCertificationService.parseCertificationDate(params.dateEarned)
        def leaderCertificationInstance = new LeaderCertification(params)
        leaderCertificationInstance.dateEarned = dateEarned

        boolean successful = leaderCertificationService.saveLeaderCertification(leaderCertificationInstance);
        def rtn = [:]
        if (successful) {
            rtn.success = true
        } else {
            rtn.success = false
            flash.errorObj = leaderCertificationInstance
        }
        render rtn as JSON
    }

    def deleteCertification = {
        def rtn = [:]

        Leader leader = Leader.get(Integer.parseInt(params.leaderId));
        Certification certification = Certification.get(Integer.parseInt(params.certificationId));
        LeaderCertification.findAllByLeaderAndCertification(leader, certification)*.delete(flush: true)
        rtn.success = true
        render rtn as JSON
    }

    def saveCertification = {
        def rtn = [:]

        if (params.dateEarned) {

            Leader leader = Leader.get(Integer.parseInt(params.leaderId));
            Certification certification = Certification.get(Integer.parseInt(params.certificationId));
            Date dateEarned
            try {
                try {
                    dateEarned = Date.parse("MM/dd/yyyy", params.dateEarned)
                } catch (ParseException e) {
                    dateEarned = Date.parse("MM-dd-yyyy", params.dateEarned)
                }
                if (leader && certification && dateEarned) {
                    try {
                        //Let's delete any prior certification record.
                        leader.certifications?.findAll {
                            LeaderCertification leaderCert ->
                            leaderCert.certification.id == certification.id
                        }?.each {
                            leader.removeFromCertifications(it)
                            it.delete(flush: true)
                        }
                        LeaderCertification leaderCertification = new LeaderCertification()
                        leaderCertification.leader = leader
                        leaderCertification.certification = certification
                        leaderCertification.dateEarned = dateEarned
                        leaderCertification.enteredType = LeaderCertificationEnteredType.ManuallyEntered
                        leaderCertification.enteredBy = springSecurityService.currentUser
                        leaderCertification.dateEntered = new Date()
                        leaderCertification.save()
                        leader.addToCertifications(leaderCertification)
                        leader.save()
                        if (leader.hasErrors()) {
                            flash.errorObj = leader
                            rtn.success = false
                        } else {
                            trainingService.recalculatePctTrained(leader);
                            rtn.success = true
                        }


                    } catch (Exception e) {
                        log.error "Error saving", e
                        rtn.success = false
                        flash.error = "Error saving certification: ${e.message}"

                    }
                } else {
                    flash.error = "Unknown error"
                    rtn.success = false
                }
            } catch (ParseException e) {
                rtn.success = false
                flash.error = "Invalid date.  Please enter in the format dd/mm/yyyy"
            } catch (Exception e) {
                rtn.success = false
                flash.error = "Unknown error: ${e.message}"
            }
        } else {
            rtn.success = false
            flash.error = "You must submit a value for date"
        }

        render rtn as JSON
    }

    def show = {
        def leaderCertificationInstance = LeaderCertification.get(params.id)
        if (!leaderCertificationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
            redirect(action: "list")
        }
        else {
            [leaderCertificationInstance: leaderCertificationInstance]
        }
    }

    def edit = {
        def leaderCertificationInstance = LeaderCertification.get(params.id)
        if (!leaderCertificationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [leaderCertificationInstance: leaderCertificationInstance]
        }
    }

    def update = {
        def leaderCertificationInstance = LeaderCertification.get(params.id)
        if (leaderCertificationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (leaderCertificationInstance.version > version) {

                    leaderCertificationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'leaderCertification.label', default: 'LeaderCertification')] as Object[], "Another user has updated this LeaderCertification while you were editing")
                    render(view: "edit", model: [leaderCertificationInstance: leaderCertificationInstance])
                    return
                }
            }
            leaderCertificationInstance.properties = params
            if (!leaderCertificationInstance.hasErrors() && leaderCertificationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), leaderCertificationInstance.id])}"
                redirect(action: "show", id: leaderCertificationInstance.id)
            }
            else {
                render(view: "edit", model: [leaderCertificationInstance: leaderCertificationInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def leaderCertificationInstance = LeaderCertification.get(params.id)
        if (leaderCertificationInstance) {
            try {
                leaderCertificationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
            redirect(action: "list")
        }
    }
}
