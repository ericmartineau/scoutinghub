<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler" %>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="flow.suggestSocialLogin.title"/></title>
</head>

<body>


<div class='body'>
    <table width="100%">
        <tr>
            <td align="center">
                <div style="width:400px">

                    <g:if test="${params.fail}">
                        <g:msgbox type="error">
                            <div class="msg1">
                                <g:message code="flow.suggestSocialLogin.alreadyRegistered" args="[session['LAST_AUTH_PROVIDER']]"/>
                            </div>
                        </g:msgbox>
                    </g:if>

                    <div style="margin-top:30px">
                        <g:link controller="leader" action="index">Take me to my profile</g:link>
                    </div>
                </div>

            </td>
        </tr>
    </table>


    %{--<g:if test='${openId}'>--}%
    %{--Or if you're already a user you can <g:link action='linkAccount'>link this OpenID</g:link> to your account<br/>--}%
    %{--<br/>--}%
    %{--</g:if>--}%

</div>

</body>
