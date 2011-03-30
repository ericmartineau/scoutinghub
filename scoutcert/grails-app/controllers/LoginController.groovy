import org.springframework.security.core.context.SecurityContextHolder as SCH

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import javax.servlet.http.HttpServletResponse
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import scoutcert.Leader
import scoutcert.SocialLoginService
import scoutcert.LeaderRole
import scoutcert.Role
import grails.plugin.mail.MailService
import scoutcert.EmailVerifyService
import scoutcert.LeaderService

import scoutcert.CreateAccountCommand
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

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

    AuthenticationManager authenticationManager

    SocialLoginService socialLoginService

    MailService mailService

    EmailVerifyService emailVerifyService

    LeaderService leaderService

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
    @Secured(["ROLE_ANONYMOUS"])
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

    @Secured(["ROLE_LEADER"])
    def suggestSocialLogin = {
    }

    /**
     * The Ajax denied redirect url.
     */
    def ajaxDenied = {
        render([error: 'access denied'] as JSON)
    }

    def createAccount = {
    }

    def emailVerify = {
        Leader leader = Leader.findByVerifyHash(params.code)
        if (!leader) {

        }
    }


    def accountLinkFlow = {
        locateSocialLogin {
            action {
                String socialProvider = session["LAST_AUTH_PROVIDER"]
                String socialPrincipal = session[OpenIdAuthenticationFailureHandler.LAST_OPENID_USERNAME]

                flash.hasSocialAuth = socialProvider && socialPrincipal
                flash.socialProvider = socialProvider
                flash.socialPrincipal = socialPrincipal
                return success()
            }
            on("success").to "locateAccount"
        }

        locateAccount {
            on("linkSocial").to "submitVerifyUserPass"
            on("createNewAccount").to "verifyAccountExists"
        }



        verifyAccountExists {
            action {
                flow.createAccount = new CreateAccountCommand(
                        firstName: params.firstName,
                        lastName: params.lastName,
                        email: params.email,
                        unitNumber: params.unitNumber,
                        scoutid: params.scoutid,
                )

                Leader leader = leaderService.findExactLeaderMatch(params.scoutid, params.email,
                        params.firstName, params.lastName, params.unitNumber)
                if (leader) {
                    flow.leader = leader
                    return foundSingleExistingRecord()
                } else {
                    Collection<Leader> leaderSet = leaderService.findLeaders(params.scoutid, params.email,
                            params.firstName, params.lastName, params.unitNumber)

                    if (leaderSet.size() > 0) { //Ambigious
                        flow.leaderSet = leaderSet
                        return foundMultipleMatches()
                    } else {
                        def errors = []
                        def requiredProps = ["firstName", "lastName", "email", "unitNumber"]

                        requiredProps.each {
                            if (!params[it]) errors << "${it}.validation.error"
                        }

                        if (errors.size() > 0) {
                            flash.errors = errors
                            return error();
                        } else {
                            return proceedNewAccount()
                        }
                    }
                }


            }
            on("proceedNewAccount").to "selectUsernameAndPassword"
            on("foundMultipleMatches").to "foundMultipleMatches"
            on("foundSingleExistingRecord").to "foundSingleExistingRecord"
            on("error").to "locateSocialLogin"
        }



        foundSingleExistingRecord {
            action {
                Leader leader = flow.leader
                if (leader?.enabled) {
                    if (leader?.username) {
                        return verifyUserPass()
                    } else if (leader?.email) {
                        flash.leader = leader
                        return verifyEmail()
                    } else {
                        return enterAccountDetails()
                    }
                } else {
                    //First time setup
                    if (leader?.email) {
                        flash.leader = leader;
                        return verifyEmail()
                    } else {
                        return enterAccountDetails()
                    }

                }

            }
            on("verifyUserPass").to "verifyUserPass"
            on("verifyEmail").to "sendVerifyEmail"
            on("enterAccountDetails").to "enterAccountDetails"
        }

        foundMultipleMatches {
            on("selectLeader").to "selectLeader"
        }

        selectLeader {
            action {
                flow.leaderSet = null
                int leaderId = Integer.parseInt(params.leaderId)
                Leader leader
                if (leaderId == 0) {
                    return selectUsernameAndPassword()
//                    leader = leaderService.createLeader(flow.createAccount)
                } else {
                    leader = Leader.get(leaderId)
                    flow.leader = leader
                    return foundSingleExistingRecord()
                }

                //Add unit
//                ScoutUnit scoutUnit = ScoutUnit.findByUnitIdentifier(flow.createAccount?.unitNumber)
//                if(scoutUnit) {
//                    scoutUnit.addToLeaders(leader)
//                    scoutUnit.save(failOnError:true)
//                }
//
//                socialLoginService.linkSocialLogin(leader, session)
//                return success()
            }
            on("foundSingleExistingRecord").to "foundSingleExistingRecord"
            on("selectUsernameAndPassword").to "selectUsernameAndPassword"
        }

        selectUsernameAndPassword {
            on("submitUsernameAndPassword").to "submitUsernameAndPassword"
        }

        submitUsernameAndPassword {
            action {
                flow.createAccount.username = params.username
                flow.createAccount.password = params.password

                if (Leader.findByUsername(params.username)) {
                    flash.error = "flow.submitUsernameAndPassword.usernameTaken"
                    return error()
                } else if (params.password != params.confirmPassword) {
                    flash.error = "flow.submitUsernameAndPassword.passwordMismatch"
                    return error()
                } else if (!params.username || !params.password) {
                    flash.error = "flow.submitUsernameAndPassword.bothRequired"
                    return error()
                } else {
                    if (flow.leader) {
                        flow.leader.username = params.username
                        flow.leader.password = springSecurityService.encodePassword(params.password)
                    } else {

                        Leader leader = leaderService.createLeader(flow.createAccount)
                        flow.leader = leader;

                    }
                    return success()
                }

            }
            on("success").to "linkSocial"
            on("error").to "selectUsernameAndPassword"
        }

        enterAccountDetails {
        }

        verifyEmail {
            on("noCode").to "enterAccountDetails"
            on("processVerifyEmail").to "processVerifyEmail"
        }

        processVerifyEmail {
            action {
                Leader leader = Leader.findByVerifyHash(params.code)
                if (leader) {
                    leader.verifyHash = null
                    leader.save(failOnError: true)
                    flow.leader = leader
                    return linkSocial()

                } else {
                    flash.verifyError = "flow.verifyEmail.codeMismatch"
                    return failVerify()
                }

            }
            on("linkSocial").to "linkSocial"
            on("failVerify").to "verifyEmail"
        }

        verifyUserPass {
            on("submitUserPassVerify").to "submitVerifyUserPass"
            on("verifyByEmail").to "sendVerifyEmail"
        }

        sendVerifyEmail {
            action {
                if (!flow.leader?.email) {
                    return noEmail()
                } else {
                    try {
                        String subject = message(code: "verifyEmail.message.subject")
                        emailVerifyService.generateTokenForEmailValidation(flow.leader, subject)
                        return success()
                    } catch (Exception e) {
                        flash.exceptionMessage = e.message
                        flash.errorMessage = "flow.verifyEmail.cantsend"
                        return error();
                    }

                }

            }
            on("success").to "verifyEmail"
            on("error").to "errorVerifyEmail"
        }

        errorVerifyEmail {

        }

        submitVerifyUserPass {
            action {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(params.username, params.password)
                try {
                    Authentication authenticate = authenticationManager.authenticate(token)
                    if (authenticate.authenticated) {
                        flow.leader = Leader.findByUsername(params.username)
                        return successVerify()
                    } else {
                        return failedVerify();
                    }
                } catch (Exception e) {
                    return failedVerify()
                }
            }


            on("failedVerify") {
                flash.error = "flow.verifyUserPass.failedVerify"
                flash.error2 = "flow.verifyUserPass.failedVerifyMessage"
            }.to "verifyUserPass"
            on("successVerify") {
                redirect(controller: "leader", action: "index")
            }.to "linkSocial"

        }

        linkSocial {
            action {
                Leader leader = flow.leader
                leader.enabled = true
                leader.save(failOnError: true)
                if (!leader.username || !leader.password) {
                    return selectUsernameAndPassword()
                }
                flow.newSetup = leader.setupDate == null
                if (!leader.setupDate) {
                    leader.setupDate = new Date()
                    if (!leader.authorities.collect {it.authority}?.contains("ROLE_LEADER")) {
                        LeaderRole.create(leader, Role.findByAuthority("ROLE_LEADER"))
                    }
                }

                boolean linkedSocial = socialLoginService.linkSocialLogin(leader, session)
                if (!linkedSocial) {
                    springSecurityService.reauthenticate(leader.username, leader.password)
                }


                flow.leader = null
                if (flow.newSetup && !linkedSocial) {
                    return redirectSuggestSocialLogin()
                } else {
                    return login()
                }
            }
            on("login").to "login"
            on("error").to "locateSocialLogin"
            on("selectUsernameAndPassword").to "selectUsernameAndPassword"
            on("redirectSuggestSocialLogin").to "redirectSuggestSocialLogin"
        }

        redirectSuggestSocialLogin {
            redirect(controller: "login", action: "suggestSocialLogin")
        }


        login {
            redirect(controller: "leader", action: "index")
        }
    }
}


