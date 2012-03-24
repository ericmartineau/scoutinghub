package scoutinghub.meritbadge

class MeritBadgeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [meritBadgeInstanceList: MeritBadge.list(params), meritBadgeInstanceTotal: MeritBadge.count()]
    }

    def create = {
        def meritBadgeInstance = new MeritBadge()
        meritBadgeInstance.properties = params
        return [meritBadgeInstance: meritBadgeInstance]
    }

    def save = {
        def meritBadgeInstance = new MeritBadge(params)
        if (meritBadgeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'meritBadge.label', default: 'MeritBadge'), meritBadgeInstance.id])}"
            redirect(action: "show", id: meritBadgeInstance.id)
        }
        else {
            render(view: "create", model: [meritBadgeInstance: meritBadgeInstance])
        }
    }

    def show = {
        def meritBadgeInstance = MeritBadge.get(params.id)
        if (!meritBadgeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meritBadge.label', default: 'MeritBadge'), params.id])}"
            redirect(action: "list")
        }
        else {
            [meritBadgeInstance: meritBadgeInstance]
        }
    }

    def edit = {
        def meritBadgeInstance = MeritBadge.get(params.id)
        if (!meritBadgeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meritBadge.label', default: 'MeritBadge'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [meritBadgeInstance: meritBadgeInstance]
        }
    }

    def update = {
        def meritBadgeInstance = MeritBadge.get(params.id)
        if (meritBadgeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (meritBadgeInstance.version > version) {
                    
                    meritBadgeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'meritBadge.label', default: 'MeritBadge')] as Object[], "Another user has updated this MeritBadge while you were editing")
                    render(view: "edit", model: [meritBadgeInstance: meritBadgeInstance])
                    return
                }
            }
            meritBadgeInstance.properties = params
            if (!meritBadgeInstance.hasErrors() && meritBadgeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'meritBadge.label', default: 'MeritBadge'), meritBadgeInstance.id])}"
                redirect(action: "show", id: meritBadgeInstance.id)
            }
            else {
                render(view: "edit", model: [meritBadgeInstance: meritBadgeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meritBadge.label', default: 'MeritBadge'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def meritBadgeInstance = MeritBadge.get(params.id)
        if (meritBadgeInstance) {
            try {
                meritBadgeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'meritBadge.label', default: 'MeritBadge'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'meritBadge.label', default: 'MeritBadge'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meritBadge.label', default: 'MeritBadge'), params.id])}"
            redirect(action: "list")
        }
    }
}
