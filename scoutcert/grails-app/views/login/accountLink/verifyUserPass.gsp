<head>
    <meta name='layout' content='main'/>
    <title><g:message code="flow.verifyUserPass.title"/></title>
</head>

<body>

<div class='body'>

    <g:header><g:message code="flow.verifyUserPass.title"/></g:header>


    <div id="createAccount">
        <g:form action="accountLink">
            <div class="explain"><g:message code="flow.verifyUserPass.message"/></div>
            <table width="100%" id='createAccount'>
                <tr>
                    <td width="50%" valign="top" align="center">
                        <g:if test="${error}">
                            <div class="errors">
                                <g:message code="${error}" />
                            </div>
                        </g:if>
                        <table>
                            <tr>
                                <td align="left">

                                    <div class="fldContainer">
                                        <label class="biglabel" for='username'><g:message code="label.username"/></label><br/>
                                        <g:textField class="loginForm ui-corner-all horizButton" name='username' value=''/>
                                    </div>

                                    <div class="fldContainer">
                                        <label class="biglabel" for='password'><g:message code="label.password"/></label><br/>
                                        <g:passwordField class="loginForm ui-corner-all horizButton" name='password' value=''/>
                                    </div>

                                    <div class="fldContainer">
                                        <g:submitButton name="submitUserPassVerify" class="ui-button" value="${message(code: 'flow.verifyUserPass.button')}" />
                                    </div>

                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="50%" valign="center" align="center">

                        <div class="fldContainer">
                            <g:submitButton class="ui-button" name="verifyByEmail" value="${message(code: 'flow.verifyUserPass.verifyEmailButton')}"></g:submitButton>
                        </div>

                    </td>


                </tr>
            </table>

        </g:form>
    </div>


</div>

</body>
