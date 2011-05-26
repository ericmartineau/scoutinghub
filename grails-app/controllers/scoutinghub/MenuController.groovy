package scoutinghub

import grails.plugins.springsecurity.Secured

@Secured(["ROLE_LEADER"])
class MenuController {

    def show = {

    }
}
