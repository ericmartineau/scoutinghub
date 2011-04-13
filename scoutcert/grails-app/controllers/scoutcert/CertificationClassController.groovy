package scoutcert

class CertificationClassController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def springSecurityService

    def index = {
        redirect(action: "list", params: params)
    }

    def findByCertification = {

        Certification cert = Certification.get(Integer.parseInt(params.certificationId))
        Leader leader = Leader.get(Integer.parseInt(params.leaderId));

        //Look for registered
        CertificationClass registered = leader.certificationClasses?.find {it.certification.id == cert.id}
        if (registered) {
            return [registeredFor: registered]
        } else {
            def availableClasses = CertificationClass.findAllByCertificationAndClassDateGreaterThan(
                    cert, new Date())
            if (availableClasses && availableClasses.size() > 0) {
                return [availableClasses: availableClasses]
            } else {
                render("")
            }
        }


    }

    def register = {
        CertificationClass cert = CertificationClass.get(params.id)
        Leader leader = Leader.get(Integer.parseInt(params.leaderId));

        //Look for registered
        CertificationClass registered = leader.certificationClasses?.find {it.id == cert.id}

        return [registered: registered != null, certificationClass: CertificationClass.get(params.id), leader: Leader.get(Integer.parseInt(params.leaderId))]
    }

    def processRegister = {
        int id = Integer.parseInt(params.id)
        CertificationClass certificationClass = CertificationClass.get(id)
        Leader leader = Leader.get(Integer.parseInt(params.leaderId))
        certificationClass.addToRegistrants(leader)
        leader.addToCertificationClasses(certificationClass)
        certificationClass.save(flush: true)
        leader.save(flush: true)
        render("<script>window.location.reload()</script>")
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [certificationClassInstanceList: CertificationClass.list(params), certificationClassInstanceTotal: CertificationClass.count()]
    }

    def create = {
        def certificationClassInstance = new CertificationClass()
        certificationClassInstance.properties = params
        return [certificationClassInstance: certificationClassInstance]
    }

    def save = {
        def certificationClassInstance = new CertificationClass(params)
        if (certificationClassInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'certificationClass.label', default: 'CertificationClass'), certificationClassInstance.id])}"
            redirect(action: "show", id: certificationClassInstance.id)
        }
        else {
            render(view: "create", model: [certificationClassInstance: certificationClassInstance])
        }
    }

    def show = {
        def certificationClassInstance = CertificationClass.get(params.id)
        if (!certificationClassInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationClass.label', default: 'CertificationClass'), params.id])}"
            redirect(action: "list")
        }
        else {
            [certificationClassInstance: certificationClassInstance]
        }
    }

    def edit = {
        def certificationClassInstance = CertificationClass.get(params.id)
        if (!certificationClassInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationClass.label', default: 'CertificationClass'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [certificationClassInstance: certificationClassInstance]
        }
    }

    def update = {
        def certificationClassInstance = CertificationClass.get(params.id)
        if (certificationClassInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (certificationClassInstance.version > version) {

                    certificationClassInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'certificationClass.label', default: 'CertificationClass')] as Object[], "Another user has updated this CertificationClass while you were editing")
                    render(view: "edit", model: [certificationClassInstance: certificationClassInstance])
                    return
                }
            }
            certificationClassInstance.properties = params
            if (!certificationClassInstance.hasErrors() && certificationClassInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'certificationClass.label', default: 'CertificationClass'), certificationClassInstance.id])}"
                redirect(action: "show", id: certificationClassInstance.id)
            }
            else {
                render(view: "edit", model: [certificationClassInstance: certificationClassInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationClass.label', default: 'CertificationClass'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def certificationClassInstance = CertificationClass.get(params.id)
        if (certificationClassInstance) {
            try {
                certificationClassInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'certificationClass.label', default: 'CertificationClass'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'certificationClass.label', default: 'CertificationClass'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationClass.label', default: 'CertificationClass'), params.id])}"
            redirect(action: "list")
        }
    }
}
