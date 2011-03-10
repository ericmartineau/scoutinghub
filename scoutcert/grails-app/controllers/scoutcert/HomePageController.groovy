package scoutcert

import grails.plugins.springsecurity.Secured

@Secured(["ROLE_LEADER"])
class HomePageController {

    def index = {

    }
}
