<head>
    <meta name='layout' content='main'/>
    <title><g:message code="flow.selectUsernameAndPassword.title"/></title>
</head>

<body>

<div class='body'>

        %{--<g:header><g:message code="flow.verifyUserPass.title"/></g:header>--}%


    <div>
        <g:form action="accountLink">

            <table width="100%">
                <tr>
                    <td width="100%" valign="top" align="center">
                        <div style="width:425px">
                            <g:msgbox type="info" code="flow.selectUsernameAndPassword.title" code2="flow.selectUsernameAndPassword.message" />
                        </div>

                        <g:if test="${error}">
                            <div style="width:425px">
                                <g:msgbox type="error" code="${error}" />
                            </div>

                        </g:if>
                        <table>
                            <tr>
                                <td align="left">

                                    <div class="fldContainer">
                                        <label class="biglabel" for='username'><g:message code="label.username"/></label><br/>
                                        <g:textField class="loginForm ui-corner-all horizButton" name='username' value='${createAccount.username}'/>
                                    </div>

                                    <div class="fldContainer">
                                        <label class="biglabel" for='password'><g:message code="label.password"/></label><br/>
                                        <g:passwordField class="loginForm ui-corner-all horizButton" name='password' value=''/>
                                    </div>

                                    <div class="fldContainer">
                                        <label class="biglabel" for='confirmPassword'><g:message code="label.confirmPassword"/></label><br/>
                                        <g:passwordField class="loginForm ui-corner-all horizButton" name='confirmPassword' value=''/>
                                    </div>

                                    <div class="fldContainer">
                                        <g:submitButton name="submitUsernameAndPassword" class="ui-button" value="${message(code: 'flow.selectUsernameAndPassword.button')}" />
                                    </div>

                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>

        </g:form>
    </div>


</div>

</body>
