package scoutcert

import grails.plugins.springsecurity.Secured

@Secured(["ROLE_ADMIN"])
class AdminController {

    def index = {
        forward(action: 'setup')
    }
    def setup = { }
}
