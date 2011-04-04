<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="flow.verifyUserPass.title"/></title>
</head>

<body>
<content tag="leftNavigation">
    <iwebkit:leftnavigation navtype="arrow">
        <iwebkit:navelement action="auth" content="${message(code:'Login')}"/>
    </iwebkit:leftnavigation>
</content>

<s:content>
    <g:form action="accountLink">

        <s:section header="small" code="flow.verifyUserPass.title">
            <s:msg type="warning" code="flow.verifyUserPass.message"/>
            <g:if test="${error}">
                <s:msg type="error" code="${error}" code2="${error2}"/>
            </g:if>

            <s:bigTextField name="username" code="${message(code:'label.username')}" placeholder="${message(code:'label.username')}"/>
            <s:bigTextField type="password" name="password" code="${message(code:'label.password')}" placeholder="${message(code:'label.password')}"/>

        </s:section>

        <s:section>
            <s:submit name="submitUserPassVerify" class="ui-button" value="${message(code: 'flow.verifyUserPass.button')}"/>
        %{--<div class="fldContainer">--}%
        %{--<label class="biglabel" for='username'><g:message code="label.username"/></label><br/>--}%
        %{--<g:textField class="loginForm ui-corner-all horizButton" name='username' value=''/>--}%
        %{--</div>--}%
        %{----}%
        %{--<div class="fldContainer">--}%
        %{--<label class="biglabel" for='password'><g:message code="label.password"/></label><br/>--}%
        %{--<g:passwordField class="loginForm ui-corner-all horizButton" name='password' value=''/>--}%
        %{--</div>--}%
        %{----}%
        %{--<div class="fldContainer">--}%
        %{--<g:submitButton name="submitUserPassVerify" class="ui-button" value="${message(code: 'flow.verifyUserPass.button')}"/>--}%
        %{--</div>--}%

        </s:section>

    </g:form>

    <s:section header="small">
    %{--<s:section header="small" code="flow.verifyUserPass.verifyEmailInstead">--}%

        <s:bigButton controller="login" action="accountLink" event="verifyByEmail" value="${message(code:'flow.verifyUserPass.verifyEmailInstead')}"/>


    %{--name="verifyByEmail" value="${message(code: '')}" />--}%
    </s:section>

</s:content>

%{--<div class='body'>--}%

%{--<g:header><g:message code="flow.verifyUserPass.title"/></g:header>--}%


%{--<div id="createAccount">--}%
%{--<g:form action="accountLink">--}%
%{--<div class="explain"><g:message code="flow.verifyUserPass.message"/></div>--}%
%{--<table width="100%" id='createAccount'>--}%
%{--<tr>--}%
%{--<td width="50%" valign="top" align="center">--}%
%{--<table>--}%
%{--<tr>--}%
%{--<td align="left">--}%

%{--<div class="fldContainer">--}%
%{--<label class="biglabel" for='username'><g:message code="label.username"/></label><br/>--}%
%{--<g:textField class="loginForm ui-corner-all horizButton" name='username' value=''/>--}%
%{--</div>--}%

%{--<div class="fldContainer">--}%
%{--<label class="biglabel" for='password'><g:message code="label.password"/></label><br/>--}%
%{--<g:passwordField class="loginForm ui-corner-all horizButton" name='password' value=''/>--}%
%{--</div>--}%

%{--<div class="fldContainer">--}%
%{--<g:submitButton name="submitUserPassVerify" class="ui-button" value="${message(code: 'flow.verifyUserPass.button')}"/>--}%
%{--</div>--}%

%{--</td>--}%
%{--</tr>--}%
%{--</table>--}%
%{--</td>--}%
%{--<td width="50%" valign="center" align="center">--}%

%{--<div class="fldContainer">--}%
%{--<g:submitButton class="ui-button" name="verifyByEmail" value="${message(code: 'flow.verifyUserPass.verifyEmailButton')}"></g:submitButton>--}%
%{--</div>--}%

%{--</td>--}%

%{--</tr>--}%
%{--</table>--}%

%{--</g:form>--}%
%{--</div>--}%

%{--</div>--}%

</body>
