package scoutcert

import grails.plugins.springsecurity.SpringSecurityService

class PermissionsTagLib {

    SpringSecurityService springSecurityService

    static namespace = "p"

    def canAdministerGroup = {attrs, body->
        ScoutGroup scoutGroup = attrs.scoutGroup
        if(scoutGroup.canBeAdministeredBy(springSecurityService.currentUser)) {
            out << body()
        }
    }

}
