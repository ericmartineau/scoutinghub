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

    def profile = {
        forward(action:"view")
    }

    def view = {
        Leader leader
        if (params.id) {
            leader = Leader.get(params.id)
        } else {
            leader = springSecurityService.currentUser
        }
        def requiredCertifications
        def certificationInfo = []
        if(leader.groups?.size() > 0) {

            def c = ProgramCertification.createCriteria()

            requiredCertifications = c.list {
                and {
                    or {
                        inList('unitType', leader.groups?.collect {it.scoutGroup.unitType})
                        inList('positionType', leader.groups?.collect {it.position})
                    }
                    eq('required', true)
                }
                certification {
                    sort: 'name'
                }

            }

            requiredCertifications?.each{
                ProgramCertification programCertification->
                certificationInfo << new LeaderCertificationInfo(leader, programCertification.certification)
            }



        }

        def rtn = [certificationInfo: certificationInfo, leader: leader]

        return rtn


    }

    def training = {}
}
