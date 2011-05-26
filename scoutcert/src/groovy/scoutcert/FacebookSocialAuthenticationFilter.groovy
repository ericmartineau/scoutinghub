package scoutcert

import org.springframework.security.facebook.FacebookAuthenticationFilter
import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.security.facebook.FacebookHelper
import org.springframework.security.facebook.FacebookAuthenticationToken
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement

/**
 * User: eric
 * Date: 3/17/11
 * Time: 5:47 PM
 */
class FacebookSocialAuthenticationFilter extends FacebookAuthenticationFilter {

    SpringSecurityService springSecurityService
    FacebookHelper socialFacebookHelper
    FacebookSocialAuthenticationProcessor processor = new FacebookSocialAuthenticationProcessor()
    def grailsApplication

    @Override
    Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        processor.processAuthentication(req, res, springSecurityService)
    }

    public Authentication superAttemptAuthentication(HttpServletRequest req, HttpServletResponse res) {

        Authentication rtn = super.attemptAuthentication(req, res);
        FacebookAuthenticationToken authentication = (FacebookAuthenticationToken) rtn
        if (authentication) {
            def c = Leader.createCriteria();
            Leader leader = c.get {
                openIds {
                    eq('url', String.valueOf(authentication.uid))
                }
            }
            if (leader) {
                springSecurityService.reauthenticate(leader.username, leader.password)
                rtn = springSecurityService.authentication
            }
        }

        return rtn

    }

    class FacebookSocialAuthenticationProcessor extends AbstractSocialAuthenticationProcessor {
        @Override
        Authentication doAttemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
            superAttemptAuthentication(request, response);
        }

        @Override
        String getSocialUserId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication auth) {
//            String rtn = null
//            if (auth && auth instanceof FacebookAuthenticationToken) {
//                rtn = String.valueOf(auth.uid)
//            }

//            String url = "https://graph.facebook.com/oauth/access_token" +
//                "?client_id=184451748255736" +
//                "&client_secret=ab725467de1c9853e5f45ed389ce48d9" +
//                "&redirect_uri=" + grailsApplication.config.grails.serverURL + "/j_spring_facebook_security_check" +
//                "&code=" + httpServletRequest.getParameter("code");
//            String access_token = new URL(url).getText();
//            String jsonUserData = new URL("https://graph.facebook.com/me?" + access_token).getText()
//            def jsonData = JSON.parse(jsonUserData)
//            return jsonData.id
            long id = socialFacebookHelper.getLoggedInUserId(httpServletRequest, httpServletResponse);
            if (httpServletRequest.getParameter("auth_token")) {
                //Write the cookies and redirect - hack but I can't figure out another way to do it

                if (id > 0) {
                    httpServletResponse.sendRedirect(httpServletRequest.getRequestURI()); //Without parameter - cookie should be set.
                    throw new RuntimeException("Not ready to process");

                }
            }
            return String.valueOf(id);
//            return rtn;
        }

        @Override
        boolean handles(Authentication authentication) {
            return authentication instanceof FacebookAuthenticationToken
        }

        @Override
        boolean wasSuccessful(Authentication authentication) {
            FacebookAuthenticationToken facebookAuthenticationToken = (FacebookAuthenticationToken) authentication
            return facebookAuthenticationToken != null && facebookAuthenticationToken.uid
        }

    }
}
