package scoutinghub

import grails.converters.JSON

class UiShowcaseController {

    def index = {
        return [layoutName: params.layoutName ?: request.layoutName]
    }

    def mobile = {
        session.isMobile = true
        forward(action: "index")
    }

    def browser = {
        session.isMobile = false
        forward(action: "index")
    }

    def dialog = {
    }

    def processDialog = {
        def rtn = [:]
        if(params.submitWithError) {
            rtn.success = false
            flash.error = "uiShowcase.dialog.error"
        } else {
            rtn.success = true
        }
        render rtn as JSON
    }
}
