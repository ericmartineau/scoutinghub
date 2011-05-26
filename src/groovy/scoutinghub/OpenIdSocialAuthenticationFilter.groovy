package scoutinghub

import org.springframework.security.openid.OpenIDAuthenticationFilter
import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.security.openid.OpenIDAuthenticationStatus

/**
 * User: eric
 * Date: 3/17/11
 * Time: 5:56 PM
 */
class OpenIdSocialAuthenticationFilter extends OpenIDAuthenticationFilter {

    OpenIdSocialAuthenticationProcessor processor = new OpenIdSocialAuthenticationProcessor()
    SpringSecurityService springSecurityService

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return processor.processAuthentication(request, response, springSecurityService)
    }

    public Authentication superAttemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        return super.attemptAuthentication(req, res)
    }



    class OpenIdSocialAuthenticationProcessor extends AbstractSocialAuthenticationProcessor {
        @Override
        Authentication doAttemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
            return superAttemptAuthentication(request, response)
        }

        @Override
        String getSocialUserId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Authentication authentication) {
            return httpServletRequest.getParameter("openid.identity")
        }

        @Override
        boolean handles(Authentication authentication) {
            return authentication instanceof OpenIDAuthenticationToken
        }

        @Override
        boolean wasSuccessful(Authentication authentication) {
            OpenIDAuthenticationToken openIDAuthenticationToken = (OpenIDAuthenticationToken) authentication
            return openIDAuthenticationToken.status == OpenIDAuthenticationStatus.SUCCESS;
        }

    }
}
