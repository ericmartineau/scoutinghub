<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler" %>
<html>
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="flow.errorVerifyEmail.title"/></title>
</head>

<body>

<s:content>
    <s:section>
        <s:msg type="error" code="${errorMessage}" code2="${exceptionMessage}"/>
    </s:section>
</s:content>
</body>
</html>