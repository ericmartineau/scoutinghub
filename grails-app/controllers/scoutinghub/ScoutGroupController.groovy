package scoutinghub

import grails.plugins.springsecurity.Secured
import org.compass.core.engine.SearchEngineQueryParseException

@Secured(["ROLE_ADMIN"])
class ScoutGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    ScoutGroupService scoutGroupService

    @Secured(["ROLE_LEADER"])
    def report = {
        redirect(controller: "training", action: "trainingReport", id: params.id)
    }

    def makeAdmin = {
        //todo: Fix permissions here
        LeaderGroup leaderGroup = LeaderGroup.get(params.id)
        leaderGroup.admin = true
        leaderGroup.save(failOfError: true)

        redirect(controller: "leader", view: "view", id: leaderGroup.leader.id)
    }

    @Secured(["ROLE_ADMIN"])
    def unitQuery = {
        def searchParam = params.param?.trim()
        def orgTypeParam = params.orgType?.trim()
        if (!searchParam && !orgTypeParam) {
            return [:]
        }
        try {

//            def results = ScoutGroup.search(searchQuery, params)
            def results = ScoutGroup.search {
                if(orgTypeParam) {
                    must(term('groupType', orgTypeParam?.toLowerCase()))
                }
                if(searchParam) {
                    queryString("${searchParam}*")
                }
            }
            return [results: results.results]
        } catch (Exception ex) {
            return [parseException: true]
        }
    }

    def reindex = {
        scoutGroupService.reindex()
        render("Done")

    }

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [scoutGroupInstanceList: ScoutGroup.list(params), scoutGroupInstanceTotal: ScoutGroup.count()]
    }

    def create = {
        def scoutGroupInstance = new ScoutGroup()
        scoutGroupInstance.properties = params
        return [scoutGroupInstance: scoutGroupInstance]
    }

    def save = {
        def scoutGroupInstance = new ScoutGroup(params)
        scoutGroupInstance = scoutGroupInstance.merge() ?: scoutGroupInstance
        if (scoutGroupInstance.save(flush: true)) {
            ScoutGroup.reindex()
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), scoutGroupInstance.id])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [scoutGroupInstance: scoutGroupInstance])
        }
    }

    def show = {
        def scoutGroupInstance = ScoutGroup.get(params.id)
        if (!scoutGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            [scoutGroupInstance: scoutGroupInstance]
        }
    }

    def edit = {
        def scoutGroupInstance = ScoutGroup.get(params.id)
        if (!scoutGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [scoutGroupInstance: scoutGroupInstance]
        }
    }

    def update = {
        def scoutGroupInstance = ScoutGroup.get(params.id)
        if (scoutGroupInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (scoutGroupInstance.version > version) {

                    scoutGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'scoutGroup.label', default: 'ScoutGroup')] as Object[], "Another user has updated this ScoutGroup while you were editing")
                    render(view: "show", model: [scoutGroupInstance: scoutGroupInstance])
                    return
                }
            }
            scoutGroupInstance.properties = params
            if (!scoutGroupInstance.hasErrors() && scoutGroupInstance.save(flush: true)) {
                ScoutGroup.reindex()
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), scoutGroupInstance.id])}"
                redirect(action: "show", id: scoutGroupInstance.id)
            }
            else {
                render(view: "show", model: [scoutGroupInstance: scoutGroupInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def scoutGroupInstance = ScoutGroup.get(params.id)
        if (scoutGroupInstance) {
            try {
                scoutGroupInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), params.id])}"
            redirect(action: "list")
        }
    }
}
