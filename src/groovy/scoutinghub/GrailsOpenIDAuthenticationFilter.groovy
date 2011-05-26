package scoutinghub

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.openid.OpenIDAuthenticationFilter
import org.springframework.security.authentication.AuthenticationManager
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.openid.OpenIDAuthenticationStatus

/**
 * User: eric
 * Date: 3/16/11
 * Time: 6:56 PM
 */
class GrailsOpenIDAuthenticationFilter extends OpenIDAuthenticationFilter {

    SpringSecurityService springSecurityService

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Authentication rtn = null
        String identityUrl = request.getParameter("openid.identity")
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
                response.sendRedirect("/scoutinghub/login/suggestSocialLogin?fail=1");
                return null;

            }
        }
        if (!rtn) {
            try {
                rtn = super.attemptAuthentication(new GrailsHttpServletRequest(request), response)
            } catch (UsernameNotFoundException une) { //Exception thrown because the account isn't linked
                boolean handled = false
                if (userId && identityUrl) {
                    if (une.authentication instanceof OpenIDAuthenticationToken) {
                        OpenIDAuthenticationToken openIDAuthenticationToken = (OpenIDAuthenticationToken) une.authentication
                        if (openIDAuthenticationToken.status == OpenIDAuthenticationStatus.SUCCESS) {
                            //Let's link them up
                            Leader.withTransaction {
                                Leader leader = Leader.get(userId)
                                new OpenID(url: identityUrl, leader: leader).save()

                                request.session.removeAttribute("open_link_userid")
                                springSecurityService.reauthenticate(leader.username, leader.password)

                            }
                            response.sendRedirect("/scoutinghub/leader/index");
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


}
