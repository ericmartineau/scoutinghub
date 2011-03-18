<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler" %>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="flow.suggestSocialLogin.title"/></title>
</head>

<body>

<div id="fb-root"></div>
<script
        src="http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php/en_US"
        type="text/javascript"></script>
<script type="text/javascript">
    FB.init("d6fc406cd3f5f8d3458eda5bd4e19e75", "/scoutcert/static/xd_receiver.html");
    function facebook_onlogin() {
        FB.Connect.ifUserConnected(function () {
            // make request to the url 'fbconnect' for logging in bss
            window.location = '/scoutcert/openId/facebook?suggest=true';
        });
    }
</script>


<div class='body'>
    <table width="100%">
        <tr>
            <td align="center">
                <div style="width:400px">
                    <g:msgbox type="info" code="flow.suggestSocialLogin.header" code2="flow.suggestSocialLogin.message"/>
                    <g:if test="${params.fail}">
                        <g:msgbox type="error">
                            <div class="msg1">
                                <g:message code="flow.suggestSocialLogin.alreadyRegistered" args="[session['LAST_AUTH_PROVIDER']]"/>
                            </div>
                        </g:msgbox>
                    </g:if>


                    <div style="margin-top:20px">
                        <a href="/scoutcert/openId/yahoo?suggest=true"><img src="/scoutcert/images/yahoo.jpg"/></a>
                        <a href="/scoutcert/openId/google?suggest=true"><img src="/scoutcert/images/google.jpg"/></a>
                    </div>
                    <div>
                        <fb:login-button size="large" length="long" background="white"
                                onlogin="javascript:FB.Connect.requireSession(facebook_onlogin);">Facebook</fb:login-button>

                    </div>

                    <div style="margin-top:30px">
                        <g:link controller="leader" action="index">No thanks, just take me into ScoutCert</g:link>
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
