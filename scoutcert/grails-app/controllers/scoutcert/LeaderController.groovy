package scoutcert

import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService

@Secured(["ROLE_LEADER"])
class LeaderController {

    SpringSecurityService springSecurityService

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

    def doMerge = {
        Leader leaderA = Leader.get(Integer.parseInt(params.leaderA))
        Leader leaderB = Leader.get(Integer.parseInt(params.leaderB))

        leaderB.delete()
        redirect(view:"view", id:leaderA.id)

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
