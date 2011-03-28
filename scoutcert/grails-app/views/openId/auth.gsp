<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
        xmlns:fb="http://www.facebook.com/2008/fbml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.facebook.com/2008/fbml ">
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="menu.login"/></title>
    <style type="text/css">

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

<div id='login'>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="100%" align="center">
                <table width="100%">
                    <tr>

                        <td align="center" width="50%">
                            <h2><g:message code="login.enteruandp"/></h2>
                            <g:if test='${flash.message}'>
                                <g:msgbox type="error" code="${flash.message}"/>
                            </g:if>

                            <table>

                                <tr>
                                    <td align="left">

                                        <div class='inner'>
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

                                            </form>
                                        </div>

                                    </td>
                                </tr>
                            </table>

                        </td>
                        <td valign="top" width="50%" align="center">
                            <h2><g:message code="login.alternateproviders"/></h2>

                            <table cellpadding="5">
                                <tr>
                                    <td><a href="/scoutcert/openId/yahoo"><img src="/scoutcert/images/yahoo.jpg"/></a></td>
                                    <td><a href="/scoutcert/openId/google"><img src="/scoutcert/images/google.jpg"/></a></td>

                                </tr>
                                <tr>
                                    <td colspan="2" align="center">
                                        %{--<a href="/scoutcert/openId/facebook"><img src="/scoutcert/images/facebook.jpg"/></a>--}%

                                        <fb:login-button class="fbconnect_login" size="large" length="long" background="white"
                                                onlogin="javascript:FB.Connect.requireSession(facebook_onlogin);">Facebook</fb:login-button>

                                    </td>
                                </tr>

                            </table>

                            <hr/>

                            <div style="margin-top:30px; text-align:center">
                                %{--<g:link controller="login" action="forgotPassword">Forgot Password</g:link> |--}%
                                <h2><g:message code="login.firstTimeHere"/></h2>

                                <g:link controller="login" action="accountLink"><g:message code="label.createAccount"/></g:link>
                            </div>

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
<div id="fb-root"></div>
<script
        src="http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php/en_US"
        type="text/javascript"></script>
<script type="text/javascript">
    //    FB.init("d6fc406cd3f5f8d3458eda5bd4e19e75", "/scoutcert/static/xd_receiver.html");

    function facebook_onlogin() {
        FB.Connect.ifUserConnected(function () {
            window.location = '/scoutcert/openId/facebook';
        });
    }
    <!--
    jQuery(document).ready(function() {
        document.forms['loginForm'].elements['j_username'].focus();
        FB_RequireFeatures(["XFBML"], function() {

            FB.Facebook.init("d6fc406cd3f5f8d3458eda5bd4e19e75", "/scoutcert/static/xd_receiver.html");
            FB.Facebook.get_initialized().waitUntilReady(function() {
//                setTimeout(function() {
                jQuery(".FB_login_button").find("img").attr("src", "/scoutcert/images/facebook.jpg")
//                }, 150)
            })


        });
    });


    // -->
</script>
</body>
</html>
