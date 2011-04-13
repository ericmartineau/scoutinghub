package scoutcert

import grails.converters.JSON

class LeaderGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def searchableService

    def index = {
        redirect(action: "list", params: params)
    }

    def getApplicablePositions = {
        ScoutGroup leaderGroup = ScoutGroup.get(params.id)
        ScoutUnitType unitType = leaderGroup.unitType
        def rtnList = [:]
        if (unitType == null) {
            LeaderPositionType.values().each{
                if(it.scoutUnitTypes.length == 0) {
                    rtnList[it.name()] = it.name().humanize()
                }
            }
        } else {
            LeaderPositionType.values().findAll {
                return it.scoutUnitTypes.find {it == unitType}
            }?.each {rtnList[it.name()] = it.name().humanize()}
        }
        render rtnList as JSON
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [leaderGroupInstanceList: LeaderGroup.list(params), leaderGroupInstanceTotal: LeaderGroup.count()]
    }

    def create = {
        def leaderGroupInstance = new LeaderGroup()
        leaderGroupInstance.properties = params
        return [leaderGroupInstance: leaderGroupInstance]
    }

    def save = {

        //Strange bug with searchable requires this goofy logic using merge
        def leaderGroupInstance = new LeaderGroup(params)

        LeaderGroup leaderGroupInstance2 = leaderGroupInstance.merge(flush:true)
        if (leaderGroupInstance2 && leaderGroupInstance2.save(flush: true)) {
            leaderGroupInstance = leaderGroupInstance2
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), leaderGroupInstance.id])}"
            redirect(action: "show", id: leaderGroupInstance.id)
        }
        else {
            render(view: "create", model: [leaderGroupInstance: leaderGroupInstance])
        }

    }

    def show = {
        def leaderGroupInstance = LeaderGroup.get(params.id)
        if (!leaderGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            [leaderGroupInstance: leaderGroupInstance]
        }
    }

    def edit = {
        def leaderGroupInstance = LeaderGroup.get(params.id)
        if (!leaderGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [leaderGroupInstance: leaderGroupInstance]
        }
    }

    def update = {
        def leaderGroupInstance = LeaderGroup.get(params.id)
        if (leaderGroupInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (leaderGroupInstance.version > version) {

                    leaderGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'leaderGroup.label', default: 'LeaderGroup')] as Object[], "Another user has updated this LeaderGroup while you were editing")
                    render(view: "edit", model: [leaderGroupInstance: leaderGroupInstance])
                    return
                }
            }
            leaderGroupInstance.properties = params
            if (!leaderGroupInstance.hasErrors() && leaderGroupInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), leaderGroupInstance.id])}"
                redirect(action: "show", id: leaderGroupInstance.id)
            }
            else {
                render(view: "edit", model: [leaderGroupInstance: leaderGroupInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def leaderGroupInstance = LeaderGroup.get(params.id)
        if (leaderGroupInstance) {
            try {
                leaderGroupInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
            redirect(action: "list")
        }
    }
}
