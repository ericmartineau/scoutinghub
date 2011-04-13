package scoutcert

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class CertificationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [certificationInstanceList: Certification.list(params), certificationInstanceTotal: Certification.count()]
    }

    def create = {
        def certificationInstance = new Certification()
        certificationInstance.properties = params
        return [certificationInstance: certificationInstance]
    }

    def save = {
        def certificationInstance = new Certification(params)
        if (certificationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'certification.label', default: 'Certification'), certificationInstance.id])}"
            redirect(action: "show", id: certificationInstance.id)
        }
        else {
            render(view: "create", model: [certificationInstance: certificationInstance])
        }
    }

    def show = {
        def certificationInstance = Certification.get(params.id)
        if (!certificationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certification.label', default: 'Certification'), params.id])}"
            redirect(action: "list")
        }
        else {
            [certificationInstance: certificationInstance]
        }
    }

    def edit = {
        def certificationInstance = Certification.get(params.id)
        if (!certificationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certification.label', default: 'Certification'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [certificationInstance: certificationInstance]
        }
    }

    def update = {
        def certificationInstance = Certification.get(params.id)
        if (certificationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (certificationInstance.version > version) {
                    
                    certificationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'certification.label', default: 'Certification')] as Object[], "Another user has updated this Certification while you were editing")
                    render(view: "edit", model: [certificationInstance: certificationInstance])
                    return
                }
            }
            certificationInstance.properties = params
            if (!certificationInstance.hasErrors() && certificationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'certification.label', default: 'Certification'), certificationInstance.id])}"
                redirect(action: "show", id: certificationInstance.id)
            }
            else {
                render(view: "edit", model: [certificationInstance: certificationInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certification.label', default: 'Certification'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def certificationInstance = Certification.get(params.id)
        if (certificationInstance) {
            try {
                certificationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'certification.label', default: 'Certification'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'certification.label', default: 'Certification'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certification.label', default: 'Certification'), params.id])}"
            redirect(action: "list")
        }
    }
}
