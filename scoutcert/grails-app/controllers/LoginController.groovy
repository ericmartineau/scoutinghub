import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import grails.plugins.springsecurity.Secured
import org.springframework.web.servlet.ModelAndView
import scoutcert.Leader
import org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.web.savedrequest.DefaultSavedRequest

@Secured(['ROLE_ANONYMOUS'])
class LoginController {

    /**
     * Dependency injection for the authenticationTrustResolver.
     */
    def authenticationTrustResolver

    /**
     * Dependency injection for the springSecurityService.
     */
    def springSecurityService

    /**
     * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
     */
    def index = {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        }
        else {
            redirect action: auth, params: params
        }
    }

    /**
     * Show the login page.
     */
    def auth = {

        def config = SpringSecurityUtils.securityConfig

        if (springSecurityService.isLoggedIn()) {
            redirect uri: config.successHandler.defaultTargetUrl
            return
        }

        String view = 'auth'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl,
                rememberMeParameter: config.rememberMe.parameter]
    }

    /**
     * The redirect action for Ajax requests.
     */
    @Secured(['ROLE_ANONYMOUS'])
    def authAjax = {
        response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
    }

    /**
     * Show denied page.
     */
    def denied = {
        if (springSecurityService.isLoggedIn() &&
                authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: full, params: params
        }
    }

    /**
     * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
     */
    def full = {
        def config = SpringSecurityUtils.securityConfig
        render view: 'auth', params: params,
                model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
                        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

    /**
     * Callback after a failed login. Redirects to the auth page with a warning message.
     */
    def authfail = {

        def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.expired
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.passwordExpired
            }
            else if (exception instanceof DisabledException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.disabled
            }
            else if (exception instanceof LockedException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.locked
            }
            else {
                msg = SpringSecurityUtils.securityConfig.errors.login.fail
            }
        }

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        }
        else {
            flash.message = msg
            redirect action: auth, params: params
        }
    }

    /**
     * The Ajax success redirect url.
     */
    def ajaxSuccess = {
        render([success: true, username: springSecurityService.authentication.name] as JSON)
    }

    /**
     * The Ajax denied redirect url.
     */
    def ajaxDenied = {
        render([error: 'access denied'] as JSON)
    }

    def createAccount = {
    }

    def accountLinkFlow = {
        locateAccount {
            on("createAccount").to "enterAccountDetails"
            on("findByEmail").to "verifyAccountAction"
            on("findByScoutId").to "verifyAccountAction"
            on("findByName").to "verifyAccountAction"
        }

        verifyAccountAction {
            action {
                Leader leader
                if (params.email != "") {
                    leader = Leader.findByEmail(params.email)
                    if (!leader) {
                        flash.emailError = "error.emailNotFound"
                    }
                }

                if(params.scoutid != "") {
                    def c = Leader.createCriteria();
                    leader = c.get {
                        myScoutingIds {
                            eq('myScoutingIdentifier', params.scoutid)
                        }
                    };
                    if(!leader) {
                        flash.scoutIdError = "error.scoutIdNotFound"
                    }

                }

                if (!leader) {
                    return error();
                }
                if (leader?.enabled) {
                    if (leader?.username) {
                        return verifyUserPass()
                    } else if (leader?.email) {
                        return verifyEmail()
                    } else {
                        return enterAccountDetails()
                    }
                } else {
                    //First time setup
                    if (leader?.email) {
                        return verifyEmail()
                    } else {
                        return enterAccountDetails()
                    }

                }
            }
            on("verifyEmail").to "verifyEmail"
            on("verifyUserPass").to "verifyUserPass"
            on("enterAccountDetails").to "enterAccountDetails"
            on("error").to "locateAccount"

        }

        enterAccountDetails {

        }

        verifyEmail {

        }

        verifyUserPass {
            on("submitUserPassVerify").to "submitVerifyUserPass"
            on("verifyByEmail").to "sendVerifyEmail"
        }

        sendVerifyEmail {
            action {

            }
            on("success").to "login"
        }

        submitVerifyUserPass {
            action {
                Leader verifiedLeader = Leader.findByUsernameAndPassword(params.username,
                        springSecurityService.encodePassword(params.password))
                if (verifiedLeader) {
                    def lastOpenId = session[OpenIdAuthenticationFailureHandler.LAST_OPENID_USERNAME]
                    if (lastOpenId) {
                        Leader.withTransaction { status ->
                            verifiedLeader.addToOpenIds(url: lastOpenId)
                            if (!verifiedLeader.validate()) {
                                status.setRollbackOnly()
                            } else {
                                springSecurityService.reauthenticate verifiedLeader.username
                                session.removeAttribute OpenIdAuthenticationFailureHandler.LAST_OPENID_USERNAME
                                session.removeAttribute OpenIdAuthenticationFailureHandler.LAST_OPENID_ATTRIBUTES
                            }
                        }
                    }
                    return successVerify()
                } else {

                    return failedVerify();
                }
            }
            on("failedVerify") {
                flash.error = "flow.verifyUserPass.failedVerify"
            }.to "verifyUserPass"
            on("successVerify") {
                redirect(controller: "leader", action: "index")
            }.to "login"

        }

        login {
            redirect(controller: "leader", action: "index")
        }


    }
}
