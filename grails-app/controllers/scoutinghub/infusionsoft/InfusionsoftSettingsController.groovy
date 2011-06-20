package scoutinghub.infusionsoft

class InfusionsoftSettingsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [infusionsoftSettingsInstanceList: InfusionsoftSettings.list(params), infusionsoftSettingsInstanceTotal: InfusionsoftSettings.count()]
    }

    def create = {
        def infusionsoftSettingsInstance = new InfusionsoftSettings()
        infusionsoftSettingsInstance.properties = params
        return [infusionsoftSettingsInstance: infusionsoftSettingsInstance]
    }

    def save = {
        def infusionsoftSettingsInstance = new InfusionsoftSettings(params)
        if (infusionsoftSettingsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'infusionsoftSettings.label', default: 'InfusionsoftSettings'), infusionsoftSettingsInstance.id])}"
            redirect(action: "show", id: infusionsoftSettingsInstance.id)
        }
        else {
            render(view: "create", model: [infusionsoftSettingsInstance: infusionsoftSettingsInstance])
        }
    }

    def show = {
        def infusionsoftSettingsInstance = InfusionsoftSettings.get(params.id)
        if (!infusionsoftSettingsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infusionsoftSettings.label', default: 'InfusionsoftSettings'), params.id])}"
            redirect(action: "list")
        }
        else {
            [infusionsoftSettingsInstance: infusionsoftSettingsInstance]
        }
    }

    def edit = {
        def infusionsoftSettingsInstance = InfusionsoftSettings.get(params.id)
        if (!infusionsoftSettingsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infusionsoftSettings.label', default: 'InfusionsoftSettings'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [infusionsoftSettingsInstance: infusionsoftSettingsInstance]
        }
    }

    def update = {
        def infusionsoftSettingsInstance = InfusionsoftSettings.get(params.id)
        if (infusionsoftSettingsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (infusionsoftSettingsInstance.version > version) {

                    infusionsoftSettingsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'infusionsoftSettings.label', default: 'InfusionsoftSettings')] as Object[], "Another user has updated this InfusionsoftSettings while you were editing")
                    render(view: "edit", model: [infusionsoftSettingsInstance: infusionsoftSettingsInstance])
                    return
                }
            }
            infusionsoftSettingsInstance.properties = params
            if (!infusionsoftSettingsInstance.hasErrors() && infusionsoftSettingsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'infusionsoftSettings.label', default: 'InfusionsoftSettings'), infusionsoftSettingsInstance.id])}"
                redirect(action: "show", id: infusionsoftSettingsInstance.id)
            }
            else {
                render(view: "edit", model: [infusionsoftSettingsInstance: infusionsoftSettingsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infusionsoftSettings.label', default: 'InfusionsoftSettings'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def infusionsoftSettingsInstance = InfusionsoftSettings.get(params.id)
        if (infusionsoftSettingsInstance) {
            try {
                infusionsoftSettingsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'infusionsoftSettings.label', default: 'InfusionsoftSettings'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'infusionsoftSettings.label', default: 'InfusionsoftSettings'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infusionsoftSettings.label', default: 'InfusionsoftSettings'), params.id])}"
            redirect(action: "list")
        }
    }
}
