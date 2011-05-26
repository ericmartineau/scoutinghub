package scoutinghub

import grails.plugins.springsecurity.SpringSecurityService

class ScoutGroupTagLib {

    SpringSecurityService springSecurityService

    def leaderName = {attrs->
        Leader leader = attrs.leader;

        if (leader?.id == springSecurityService.currentUser?.id) {
            out << message(code: attrs.selfName) ?: " this person "
        } else {
            out << leader?.firstName ?: " this person "
        }
    }

    def ifSelf = {attrs, body->
        Leader leader = attrs.leader
        if(leader?.id == springSecurityService.currentUser?.id) {
            out << body();
        }
    }

    def ifNotSelf = {attrs, body->
        Leader leader = attrs.leader
        if(leader?.id != springSecurityService.currentUser?.id) {
            out << body();
        }
    }


}
