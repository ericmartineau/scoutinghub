package scoutinghub

class CertificationCodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [certificationCodeInstanceList: CertificationCode.list(params), certificationCodeInstanceTotal: CertificationCode.count()]
    }

    def create = {
        def certificationCodeInstance = new CertificationCode()
        certificationCodeInstance.properties = params
        return [certificationCodeInstance: certificationCodeInstance]
    }

    def save = {
        def certificationCodeInstance = new CertificationCode(params)
        if (certificationCodeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'certificationCode.label', default: 'CertificationCode'), certificationCodeInstance.id])}"
            redirect(action: "show", id: certificationCodeInstance.id)
        }
        else {
            render(view: "create", model: [certificationCodeInstance: certificationCodeInstance])
        }
    }

    def show = {
        def certificationCodeInstance = CertificationCode.get(params.id)
        if (!certificationCodeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationCode.label', default: 'CertificationCode'), params.id])}"
            redirect(action: "list")
        }
        else {
            [certificationCodeInstance: certificationCodeInstance]
        }
    }

    def edit = {
        def certificationCodeInstance = CertificationCode.get(params.id)
        if (!certificationCodeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationCode.label', default: 'CertificationCode'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [certificationCodeInstance: certificationCodeInstance]
        }
    }

    def update = {
        def certificationCodeInstance = CertificationCode.get(params.id)
        if (certificationCodeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (certificationCodeInstance.version > version) {
                    
                    certificationCodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'certificationCode.label', default: 'CertificationCode')] as Object[], "Another user has updated this CertificationCode while you were editing")
                    render(view: "edit", model: [certificationCodeInstance: certificationCodeInstance])
                    return
                }
            }
            certificationCodeInstance.properties = params
            if (!certificationCodeInstance.hasErrors() && certificationCodeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'certificationCode.label', default: 'CertificationCode'), certificationCodeInstance.id])}"
                redirect(action: "show", id: certificationCodeInstance.id)
            }
            else {
                render(view: "edit", model: [certificationCodeInstance: certificationCodeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationCode.label', default: 'CertificationCode'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def certificationCodeInstance = CertificationCode.get(params.id)
        if (certificationCodeInstance) {
            try {
                certificationCodeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'certificationCode.label', default: 'CertificationCode'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'certificationCode.label', default: 'CertificationCode'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationCode.label', default: 'CertificationCode'), params.id])}"
            redirect(action: "list")
        }
    }
}
