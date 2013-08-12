package scoutinghub.meritbadge

import grails.plugins.springsecurity.Secured
import scoutinghub.LeaderPositionType
import scoutinghub.ScoutGroup

@Secured("ROLE_LEADER")
class MeritBadgeCounselorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def find     = {
//        def meritBadgesById = [:]
//        MeritBadge.list()?.each {
//            meritBadgesById[it.id] = it
//        }
//        return [meritBadgesById: meritBadgesById]

    }
    
    def performCounselorSearch = {
        Long badgeId = Long.parseLong(params.badge)
        Long unitId = Long.parseLong(params.unit)
        
        ScoutGroup group = ScoutGroup.read(unitId)
        def list = MeritBadgeCounselor.withCriteria {
            leader {
                groups {
                    eq('leaderPosition', LeaderPositionType.MeritBadgeCounselor)
                    scoutGroup {
                        le('leftNode', group.leftNode)
                        ge('rightNode', group.rightNode)
                    }
                }
            }
            badges {
                eq('id', badgeId)
            }
        }
        def results = list.collect{it.leader}
        return [results:results]
        
    }

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [meritBadgeCounselorInstanceList: MeritBadgeCounselor.list(params), meritBadgeCounselorInstanceTotal: MeritBadgeCounselor.count()]
    }

    def create = {
        def meritBadgeCounselorInstance = new MeritBadgeCounselor()
        meritBadgeCounselorInstance.properties = params
        return [meritBadgeCounselorInstance: meritBadgeCounselorInstance]
    }

    def save = {
        def meritBadgeCounselorInstance = new MeritBadgeCounselor(params)
        if (meritBadgeCounselorInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor'), meritBadgeCounselorInstance.id])}"
            redirect(action: "show", id: meritBadgeCounselorInstance.id)
        }
        else {
            render(view: "create", model: [meritBadgeCounselorInstance: meritBadgeCounselorInstance])
        }
    }

    def show = {
        MeritBadgeCounselor meritBadgeCounselor = MeritBadgeCounselor.get(params.id)
        redirect(controller: 'leader', action: 'view', id: meritBadgeCounselor.leader.id)
    }

    def edit = {
        def meritBadgeCounselorInstance = MeritBadgeCounselor.get(params.id)
        if (!meritBadgeCounselorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [meritBadgeCounselorInstance: meritBadgeCounselorInstance]
        }
    }

    def update = {
        def meritBadgeCounselorInstance = MeritBadgeCounselor.get(params.id)
        if (meritBadgeCounselorInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (meritBadgeCounselorInstance.version > version) {

                    meritBadgeCounselorInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor')] as Object[], "Another user has updated this MeritBadgeCounselor while you were editing")
                    render(view: "edit", model: [meritBadgeCounselorInstance: meritBadgeCounselorInstance])
                    return
                }
            }
            meritBadgeCounselorInstance.properties = params

            def badges = []
            meritBadgeCounselorInstance?.badges?.each {badges << it}

            badges.each {
                meritBadgeCounselorInstance.removeFromBadges(it)
            }
            
            params.badges?.each {
                meritBadgeCounselorInstance.addToBadges(MeritBadge.get(it))
            }

            if (!meritBadgeCounselorInstance.hasErrors() && meritBadgeCounselorInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor'), meritBadgeCounselorInstance.id])}"
                redirect(action: "show", id: meritBadgeCounselorInstance.id)
            }
            else {
                render(view: "edit", model: [meritBadgeCounselorInstance: meritBadgeCounselorInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def meritBadgeCounselorInstance = MeritBadgeCounselor.get(params.id)
        if (meritBadgeCounselorInstance) {
            try {
                meritBadgeCounselorInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor'), params.id])}"
            redirect(action: "list")
        }
    }
}
