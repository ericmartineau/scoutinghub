package scoutcert

import org.compass.core.engine.SearchEngineQueryParseException
import grails.plugins.springsecurity.Secured
import grails.converters.JSON

@Secured(["ROLE_LEADER"])
class PermissionsController {

    def searchableService
    def springSecurityService
    ScoutGroupService scoutGroupService


    def index = {
    }

    def rebuild = {
        scoutGroupService.reindex()

        searchableService.reindexAll()
        render("Done")
    }

    def leaderQuery = {
        if (!params.leaderQuery?.trim()) {
            return [:]
        }
        try {
            Leader leader = springSecurityService.currentUser
            List<ScoutGroup> allGroups = leader?.groups?.findAll{it.admin}?.collect{String.valueOf(it.scoutGroup.id)}
//            List groupIds = []
//            ScoutGroup.withCriteria {
//                or {
//                    allGroups?.each {ScoutGroup grp->
//                        and {
//                            ge('leftNode', grp.leftNode)
//                            le('rightNode', grp.rightNode)
//                        }
//                    }
//                }
//
//            }?.each {
//                groupIds << String.valueOf(it.id)
//            }

            def results = Leader.search(params.leaderQuery + "*", params
                    , filter: ScoutGroupFilter.createFilter(allGroups)
            );
            return [results: results.results, roles: Role.list()]
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }
    }

    def setPermission = {
        boolean checked = Boolean.parseBoolean(params.checked)
        Leader leader = Leader.get(params.leaderId)
        Role role = Role.get(params.roleId)

        if (checked) {
            LeaderRole.create(leader, role)
        } else {
            LeaderRole.findByLeaderAndRole(leader, role)?.delete()
        }

        def rtn = [success:true]
        render rtn as JSON

    }
}
