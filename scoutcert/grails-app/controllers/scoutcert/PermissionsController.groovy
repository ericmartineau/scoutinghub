package scoutcert

import org.compass.core.engine.SearchEngineQueryParseException
import grails.plugins.springsecurity.Secured
import grails.converters.JSON

@Secured(["ROLE_ADMIN"])
class PermissionsController {

    def searchableService

    def index = {
    }

    def rebuild = {
        searchableService.reindexAll()
        render("Done")
    }

    def leaderQuery = {
        if (!params.leaderQuery?.trim()) {
            return [:]
        }
        try {
            def results = Leader.search(params.leaderQuery + "*", params)
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
