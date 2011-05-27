package scoutinghub

import grails.plugin.mail.MailService

class ErrorRenderingController {

    MailService mailService

    def show = {
        println flash.errorObj
    }

    def showStackTraces = {
        session.showStackTraces = true
        render "Done!"
    }

    def fiveHundred = {
        //Let's just spam for now - we'll work through everything else later
        try {
            mailService.sendMail {
                to "smartytime@gmail.com"
                from "errors@scoutinghub"
                subject "Error: " + request.exception?.message
                body(view: "/error/stackTrace",
                        model: [exception: request.exception, request: request])

            }
        } catch (Exception e) {
            log.error("Error logging error!!", e)
        }

        [exception:request.exception]
    }
}
