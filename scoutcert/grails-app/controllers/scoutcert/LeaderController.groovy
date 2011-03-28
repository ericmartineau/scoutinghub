package scoutcert

import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService

@Secured(["ROLE_LEADER"])
class LeaderController {

    SpringSecurityService springSecurityService


    def index = {
        forward(action:'profile')
    }

    def profile = {
        return [leader: springSecurityService.currentUser]
    }

    def training = {}
}
