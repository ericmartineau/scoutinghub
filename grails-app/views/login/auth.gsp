<head>
    <meta name='layout' content='main'/>
    <title><g:message code="menu.login"/></title>

</head>

<body>

<div id='login'>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="100%" align="center">
                <table width="100%">
                    <tr>

                        <td align="left" width="50%">

                            <div class='inner'>
                                <g:if test='${flash.message}'>
                                    <div class='login_message'>${flash.message}</div>
                                </g:if>
                                <div class="fldContainer"></div>
                                <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                                    <div class="fldContainer">
                                        <label class='biglabel' for='username'><g:message code="label.username"/></label><br/>
                                        <input type='text' class='loginForm ui-corner-all' name='j_username' id='username'/>
                                    </div>
                                    <div class="fldContainer">
                                        <label class='biglabel' for='password'><g:message code="label.password"/></label><br/>
                                        <input type='password' class='loginForm ui-corner-all' name='j_password' id='password'/>
                                    </div>

                                    <div class="fldContainer">
                                        <span><input type='submit' value='${message(code: 'label.login')}' class="ui-button"/></span>
                                        <span><input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
                                            <g:if test='${hasCookie}'>checked='checked'</g:if>/></span>
                                        <span><label for='remember_me'><g:message code="label.rememberMe"/></label></span>

                                    </div>
                                    <p>

                                    </p>
                                </form>
                            </div>
                        </td>
                        <td valign="top" width="50%">
                            <div class="biglabel"><g:message code="login.alternateproviders" /></div>

                            <table>
                                <tr>
                                    <td><a href="/scoutcert/j_spring_openid_security_check?openid_identifier=https://www.google.com/accounts/o8/id"><img src="/scoutcert/images/facebook.jpg"/></a></td>
                                    <td><a href="/scoutcert/j_spring_openid_security_check?openid_identifier=https://www.google.com/accounts/o8/id"><img src="/scoutcert/images/google.jpg"/></a></td>

                                </tr>
                                <tr>
                                    <td><img src="/scoutcert/images/yahoo.jpg"/></td>
                                    <td></td>
                                </tr>

                            </table>

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
