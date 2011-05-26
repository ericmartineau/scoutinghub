package scoutinghub

class ProgramCertificationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [programCertificationInstanceList: ProgramCertification.list(params), programCertificationInstanceTotal: ProgramCertification.count()]
    }

    def create = {
        def programCertificationInstance = new ProgramCertification()
        programCertificationInstance.properties = params
        return [programCertificationInstance: programCertificationInstance]
    }

    def save = {
        def programCertificationInstance = new ProgramCertification(params)
        if (programCertificationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'programCertification.label', default: 'ProgramCertification'), programCertificationInstance.id])}"
            redirect(action: "show", id: programCertificationInstance.id)
        }
        else {
            render(view: "create", model: [programCertificationInstance: programCertificationInstance])
        }
    }

    def show = {
        def programCertificationInstance = ProgramCertification.get(params.id)
        if (!programCertificationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'programCertification.label', default: 'ProgramCertification'), params.id])}"
            redirect(action: "list")
        }
        else {
            [programCertificationInstance: programCertificationInstance]
        }
    }

    def edit = {
        def programCertificationInstance = ProgramCertification.get(params.id)
        if (!programCertificationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'programCertification.label', default: 'ProgramCertification'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [programCertificationInstance: programCertificationInstance]
        }
    }

    def update = {
        def programCertificationInstance = ProgramCertification.get(params.id)
        if (programCertificationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (programCertificationInstance.version > version) {
                    
                    programCertificationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'programCertification.label', default: 'ProgramCertification')] as Object[], "Another user has updated this ProgramCertification while you were editing")
                    render(view: "edit", model: [programCertificationInstance: programCertificationInstance])
                    return
                }
            }
            programCertificationInstance.properties = params
            if (!programCertificationInstance.hasErrors() && programCertificationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'programCertification.label', default: 'ProgramCertification'), programCertificationInstance.id])}"
                redirect(action: "show", id: programCertificationInstance.id)
            }
            else {
                render(view: "edit", model: [programCertificationInstance: programCertificationInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'programCertification.label', default: 'ProgramCertification'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def programCertificationInstance = ProgramCertification.get(params.id)
        if (programCertificationInstance) {
            try {
                programCertificationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'programCertification.label', default: 'ProgramCertification'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'programCertification.label', default: 'ProgramCertification'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'programCertification.label', default: 'ProgramCertification'), params.id])}"
            redirect(action: "list")
        }
    }
}
