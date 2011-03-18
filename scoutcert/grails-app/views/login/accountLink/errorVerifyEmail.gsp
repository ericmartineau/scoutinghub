<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler" %>
<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="flow.errorVerifyEmail.title"/></title>
</head>

<body>

<div class='body'>
    <g:msgbox type="error" code="${errorMessage}" code2="${exceptionMessage}"/>
</div>
</body>
</html>