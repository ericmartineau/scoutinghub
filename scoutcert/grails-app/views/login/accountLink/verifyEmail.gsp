<head>
    <meta name='layout' content='main'/>
    <title><g:message code="flow.verifyEmail.title"/></title>
</head>

<body>

<div class='body'>

    %{--<g:header><g:message code="flow.verifyEmail.title"/></g:header>--}%

    <table width="100%">
        <tr>
            <td align="center">

                <div id="createAccount" style="width:400px">
                    <g:msgbox type="info" code="flow.verifyEmail.title" code2="flow.verifyEmail.message" />
                    <g:if test="${verifyError}">
                        <g:msgbox type="error" code="${verifyError}" />

                    </g:if>

                    <g:form action="accountLink">
                    %{--<g:if test="${verifyMessage}">--}%
                    %{--<div class="explain"><g:message code="${verifyMessage}"/></div>--}%
                    %{--</g:if>--}%

                        %{--<div class="explain"><g:message code="flow.verifyEmail.message"/></div>--}%
                        <table width="100%">
                            <tr>
                                <td width="50%" valign="top" align="center">
                                    <g:if test="${error}">
                                        <div class="errors">
                                            <g:message code="${error}"/>
                                        </div>
                                    </g:if>

                                    <div class="fldContainer">
                                        <label class="biglabel" for='code'><g:message code="flow.verifyEmail.code"/></label><br/>
                                        <g:textField class="loginForm ui-corner-all horizButton" name='code' value=''/>
                                    </div>

                                    <div class="fldContainer">
                                        <g:submitButton name="processVerifyEmail" class="ui-button" value="${message(code: 'flow.verifyEmail.button')}"/>
                                        %{--<g:link style="margin-left:20px" action="accountLink" event="noCode"><g:message code="flow.verifyEmail.nocode"/></g:link>--}%
                                    </div>

                                </td>

                            </tr>
                        </table>

                    </g:form>
                </div>

            </td>
        </tr>
    </table>
</div>

</body>
