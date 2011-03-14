<head>
    <meta name='layout' content='main'/>
    <title><g:message code="title.linkaccount"/></title>
</head>

<body>

<div class='body'>

    <g:header><g:message code="linkaccount.searchaccount"/></g:header>

    <div id="createAccount">
        <g:form action="accountLink">
            <div class="explain"><g:message code="text.linkaccount"/></div>
            <table width="100%" id='createAccount'>
                <tr>
                    <td width="33%" valign="top" align="center">
                        <g:if test="${scoutIdError}">
                            <div class="errors">
                                <g:message code="${scoutIdError}"/>
                            </div>
                        </g:if>

                        <table>
                            <tr>
                                <td align="left">

                                    <div class="fldContainer">
                                        <label class="biglabel" for='scoutid'><g:message code="label.scoutid"/></label><br/>
                                        <g:textField class="loginForm ui-corner-all horizButton" name='scoutid' value=''/>
                                    </div>

                                    <div class="fldContainer">
                                        <g:submitButton class="ui-button" name="findByScoutId" value="${message(code: 'label.findByScoutId')}"></g:submitButton>
                                    </div>

                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="33%" valign="top" align="center">
                        <table>
                            <tr>
                                <td align="left">
                                    <g:if test="${emailError}">
                                        <div class="errors">
                                            <g:message code="${emailError}"/>
                                        </div>
                                    </g:if>

                                    <div class="fldContainer">
                                        <label class="biglabel" for='email'><g:message code="label.email"/></label><br/>
                                        <g:textField class="loginForm ui-corner-all" name='email' value=''/>
                                    </div>

                                    <div class="fldContainer">
                                        <g:submitButton class="ui-button" name="findByEmail" value="${message(code: 'label.findByEmailAddress')}"></g:submitButton>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="33%" valign="top" align="center">
                        <table>
                            <tr>
                                <td align="left">
                                    <div class="fldContainer">
                                        <label class="biglabel" for='firstName'><g:message code="label.firstName"/></label><br/>
                                        <g:textField class="loginForm ui-corner-all" name='firstName' value=''/>
                                    </div>
                                    <div class="fldContainer">
                                        <label class="biglabel" for='lastName'><g:message code="label.lastName"/></label><br/>
                                        <g:textField class="loginForm ui-corner-all" name='lastName' value=''/>
                                    </div>
                                    <div class="fldContainer">
                                        <label class="biglabel" for='unitNumber'><g:message code="label.unitNumber"/></label><br/>
                                        <g:textField class="loginForm ui-corner-all" name='unitNumber' value=''/>
                                    </div>
                                    <div class="fldContainer">
                                        <input type="submit" class="ui-button" value="${message(code: 'label.findByUnitNumber')}">
                                    </div>
                                </td>
                            </tr>
                        </table>

                    </td>


                    %{--<td width="50%" valign="top" align="center">--}%
                    %{--<table>--}%
                    %{--<tr>--}%
                    %{--<td align="left">--}%

                    %{--<g:header><g:message code="linkaccount.createnewaccount"/></g:header>--}%
                    %{--<g:form action='createAccount'>--}%
                    %{--<g:hasErrors bean="${command}">--}%
                    %{--<div class="errors">--}%
                    %{--<g:renderErrors bean="${command}" as="list"/>--}%
                    %{--</div>--}%
                    %{--</g:hasErrors>--}%

                    %{--<g:if test='${flash.error}'>--}%
                    %{--<div class="errors">${flash.error}</div>--}%
                    %{--</g:if>--}%

                    %{--<g:message code="linkaccount.createnewaccount.warning"/><br/><br/>--}%

                    %{--<div class="fldContainer">--}%
                    %{--<label class="biglabel" for='username'><g:message code="label.username"/></label><br/>--}%
                    %{--<g:textField class="loginForm ui-corner-all" name='username' value='${command.username}'/>--}%
                    %{--</div>--}%


                    %{--<div class="fldContainer">--}%
                    %{--<label class="biglabel" for='password'>Password:</label><br/>--}%
                    %{--<g:passwordField class="loginForm ui-corner-all" name='password' value='${command.password}'/><br/>--}%
                    %{--</div>--}%

                    %{--<div class="fldContainer">--}%
                    %{--<label class="biglabel" for='password2'>Password (again):</label><br/>--}%
                    %{--<g:passwordField class="loginForm ui-corner-all" name='password2' value='${command.password2}'/>--}%
                    %{--</div>--}%

                    %{--<input class="ui-button" type='submit' value='Register'/>--}%

                    %{--</g:form>--}%

                    %{--</td>--}%
                    %{--</tr>--}%
                    %{--</table>--}%
                    %{--</td>--}%

                </tr>
            </table>

        </g:form>
    </div>


    %{--<g:if test='${openId}'>--}%
    %{--Or if you're already a user you can <g:link action='linkAccount'>link this OpenID</g:link> to your account<br/>--}%
    %{--<br/>--}%
    %{--</g:if>--}%

</div>

</body>
