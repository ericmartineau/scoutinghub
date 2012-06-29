package scoutinghub.infusionsoft

class InfusionsoftFollowUpInfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    InfusionsoftService infusionsoftService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [infusionsoftFollowUpInfoInstanceList: InfusionsoftFollowUpInfo.list(params), infusionsoftFollowUpInfoInstanceTotal: InfusionsoftFollowUpInfo.count()]
    }

    def noInfusionsoftSetup = {

    }

    def create = {
        def infusionsoftFollowUpInfoInstance = new InfusionsoftFollowUpInfo()
        infusionsoftFollowUpInfoInstance.properties = params

        if(!infusionsoftService.configured) {
            forward(action: "noInfusionsoftSetup")
        } else {
            def campaigns = infusionsoftService.apiService.DataService.query("Campaign", 100, 0, [:], ["Id", "Name"])
            return [infusionsoftFollowUpInfoInstance: infusionsoftFollowUpInfoInstance, campaigns:campaigns]
        }
    }

    def save = {
        def infusionsoftFollowUpInfoInstance = new InfusionsoftFollowUpInfo(params)
        if (infusionsoftFollowUpInfoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo'), infusionsoftFollowUpInfoInstance.id])}"
            redirect(action: "show", id: infusionsoftFollowUpInfoInstance.id)
        }
        else {
            render(view: "create", model: [infusionsoftFollowUpInfoInstance: infusionsoftFollowUpInfoInstance])
        }
    }

    def show = {
        def infusionsoftFollowUpInfoInstance = InfusionsoftFollowUpInfo.get(params.id)
        if (!infusionsoftFollowUpInfoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo'), params.id])}"
            redirect(action: "list")
        }
        else {
            [infusionsoftFollowUpInfoInstance: infusionsoftFollowUpInfoInstance]
        }
    }

    def edit = {
        def infusionsoftFollowUpInfoInstance = InfusionsoftFollowUpInfo.get(params.id)
        if (!infusionsoftFollowUpInfoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [infusionsoftFollowUpInfoInstance: infusionsoftFollowUpInfoInstance]
        }
    }

    def update = {
        def infusionsoftFollowUpInfoInstance = InfusionsoftFollowUpInfo.get(params.id)
        if (infusionsoftFollowUpInfoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (infusionsoftFollowUpInfoInstance.version > version) {

                    infusionsoftFollowUpInfoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo')] as Object[], "Another user has updated this InfusionsoftFollowUpInfo while you were editing")
                    render(view: "edit", model: [infusionsoftFollowUpInfoInstance: infusionsoftFollowUpInfoInstance])
                    return
                }
            }
            infusionsoftFollowUpInfoInstance.properties = params
            if (!infusionsoftFollowUpInfoInstance.hasErrors() && infusionsoftFollowUpInfoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo'), infusionsoftFollowUpInfoInstance.id])}"
                redirect(action: "show", id: infusionsoftFollowUpInfoInstance.id)
            }
            else {
                render(view: "edit", model: [infusionsoftFollowUpInfoInstance: infusionsoftFollowUpInfoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def infusionsoftFollowUpInfoInstance = InfusionsoftFollowUpInfo.get(params.id)
        if (infusionsoftFollowUpInfoInstance) {
            try {
                infusionsoftFollowUpInfoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo'), params.id])}"
            redirect(action: "list")
        }
    }
}
