package scoutinghub

import grails.plugins.springsecurity.SpringSecurityService

class PermissionsTagLib {

    SpringSecurityService springSecurityService

    static namespace = "p"

    def canAdministerGroup = {attrs, body ->
        ScoutGroup scoutGroup = attrs.scoutGroup
        Leader leader = attrs.leader ?: springSecurityService.currentUser
        if (scoutGroup.canBeAdministeredBy(leader)) {
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
            request.hadPermissions = true
            Leader leader = attrs.leader
            boolean checked = leader.groups?.find {it.scoutGroup?.id == group?.id}?.admin
            out << singlePermission(checked: checked, label: group?.toString()?.trimTo(24), id: group?.id)
        }
    }

    def singlePermission = {attrs ->
        def label = attrs.label
        def id = attrs.id
        def checked = attrs.checked
        out << s.checkbox(name: "grp" + id, checked: checked, value: true, code:label)
//        out << "<li class='permission ${attrs.class ?: ''}'>"
//        out << "<div class='permission-label'>"
//        out << label
//        out << "</div>"
//        out << "<div class='permission-checkbox'>"
//        out << checkBox()
//        out << "</div>"
//        out << "</li>"
    }
}

