package scoutinghub

import org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler
import grails.plugins.springsecurity.SpringSecurityService

class SocialLoginService {

    static transactional = false

    SpringSecurityService springSecurityService;

    public boolean linkSocialLogin(Leader leader, def session) {
        String socialLoginToken = session[OpenIdAuthenticationFailureHandler.LAST_OPENID_USERNAME]
        boolean rtn = false;
        if (socialLoginToken) {
            Leader.withTransaction { status ->
                leader.addToOpenIds(url: socialLoginToken)
                if (!leader.validate()) {
                    status.setRollbackOnly()
                } else {
                    springSecurityService.reauthenticate leader.username
                    session.removeAttribute OpenIdAuthenticationFailureHandler.LAST_OPENID_USERNAME
                    session.removeAttribute OpenIdAuthenticationFailureHandler.LAST_OPENID_ATTRIBUTES
                    rtn = true
                }
            }
        }
        return rtn
    }


}
