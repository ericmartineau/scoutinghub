package scoutcert

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.userdetails.UsernameNotFoundException
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.security.openid.OpenIDAuthenticationToken

/**
 * User: eric
 * Date: 3/17/11
 * Time: 5:37 PM
 */
abstract class AbstractSocialAuthenticationProcessor {

    Authentication processAuthentication(HttpServletRequest request, HttpServletResponse response, SpringSecurityService springSecurityService) {
        Authentication rtn = null
        String identityUrl = getIdentityUrl(request, response)
        Integer userId = request.session["open_link_userid"]

        if (identityUrl && userId) {
            def c = Leader.createCriteria();
            Leader leader = c.get {
                openIds {
                    eq('url', identityUrl)
                }
            }

            if (leader && leader?.id != userId) {
                Leader existing = Leader.get(userId)
                springSecurityService.reauthenticate(existing?.username, existing?.password)
                request.session.removeAttribute("open_link_userid")
                response.sendRedirect("/scoutcert/login/suggestSocialLogin?fail=1");
                return null;

            }
        }
        if (!rtn) {
            try {
                rtn = doAttemptAuthentication(new GrailsHttpServletRequest(request), response)
            } catch (UsernameNotFoundException une) { //Exception thrown because the account isn't linked
                boolean handled = false
                if (userId && identityUrl) {

                    if (this.handles(une.authentication)) {
                        if(this.wasSuccessful(une.authentication)) {
                            //Let's link them up
                            Leader.withTransaction {
                                Leader leader = Leader.get(userId)
                                new OpenID(url: identityUrl, leader: leader).save()

                                request.session.removeAttribute("open_link_userid")
                                springSecurityService.reauthenticate(leader.username, leader.password)

                            }
                            response.sendRedirect("/scoutcert/leader/index");
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

    abstract String getIdentityUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)

    abstract Authentication doAttemptAuthentication(HttpServletRequest request, HttpServletResponse response)

    abstract boolean handles(Authentication authentication);
    abstract boolean wasSuccessful(Authentication authentication);

}
