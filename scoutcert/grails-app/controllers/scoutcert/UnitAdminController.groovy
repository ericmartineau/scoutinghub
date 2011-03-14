package scoutcert

import grails.plugins.springsecurity.Secured

@Secured(["ROLE_UNITADMIN"])
class UnitAdminController {

    def index = {
        forward(action: 'tools')
    }

    def tools = { }

    def reports = {}
}
