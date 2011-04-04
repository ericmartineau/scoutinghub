package scoutcert

import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService

@Secured(["ROLE_LEADER"])
class LeaderController {

    SpringSecurityService springSecurityService


    def index = {
        forward(action: 'profile')
    }

    def profile = {
        def now = new Date()
        def c = ProgramCertification.createCriteria()
        Leader leader = springSecurityService.currentUser
        def requiredCertifications = c.list {

            and {
                or {
                    inList('unitType', leader.groups?.collect {it.scoutGroup.unitType})
                    inList('positionType', leader.groups?.collect {it.position})
                }
                eq('required', true)
                lt('startDate', now)
                gt('endDate', now)
            }
        }

        int countCompleted = 0
        requiredCertifications?.each {
            if(leader.hasCertification(it.certification)) {
                countCompleted++
            }
        }

        def rtn = [requiredCertifications: requiredCertifications, leader: leader]
        if (requiredCertifications?.size() > 0 && countCompleted > 0) {
            rtn.trainingCompletePct = countCompleted / requiredCertifications?.size() * 100
        }
        return rtn
    }

    def view = {
        Leader leader = Leader.get (params.id)
        render(view: "profile", model: [leader: leader])
    }

    def training = {}
}
