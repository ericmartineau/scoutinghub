package scoutinghub

import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.security.access.AccessDeniedException

@Secured(["ROLE_ADMIN"])
class ScoutGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    ScoutGroupService scoutGroupService
    SpringSecurityService springSecurityService

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

    @Secured(["ROLE_LEADER"])
    def unitQuery = {

        Leader leader = springSecurityService.currentUser
        def searchParam = params.param?.trim()
        def orgTypeParam = params.orgType?.trim()
        if (!searchParam && !orgTypeParam) {
            return [:]
        }
        try {

            List<String> allGroups = leader?.groups?.findAll {it.admin}?.collect {String.valueOf(it.scoutGroup.id)}

//            def results = ScoutGroup.search(searchQuery, params)
            def searchClosure = {
                if (orgTypeParam) {
                    must(term('groupType', orgTypeParam?.toLowerCase()))
                }
                if (searchParam) {
                    queryString("${searchParam}*")
                }
                // Leader.search(params.leaderQuery?.trim() + "*", params, filter: ScoutGroupFilter.createFilter(allGroups));
                //If not an admin, only allow searching on unit admins
                //def results = ScoutGroup.search(params.term.trim() + "*", defaultOperator:"or", properties:
            }
            def results
            if(leader.hasRole("ROLE_ADMIN")) {
                results = ScoutGroup.search(searchClosure)
            } else {
                results = ScoutGroup.search(searchClosure, filter: ScoutGroupFilter.createFilter(allGroups))
            }

            results.results?.each {it.refresh()}

            return [results: results.results]
        } catch (Exception ex) {
            log.error "Error retrieveing results", ex
            return [parseException: true]
        }
    }

    def reindex = {
        scoutGroupService.reindex()
        render("Done")

    }

    @Secured(["ROLE_LEADER"])
    def index = {
        forward(action: "list")
    }

    @Secured(["ROLE_LEADER"])
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
            scoutGroupService.reindex()
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), scoutGroupInstance.id])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [scoutGroupInstance: scoutGroupInstance])
        }
    }

    @Secured(["ROLE_LEADER"])
    def show = {
        def scoutGroupInstance = ScoutGroup.get(params.id)

        if (!scoutGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            Leader leader = springSecurityService.currentUser
            if (!scoutGroupInstance.canBeAdministeredBy(leader)) {
                throw new AccessDeniedException("Unable to see this unit")
            }

            [scoutGroupInstance: scoutGroupInstance]
        }
    }

    @Secured(["ROLE_LEADER"])
    def edit = {
        def scoutGroupInstance = ScoutGroup.get(params.id)
        if (!scoutGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            Leader leader = springSecurityService.currentUser
            if (!scoutGroupInstance.canBeAdministeredBy(leader)) {
                throw new AccessDeniedException("Unable to see this unit")
            }
            return [scoutGroupInstance: scoutGroupInstance]
        }
    }


    @Secured(["ROLE_LEADER"])
    def update = {
        def scoutGroupInstance = ScoutGroup.get(params.id)

        Leader leader = springSecurityService.currentUser
        if (!scoutGroupInstance.canBeAdministeredBy(leader)) {
            throw new AccessDeniedException("Unable to see this unit")
        }
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
                scoutGroupService.reindex()

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
