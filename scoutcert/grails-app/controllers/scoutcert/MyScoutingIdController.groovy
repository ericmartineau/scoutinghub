package scoutcert

class MyScoutingIdController {

    def searchableService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

//    def list = {
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
//        [myScoutingIdInstanceList: MyScoutingId.list(params), myScoutingIdInstanceTotal: MyScoutingId.count()]
//    }

    def create = {
        def myScoutingIdInstance = new MyScoutingId()
        myScoutingIdInstance.properties = params
        return [myScoutingIdInstance: myScoutingIdInstance]
    }

    def save = {
        def myScoutingIdInstance = new MyScoutingId(params)

        if(params.myScoutingIdentifier) {
            if(MyScoutingId.findByMyScoutingIdentifier(params.myScoutingIdentifier)) {
                flash.info = 'myScoutingId.myScoutingIdentifier.unique'
                flash.info2 = 'myScoutingId.myScoutingIdentifier.uniqueExtra'
                render(view: "create", model: [myScoutingIdInstance: myScoutingIdInstance])
                return
            }
        }


        searchableService.stopMirroring()
        try {
            if (myScoutingIdInstance.save(flush: true)) {

                flash.message = "${message(code: 'default.created.message', args: [message(code: 'myScoutingId.label', default: 'MyScoutingId'), myScoutingIdInstance.id])}"
                redirect(action: "show", id: myScoutingIdInstance.id)
            }
            else {
                render(view: "create", model: [myScoutingIdInstance: myScoutingIdInstance])
            }
        } finally {
            searchableService.startMirroring()
            if(myScoutingIdInstance?.id > 0) {
                myScoutingIdInstance.reindex()
            }
        }
    }

    def show = {
        def myScoutingIdInstance = MyScoutingId.get(params.id)
        if (!myScoutingIdInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'myScoutingId.label', default: 'MyScoutingId'), params.id])}"
            redirect(action: "list")
        }
        else {
            [myScoutingIdInstance: myScoutingIdInstance]
        }
    }

    def edit = {
        def myScoutingIdInstance = MyScoutingId.get(params.id)
        if (!myScoutingIdInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'myScoutingId.label', default: 'MyScoutingId'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [myScoutingIdInstance: myScoutingIdInstance]
        }
    }

    def update = {
        def myScoutingIdInstance = MyScoutingId.get(params.id)
        if (myScoutingIdInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (myScoutingIdInstance.version > version) {
                    
                    myScoutingIdInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'myScoutingId.label', default: 'MyScoutingId')] as Object[], "Another user has updated this MyScoutingId while you were editing")
                    render(view: "edit", model: [myScoutingIdInstance: myScoutingIdInstance])
                    return
                }
            }
            myScoutingIdInstance.properties = params
            if (!myScoutingIdInstance.hasErrors() && myScoutingIdInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'myScoutingId.label', default: 'MyScoutingId'), myScoutingIdInstance.id])}"
                redirect(action: "show", id: myScoutingIdInstance.id)
            }
            else {
                render(view: "edit", model: [myScoutingIdInstance: myScoutingIdInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'myScoutingId.label', default: 'MyScoutingId'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def myScoutingIdInstance = MyScoutingId.get(params.id)
        if (myScoutingIdInstance) {
            try {
                myScoutingIdInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'myScoutingId.label', default: 'MyScoutingId'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'myScoutingId.label', default: 'MyScoutingId'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'myScoutingId.label', default: 'MyScoutingId'), params.id])}"
            redirect(action: "list")
        }
    }
}
