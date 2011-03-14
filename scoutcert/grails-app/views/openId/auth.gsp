<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
        xmlns:fb="http://www.facebook.com/2008/fbml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.facebook.com/2008/fbml ">
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="menu.login"/></title>
    <style type="text/css">

    .biglabel {
        font-size: 14px;
        font-weight: bold;
    }

    .alabel {
        font-size: 14px;
    }

    a {
        font-size: 14px;
    }

    .loginForm {
        font-size: 24px;
    }

    .fldContainer {
        margin-bottom: 15px;
    }

    .login_message {
        color: #990000;
    }
    </style>

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
            window.location = '/scoutcert/j_spring_facebook_security_check';
        });
    }
</script>
<div id='login'>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="100%" align="center">
                <table width="100%">
                    <tr>

                        <td align="center" width="50%">
                            <table>
                                <tr>
                                    <td align="center">
                                        <div class="biglabel"><g:message code="login.enteruandp"/></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left">


                                        <div class='inner'>
                                            <g:if test='${flash.message}'>
                                                <div class='login_message'>${flash.message}</div>
                                            </g:if>
                                            <div class="fldContainer"></div>
                                            <form action='${daoPostUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                                                <div class="fldContainer">
                                                    <label class='alabel' for='username'><g:message code="label.username"/></label><br/>
                                                    <input type='text' class='loginForm ui-corner-all' name='j_username' id='username'/>
                                                </div>
                                                <div class="fldContainer">
                                                    <label class='alabel' for='password'><g:message code="label.password"/></label><br/>
                                                    <input type='password' class='loginForm ui-corner-all' name='j_password' id='password'/>
                                                </div>

                                                <div class="fldContainer">
                                                    <span><input type='submit' value='${message(code: 'label.login')}' class="ui-button"/></span>
                                                    <span><input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
                                                        <g:if test='${hasCookie}'>checked='checked'</g:if>/></span>
                                                    <span><label for='remember_me'><g:message code="label.rememberMe"/></label></span>

                                                </div>
                                                <p style="margin-top:30px; text-align:center">
                                                    <g:link controller="login" action="forgotPassword">Forgot Password</g:link> |
                                                    <g:link controller="login" action="createAccount">Create Account</g:link>
                                                </p>
                                            </form>
                                        </div>

                                    </td>
                                </tr>
                            </table>

                        </td>
                        <td valign="top" width="50%" align="center">
                            <div class="biglabel"><g:message code="login.alternateproviders"/></div>

                            <table cellpadding="5">
                                <tr>
                                    <td><a href="/scoutcert/j_spring_openid_security_check?openid_identifier=http://www.yahoo.com/"><img src="/scoutcert/images/yahoo.jpg"/></a></td>
                                    <td><a href="/scoutcert/j_spring_openid_security_check?openid_identifier=https://www.google.com/accounts/o8/id"><img src="/scoutcert/images/google.jpg"/></a></td>

                                </tr>
                                <tr>
                                    <td colspan="2" align="center">
                                        <fb:login-button size="large" length="long" background="white"
                                                onlogin="javascript:FB.Connect.requireSession(facebook_onlogin);">Facebook</fb:login-button>

                                    </td>
                                </tr>

                            </table>

                            %{--<div class="biglabel"><g:message code="login.createaccount"/></div>--}%
                            %{--<table>--}%
                                %{--<tr>--}%
                                    %{--<td>fdsdsjkl</td>--}%
                                %{--</tr>--}%
                            %{--</table>--}%


                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<script type='text/javascript'>
    <!--
    (function() {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>
</body>
</html>
