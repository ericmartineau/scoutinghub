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
import scoutcert.LeaderPositionType
import scoutcert.ScoutGroup
import scoutcert.LeaderGroup
import scoutcert.CreateAccountService
import scoutcert.RecordSavingService

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

    def searchableService

    MailService mailService

    EmailVerifyService emailVerifyService

    LeaderService leaderService

    CreateAccountService createAccountService

    RecordSavingService recordSavingService

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

    @Secured(['ROLE_ANONYMOUS'])
    def openIdCreateAccount = {
        forward(action: "accountLink")
    }

    /**
     * Show denied page.
     */
    def denied = {
//        if (springSecurityService.isLoggedIn() &&
//                authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
//            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
//            redirect action: full, params: params
//        }
    }

    /**
     * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
     */
    @Secured(["ROLE_ANONYMOUS", "ROLE_LEADER", "ROLE_ADMIN"])
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

        /**
         * Very first action - looks for the existence of a possible social login setup attempt
         */
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

        /**
         * Looks for either a:
         *
         * Single record match, OR
         * A list of possible matches
         */
        verifyAccountExists {
            action {

                CreateAccountCommand createCommand = new CreateAccountCommand()
                flow.createAccount = createCommand;

                createCommand.firstName = params.firstName
                createCommand.lastName = params.lastName
                createCommand.email = params.email
                createCommand.password = params.password
                createCommand.confirmPassword = params.confirmPassword
                createCommand.scoutid = params.scoutid

                if (params.unitNumberId) {
                    int unitId = Integer.parseInt(params.unitNumberId)
                    if (unitId > 0) {
                        createCommand.unit = ScoutGroup.get(unitId)
                    }
                }

                if (params.unitPosition) {
                    createCommand.unitPosition = LeaderPositionType.values().find {it.name() == params.unitPosition}
                }

                def errors = []
                def requiredProps = ["firstName", "lastName", "email", "unit", "password", "confirmPassword", "unitPosition"]

                requiredProps.each {
                    if (!createCommand.getProperty(it)) errors << "${it}.validation.error"
                }

                if (errors.size() > 0) {
                    flash.errors = errors
                    return error();
                } else {
                    Leader leader = leaderService.findExactLeaderMatch(params.scoutid, params.email,
                            params.firstName, params.lastName, createCommand.unit)

                    if (leader) {
                        flow.leader = leader
                        return foundSingleExistingRecord()
                    } else {
                        Collection<Leader> leaderSet = leaderService.findLeaders(params.scoutid, params.email,
                                params.firstName, params.lastName, createCommand.unit)

                        if (leaderSet.size() > 0) { //Ambigious
                            flow.leaderSet = leaderSet
                            return foundMultipleMatches()
                        } else {
                            return proceedNewAccount()
                        }
                    }

                }


            }
            on("proceedNewAccount").to "submitUsernameAndPassword"
            on("foundMultipleMatches").to "foundMultipleMatches"
            on("foundSingleExistingRecord").to "foundSingleExistingRecord"
            on("error").to "locateSocialLogin"
        }

        /**
         * Is called when a single record is located as a duplicate
         */
        foundSingleExistingRecord {
            action {
                Leader leader = flow.leader
                CreateAccountCommand createAccount = flow.createAccount

                if (leader?.enabled) {

                    if (leader?.username) {
                        //Let's try to authenticate.  If we can authenticate, let's not waste time asking them
                        //about all that other business.
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(createAccount.usernameOrEmail, params.password)
                        try {
                            Authentication authenticate = authenticationManager.authenticate(token)
                            if (authenticate.authenticated) {
                                createAccountService.mergeCreateAccountWithExistingLeader(createAccount, leader)
                                return verifiedExistingRecord();
                            }
                        } catch (Exception e) {
                            e.printStackTrace()
                        }

                        //If we didn't authenticate, have them verify the u/p
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
                        //Finish creating account
                        return proceedNewAccount()
                    }

                }

            }
            on("verifiedExistingRecord").to "verifiedExistingRecord"
            on("verifyUserPass").to "verifyUserPass"
            on("verifyEmail").to "sendVerifyEmail"
            on("proceedNewAccount").to "submitUsernameAndPassword"
        }

        /**
         * Renders when multiple matches are found from the leader querying service.
         */
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
                CreateAccountCommand createAccount = flow.createAccount
                createAccount.username = params.username ?: createAccount.username ?: createAccount.email
                createAccount.password = params.password ?: createAccount.password

                if (Leader.findByUsername(createAccount?.username)) {
                    flash.error = "flow.submitUsernameAndPassword.usernameTaken"
                    return error()
                } else if (createAccount.password != createAccount.confirmPassword) {
                    flash.error = "flow.submitUsernameAndPassword.passwordMismatch"
                    return error()
                } else if (!createAccount.username || !createAccount.password) {
                    flash.error = "flow.submitUsernameAndPassword.bothRequired"
                    return error()
                } else {
                    if (flow.leader) {
                        flow.leader.username = createAccount.username
                        flow.leader.password = springSecurityService.encodePassword(createAccount.password)
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

                    return verifiedExistingRecord()

                } else {
                    flash.verifyError = "flow.verifyEmail.codeMismatch"
                    return failVerify()
                }

            }
            on("verifiedExistingRecord").to "verifiedExistingRecord"
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
            on("noEmail").to "verifyUserPass"
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
                        return verifiedExistingRecord()
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
            on("verifiedExistingRecord") {
                //redirect(controller: "leader", action: "index")
            }.to "verifiedExistingRecord"

        }

        verifiedExistingRecord {
            action {
                createAccountService.mergeCreateAccountWithExistingLeader(flow.createAccount, flow.leader)
                return success()
            }

            on("success").to "linkSocial"
        }



        linkSocial {
            action {
                Leader leader = flow.leader
                leader.enabled = true
                recordSavingService.op(leader) {
                    it.save(failOnError: true)
                }
                if (!leader.username || !leader.password) {
                    return selectUsernameAndPassword()
                }
                flow.newSetup = leader.createDate == null
                if (!leader.createDate) {
                    leader.createDate = new Date()
                }

                if (!leader.authorities.collect {it.authority}?.contains("ROLE_LEADER")) {
                    LeaderRole.create(leader, Role.findByAuthority("ROLE_LEADER"))
                }

                //add to unit
                CreateAccountCommand createAccount = flow.createAccount
                ScoutGroup targetUnit = createAccount.unit.merge();
                if (!targetUnit.leaderGroups?.find {LeaderGroup gp -> gp.leader.id == leader.id}) {
                    targetUnit.addToLeaderGroups(new LeaderGroup(leader: leader, scoutGroup: createAccount.unit, position: createAccount.unitPosition));
                    recordSavingService.op(targetUnit) {
                        it.save(flush: true)
                    }
                }

                boolean linkedSocial = socialLoginService.linkSocialLogin(leader, session)
                if (!linkedSocial) {
                    springSecurityService.reauthenticate(leader.username, leader.password)
                }


                flow.leader = null
                if (flow.newSetup && !linkedSocial) {
//                    return login()
                    //Social login was removed - this line should be uncommented to add
                    //back in
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


