package scoutcert

import grails.plugins.springsecurity.Secured

@Secured(["ROLE_LEADER"])
class LeaderController {

    def index = {
        forward(action:'profile')
    }

    def profile = {}

    def training = {}
}
