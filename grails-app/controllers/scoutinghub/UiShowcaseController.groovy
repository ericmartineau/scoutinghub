package scoutinghub

import grails.converters.JSON

class UiShowcaseController {

    def index = {
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
