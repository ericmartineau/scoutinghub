import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.facebook.FacebookAuthenticationProvider
import org.springframework.security.facebook.FacebookHelper
import scoutcert.FacebookAuthenticationFailureHandler
import scoutcert.FacebookSocialAuthenticationFilter
import scoutcert.OpenIdSocialAuthenticationFilter
import scoutcert.ScoutUserDetailsService

// Place your Spring DSL code here
beans = {

    def conf = SpringSecurityUtils.securityConfig
    if (!conf || !conf.active) {
        return
    }

    SpringSecurityUtils.loadSecondaryConfig 'DefaultOpenIdSecurityConfig'
    // have to get again after overlaying DefaultOpenIdSecurityConfig
    conf = SpringSecurityUtils.securityConfig

    if (!conf.openid.active) {
        return
    }

    //SpringSecurityUtils.registerProvider 'facebookAuthenticationProvider'

    userDetailsService(ScoutUserDetailsService) {
        grailsApplication = ref("grailsApplication")
    }

    facebookAuthenticationFilter(FacebookSocialAuthenticationFilter) {
        authenticationManager = ref("authenticationManager")
        authenticationSuccessHandler = ref("authenticationSuccessHandler")
        authenticationFailureHandler = ref("facebookAuthenticationFailureHandler")
        socialFacebookHelper = ref("facebookHelper")
        facebookHelper = ref("facebookHelper")
        springSecurityService = ref("springSecurityService")

    }

    facebookAuthenticationFailureHandler(FacebookAuthenticationFailureHandler) {
//        redirectStrategy = ref('redirectStrategy')
        defaultFailureUrl = conf.failureHandler.defaultFailureUrl //'/login/authfail?login_error=1'
        useForward = conf.failureHandler.useForward // false
        ajaxAuthenticationFailureUrl = conf.failureHandler.ajaxAuthFailUrl // '/login/authfail?ajax=true'
        exceptionMappings = conf.failureHandler.exceptionMappings // [:]
    }


    facebookAuthenticationProvider(FacebookAuthenticationProvider) {
        userDetailsService = ref("userDetailsService")
    }

    facebookHelper(FacebookHelper) {
        apiKey = "d6fc406cd3f5f8d3458eda5bd4e19e75"
        secret = "ab725467de1c9853e5f45ed389ce48d9"
    }

    openIDAuthenticationFilter(OpenIdSocialAuthenticationFilter) {
        claimedIdentityFieldName = conf.openid.claimedIdentityFieldName // openid_identifier
        consumer = ref('openIDConsumer')
        rememberMeServices = ref('rememberMeServices')
        authenticationManager = ref('authenticationManager')
        authenticationSuccessHandler = ref('authenticationSuccessHandler')
        authenticationFailureHandler = ref('authenticationFailureHandler')
        authenticationDetailsSource = ref('authenticationDetailsSource')
        sessionAuthenticationStrategy = ref('sessionAuthenticationStrategy')
        springSecurityService = ref("springSecurityService")
        filterProcessesUrl = '/j_spring_openid_security_check' // not configurable
    }

}
