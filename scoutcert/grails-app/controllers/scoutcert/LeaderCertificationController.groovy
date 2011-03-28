package scoutcert

class LeaderCertificationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [leaderCertificationInstanceList: LeaderCertification.list(params), leaderCertificationInstanceTotal: LeaderCertification.count()]
    }

    def create = {
        def leaderCertificationInstance = new LeaderCertification()
        leaderCertificationInstance.properties = params
        return [leaderCertificationInstance: leaderCertificationInstance]
    }

    def save = {
        def leaderCertificationInstance = new LeaderCertification(params)
        if (leaderCertificationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), leaderCertificationInstance.id])}"
            redirect(action: "show", id: leaderCertificationInstance.id)
        }
        else {
            render(view: "create", model: [leaderCertificationInstance: leaderCertificationInstance])
        }
    }

    def show = {
        def leaderCertificationInstance = LeaderCertification.get(params.id)
        if (!leaderCertificationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
            redirect(action: "list")
        }
        else {
            [leaderCertificationInstance: leaderCertificationInstance]
        }
    }

    def edit = {
        def leaderCertificationInstance = LeaderCertification.get(params.id)
        if (!leaderCertificationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [leaderCertificationInstance: leaderCertificationInstance]
        }
    }

    def update = {
        def leaderCertificationInstance = LeaderCertification.get(params.id)
        if (leaderCertificationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (leaderCertificationInstance.version > version) {
                    
                    leaderCertificationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'leaderCertification.label', default: 'LeaderCertification')] as Object[], "Another user has updated this LeaderCertification while you were editing")
                    render(view: "edit", model: [leaderCertificationInstance: leaderCertificationInstance])
                    return
                }
            }
            leaderCertificationInstance.properties = params
            if (!leaderCertificationInstance.hasErrors() && leaderCertificationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), leaderCertificationInstance.id])}"
                redirect(action: "show", id: leaderCertificationInstance.id)
            }
            else {
                render(view: "edit", model: [leaderCertificationInstance: leaderCertificationInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def leaderCertificationInstance = LeaderCertification.get(params.id)
        if (leaderCertificationInstance) {
            try {
                leaderCertificationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification'), params.id])}"
            redirect(action: "list")
        }
    }
}
