package scoutcert

import org.springframework.security.facebook.FacebookAuthenticationFilter
import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.security.facebook.FacebookHelper
import org.springframework.security.facebook.FacebookAuthenticationToken

/**
 * User: eric
 * Date: 3/17/11
 * Time: 5:47 PM
 */
class FacebookSocialAuthenticationFilter extends FacebookAuthenticationFilter {

    SpringSecurityService springSecurityService
    FacebookHelper socialFacebookHelper
    FacebookSocialAuthenticationProcessor processor = new FacebookSocialAuthenticationProcessor()

    @Override
    Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        processor.processAuthentication(req, res, springSecurityService)
    }

    public Authentication superAttemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        return super.attemptAuthentication(req, res)
    }

    class FacebookSocialAuthenticationProcessor extends AbstractSocialAuthenticationProcessor {
        @Override
        Authentication doAttemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
            superAttemptAuthentication(request, response);
        }

        @Override
        String getSocialUserId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
            return socialFacebookHelper.getLoggedInUserId(httpServletRequest, httpServletResponse)
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
