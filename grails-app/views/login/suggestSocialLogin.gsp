<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler" %>
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="flow.suggestSocialLogin.title"/></title>
</head>

<body>

<s:content>

    <s:section>
        <g:if test="${params.fail}">
            <s:msg type="error">
                <s:div class="msg1">
                    <g:message code="flow.suggestSocialLogin.alreadyRegistered" args="[session['LAST_AUTH_PROVIDER']]"/>
                </s:div>
            </s:msg>
        </g:if>
        <s:sectionHeader code="flow.suggestSocialLogin.header"/>
        <s:text><g:message code="flow.suggestSocialLogin.msg"/></s:text>
        <div style="margin-top:20px">
            <a href="/scoutinghub/openId/yahoo?suggest=true"><img src="/scoutinghub/images/yahoo.jpg"/></a>
            <a href="/scoutinghub/openId/google?suggest=true"><img src="/scoutinghub/images/google.jpg"/></a>
        </div>

        <div>
            <a href="/scoutinghub/openId/facebook?suggest=true"><img src="/scoutinghub/images/facebook.jpg"/></a>
        </div>

        <s:div style="margin-top:30px">
                        <s:linker><g:link controller="leader" action="index"><g:message code="flow.suggestSocialLogin.goToProfile"/></g:link></s:linker>
        </s:div>

    </s:section>
</s:content>


</body>
</html>