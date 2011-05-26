package scoutcert

import grails.plugins.springsecurity.SpringSecurityService

class PermissionsTagLib {

    SpringSecurityService springSecurityService

    static namespace = "p"

    def canAdministerGroup = {attrs, body ->
        ScoutGroup scoutGroup = attrs.scoutGroup
        if (scoutGroup.canBeAdministeredBy(springSecurityService.currentUser)) {
            out << body()
        }
    }

    def allGroupPermissions = {attrs ->
        Leader leader = attrs.leader
        def units = new LinkedHashMap()
        leader.groups.each {LeaderGroup it ->
            def currGrp = it.scoutGroup
            while (currGrp) {
                if (!units.containsKey(currGrp.id)) {
                    units[currGrp.id] = currGrp
                }
                currGrp = currGrp.parent
            }

        }
        units.values().each {ScoutGroup group ->
            out << singleGroupPermissions(group: group, leader: leader)
        }
    }

    def singleGroupPermissions = {attrs ->

        ScoutGroup group = attrs.group;
        if (group.canBeAdministeredBy(springSecurityService.currentUser)) {
            Leader leader = attrs.leader
            boolean checked = leader.groups?.find {it.scoutGroup?.id == group?.id}?.admin
            out << "<li class='permission'>"
            out << "<div class='permission-label permission-level-${attrs.level}'>"
            out << group
            out << "</div>"
            out << "<div class='permission-checkbox permission-level-${attrs.level}'>"
            out << checkBox(name: "grp" + group.id, checked: checked, value: true)
            out << "</div>"
            out << "</li>"
        }


    }
}

