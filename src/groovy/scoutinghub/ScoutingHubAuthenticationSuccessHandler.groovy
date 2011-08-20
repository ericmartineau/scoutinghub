package scoutinghub

import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.context.ApplicationListener

/**
 * User: ericm
 * Date: 8/19/11
 * Time: 4:58 PM
 */
class ScoutingHubAuthenticationSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

    SpringSecurityService springSecurityService

    void onApplicationEvent(AuthenticationSuccessEvent e) {
        //Make sure the user's setupDate is set correctly.

        def id = e.authentication.principal.id
        Leader.withTransaction {

            Leader leader = Leader.get(id)
            if (!leader.setupDate) {
                leader.setupDate = new Date()
                leader.save(failOnError: true)
            }
        }


    }


}
