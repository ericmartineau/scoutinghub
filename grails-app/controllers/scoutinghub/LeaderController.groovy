package scoutinghub

import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService

@Secured(["ROLE_LEADER"])
class LeaderController {

    SpringSecurityService springSecurityService

    LeaderService leaderService

    TrainingService trainingService;

    def index = {
        //@todo this should be secured
        forward(action: 'profile')
    }

    def show = {
        forward(action: "view")
    }

    def profile = {
        forward(action: "view")
    }

    def merge = {
        Leader leaderA = Leader.get(Integer.parseInt(params.leaderA))
        Leader leaderB = Leader.get(Integer.parseInt(params.leaderB))

        return [leaderA: leaderA, leaderB: leaderB]
    }

    def saveProfile = {
        Leader leader = Leader.get(params.id);
        leader.firstName = params.firstName
        leader.lastName = params.lastName
        leader.email = params.email
        leader.phone = params.phone

        if(!leader.save()) {
            flash.leaderError = leader
            flash.error = true
            redirect(action: "view", id:leader.id, params:[edit:true])
        } else {
            redirect(action: "view", id: leader.id)
        }

    }

    def doMerge = {
        Leader leaderA = Leader.get(Integer.parseInt(params.leaderA))
        Leader leaderB = Leader.get(Integer.parseInt(params.leaderB))

        leaderService.mergeLeaders(leaderA,leaderB);
        trainingService.recalculatePctTrained(leaderA);
        redirect(view:"view", id:leaderA.id)

    }

    def accountCreated = {
        Leader leader = springSecurityService.currentUser
        leader.reindex()
        forward(action:'view')
    }

    def view = {
        Date now = new Date()
        Leader leader
        if (params.id) {
            leader = Leader.get(params.id)
        } else {
            leader = springSecurityService.currentUser
        }

        if(!leader) {
            redirect(controller: "login", action: "denied")
            return
        }
        def requiredCertifications
        def certificationInfo = []
        if (leader?.groups?.size() > 0) {

            def c = ProgramCertification.createCriteria()

            requiredCertifications = c.list {
                and {
                    or {
                        inList('unitType', leader.groups?.collect {it.scoutGroup.unitType})
                        inList('positionType', leader.groups?.collect {it.leaderPosition})
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

            def certificationIds = new HashSet();

            requiredCertifications?.each {
                ProgramCertification programCertification ->
                if (!certificationIds.contains(programCertification.certification.id)) {
                    certificationInfo << new LeaderCertificationInfo(leader, programCertification.certification)
                    certificationIds.add(programCertification.certification.id)
                }
            }


        }

        def rtn = [certificationInfo: certificationInfo, leader: leader]

        return rtn


    }

    def training = {}
}
