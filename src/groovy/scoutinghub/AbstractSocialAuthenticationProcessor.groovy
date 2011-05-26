package scoutinghub

import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.userdetails.UsernameNotFoundException
import grails.plugins.springsecurity.SpringSecurityService

/**
 * User: eric
 * Date: 3/17/11
 * Time: 5:37 PM
 */
abstract class AbstractSocialAuthenticationProcessor {

    Authentication processAuthentication(HttpServletRequest request, HttpServletResponse response, SpringSecurityService springSecurityService) {
        Authentication rtn = null
        String socialUserId
        try {
            socialUserId = getSocialUserId(request, response, null)
        } catch (Exception e) {
            return null;
        }


        //This value will be set in the session when the user initiates the social login process AFTER they've already
        //been logged into the software using their regular ScoutCert login
        Integer userId = request.session["open_link_userid"]

        if (socialUserId && userId) {
            //Check if the social userid has already been linked
            def c = Leader.createCriteria();
            Leader leader = c.get {
                openIds {
                    eq('url', socialUserId)
                }
            }

            //Kinda hacky, but couldn't think of another way around it.  The social authentication providers will
            //already have authenticated the social login, so if we find that it (the social username) has already
            //been registered to another user, we need to unauthenticate the social login and reauthenticate them
            //as the user that was originally logged manually into the software
            if (leader && leader?.id != userId) {
                Leader existing = Leader.get(userId)
                springSecurityService.reauthenticate(existing?.username, existing?.password)
                request.session.removeAttribute("open_link_userid")
                response.sendRedirect("/scoutinghub/login/suggestSocialLogin?fail=1");
                return null;

            }
        }
        if (!rtn) {
            try {
                rtn = doAttemptAuthentication(new GrailsHttpServletRequest(request), response)
            } catch (UsernameNotFoundException une) { //Exception thrown because the social username can't be linked to a Leader record
                //This logic could be handled by an AuthenticationFailedHandler, but it's much harder to use becuase
                //it's abstract.  I should look into it, though
                boolean handled = false
                if (!socialUserId) {
                    socialUserId = this.getSocialUserId(request, response, une.authentication)
                }
                if (userId && socialUserId) {

                    if (this.handles(une.authentication)) {

                        if (this.wasSuccessful(une.authentication)) {//Social login was successful
                            Leader.withTransaction {
                                Leader leader = Leader.get(userId)
                                new OpenID(url: socialUserId, leader: leader).save()

                                request.session.removeAttribute("open_link_userid")
                                springSecurityService.reauthenticate(leader.username, leader.password)

                            }
                            //This redirect should go to the default spring security page (after login)
                            response.sendRedirect("/scoutinghub/openId/linked?t=" + request.session["LAST_AUTH_PROVIDER"]);
                            return null
                        }
                    }
                }
                if (!handled) {
                    throw une;
                }

            }
        }

        return rtn;
    }

    abstract String getSocialUserId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)

    abstract Authentication doAttemptAuthentication(HttpServletRequest request, HttpServletResponse response)

    abstract boolean handles(Authentication authentication)

    ;

    abstract boolean wasSuccessful(Authentication authentication)

    ;

}
