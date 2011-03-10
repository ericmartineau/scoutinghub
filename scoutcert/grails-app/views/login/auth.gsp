<head>
    <meta name='layout' content='main'/>
    <title>Login</title>
    <style type="text/css">
    label.biglabel {
        font-size: 14px;
        font-weight: bold;
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
                <table>
                    <tr>
                        <td align="left">

                            <div class='inner'>
                                <g:if test='${flash.message}'>
                                    <div class='login_message'>${flash.message}</div>
                                </g:if>
                                <div class="fldContainer"></div>
                                <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                                    <div class="fldContainer">
                                        <label class='biglabel' for='username'>Username</label><br/>
                                        <input type='text' class='loginForm ui-corner-all' name='j_username' id='username'/>
                                    </div>
                                    <div class="fldContainer">
                                        <label class='biglabel' for='password'>Password</label><br/>
                                        <input type='password' class='loginForm ui-corner-all' name='j_password' id='password'/>
                                    </div>

                                    <div class="fldContainer">
                                        <span><input type='submit' value='Login' class="ui-button"/></span>
                                        <span><input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
                                            <g:if test='${hasCookie}'>checked='checked'</g:if>/></span>
                                        <span><label for='remember_me'>Remember me</label></span>

                                    </div>
                                    <p>

                                    </p>
                                </form>
                            </div>
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
