<%@ page import="scoutinghub.LeaderPositionType; org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler" %>
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="title.linkaccount"/></title>

    <g:javascript library="jquery.jstree"/>
</head>

<body>

<content tag="leftNavigation">
    <iwebkit:leftnavigation navtype="arrow">
        <iwebkit:navelement action="auth" content="${message(code:'Login')}"/>
    </iwebkit:leftnavigation>
</content>


<s:content>

    <g:form action="accountLink">

        <s:section>
            <s:sectionHeader icon="profile-icon" code="flow.locateAccount.createNewAccount" />

        %{--<g:if test="${hasSocialAuth}">--}%
        %{--<h2><g:message code="flow.locateAccount.newUsers"/></h2>--}%
        %{--</g:if>--}%
        %{--<g:else>--}%
        %{--<h2><g:message code="flow.locateAccount.createNewAccount"/></h2>--}%
        %{--</g:else>--}%

            <g:if test="${errors}">
                <s:msg type="error">
                    <div class="msg1">
                        <g:message code="label.error"/>
                    </div>
                    <div class="msg2">
                        <g:each in="${errors}" var="error">
                            <g:message code="${error}"/><br/>
                        </g:each>
                    </div>
                </s:msg>

            </g:if>

            <s:bigTextField name="firstName" value="${createAccount?.firstName}" code="${message(code:'leader.firstName.label')}"
                    placeholder="${message(code:'leader.firstName.label')}"/>
            <s:bigTextField name="lastName" value="${createAccount?.lastName}" code="${message(code:'leader.lastName.label')}"
                    placeholder="${message(code:'leader.lastName.label')}"/>
            <s:bigTextField name="email" value="${createAccount?.email}" code="${message(code:'leader.email.label')}"
                    placeholder="${message(code:'leader.email.label')}"/>
            <s:bigTextField name="scoutid" value="${createAccount?.scoutid}" code="${message(code:'label.scoutid')}"
                    placeholder="${message(code:'label.scoutid')}"/>

        </s:section>

        <s:section>
            <s:sectionHeader code="flow.locateAccount.unitInfo" icon="units-icon" />
            <s:bigTextField name="unitNumber" otherAttrs="[idField:'unitNumberId', positionField:'unitPosition']" class="unitSelector unit-selector-style" value="${createAccount?.unit}" code="${message(code:'label.unitNumber')}"
                                placeholder="${message(code:'label.unitNumber')}">
                        </s:bigTextField>

                        %{--<s:unitSelector name="unitNumber" class="unitSelector" value="${createAccount?.unitNumber}" code="${message(code:'label.unitNumber')}"/>--}%
                        <g:hiddenField name="unitNumberId" value="${createAccount?.unit?.id}"/>

            <s:bigSelecter class="selecter" from="${LeaderPositionType.values()}" optionValue="${{it.name()?.humanize()}}"
                    id="unitPosition" name="unitPosition" value="${createAccount?.unitPosition?.name()}" code="${message(code:'label.unitPosition')}"
                    noSelection="${['':"Select Position"]}"
                    placeholder="${message(code:'label.unitPosition')}"/>


        </s:section>

        <s:section>
            <s:sectionHeader code="flow.locateAccount.setPassword" />
            <s:bigTextField type="password" name="password" value="${createAccount?.password}" code="${message(code:'label.password')}"
                    placeholder="${message(code:'label.password')}"/>

            <s:bigTextField type="password" name="confirmPassword" value="${createAccount?.confirmPassword}" code="${message(code:'label.confirmPassword')}"
                    placeholder="${message(code:'label.confirmPassword')}"/>

        </s:section>

        <s:section class='centered'>
            <s:submit name="createNewAccount" value="${message(code: 'flow.locateAccount.createAccount.button')}"/>
        </s:section>

    </g:form>

</s:content>

%{--<div class='body'>--}%

%{--<g:if test="${hasSocialAuth}">--}%
%{--<g:msgbox type="success">--}%
%{--<div class="msg1"><g:message code="flow.locateAccount.openAuth" args="[session['LAST_AUTH_PROVIDER']]"/></div>--}%
%{--<div class="msg2"><g:message code="flow.locateAccount.openAuth.text" args="[session['LAST_AUTH_PROVIDER']]"/></div>--}%

%{--</g:msgbox>--}%
%{--</g:if>--}%

%{--<g:header><g:message code="linkaccount.searchaccount"/></g:header>--}%

%{--<div id="createAccount">--}%

%{--<div class="explain"><g:message code="text.linkaccount"/></div>--}%
%{--<table width="100%" id='createAccount'>--}%
%{--<tr>--}%
%{--<td width="33%" valign="top" align="center">--}%
%{--<g:if test="${scoutIdError}">--}%
%{--<div class="errors">--}%
%{--<g:message code="${scoutIdError}"/>--}%
%{--</div>--}%
%{--</g:if>--}%

%{--<table>--}%
%{--<tr>--}%
%{--<td align="left">--}%

%{--<div class="fldContainer">--}%
%{--<label class="biglabel" for='scoutid'><g:message code="label.scoutid"/></label><br/>--}%
%{--<g:textField class="loginForm ui-corner-all horizButton" name='scoutid' value=''/>--}%
%{--</div>--}%

%{--<div class="fldContainer">--}%
%{--<g:submitButton class="ui-button" name="findByScoutId" value="${message(code: 'label.findByScoutId')}"></g:submitButton>--}%
%{--</div>--}%

%{--</td>--}%
%{--</tr>--}%
%{--</table>--}%
%{--</td>--}%
%{--<g:if test="${hasSocialAuth}">--}%
%{--<td width="50%" valign="top" align="center">--}%
%{--<g:form action="accountLink">--}%
%{--<h2><g:message code="flow.locateAccount.returningUsers"/></h2>--}%
%{--<table>--}%
%{--<tr>--}%
%{--<td align="left">--}%

%{--<g:if test="${linkError}">--}%
%{--<div class="errors">--}%
%{--<g:msgbox type="error" code="${linkError}"/>--}%
%{--</div>--}%
%{--</g:if>--}%
%{--<div class="fldContainer">--}%
%{--<label class="biglabel" for='username'><g:message code="label.username"/></label><br/>--}%
%{--<g:textField class="loginForm ui-corner-all" name='username' value='${username}'/>--}%
%{--</div>--}%
%{--<div class="fldContainer">--}%
%{--<label class="biglabel" for='password'><g:message code="label.password"/></label><br/>--}%
%{--<g:passwordField class="loginForm ui-corner-all" name='password' value=''/>--}%
%{--</div>--}%



%{--<div class="fldContainer">--}%
%{--<g:submitButton class="ui-button" name="linkSocial" value="${message(code: 'flow.locateAccount.linkButton.label', args: [session['LAST_AUTH_PROVIDER']])}"></g:submitButton>--}%
%{--</div>--}%
%{--</td>--}%
%{--</tr>--}%
%{--</table>--}%
%{--</g:form>--}%
%{--</td>--}%
%{--</g:if>--}%
%{--<td valign="top" width="50%" align="center">--}%
%{--<g:form action="accountLink">--}%
%{--<g:if test="${hasSocialAuth}">--}%
%{--<h2><g:message code="flow.locateAccount.newUsers"/></h2>--}%
%{--</g:if>--}%
%{--<g:else>--}%
%{--<h2><g:message code="flow.locateAccount.createNewAccount"/></h2>--}%
%{--</g:else>--}%

%{--<g:if test="${errors}">--}%
%{--<div style="width:350px">--}%
%{--<g:msgbox type="error">--}%
%{--<div class="msg1">--}%
%{--<g:message code="label.error"/>--}%
%{--</div>--}%
%{--<div class="msg2">--}%
%{--<g:each in="${errors}" var="error">--}%
%{--<g:message code="${error}"/><br/>--}%
%{--</g:each>--}%
%{--</div>--}%
%{--</g:msgbox>--}%
%{--</div>--}%

%{--</g:if>--}%

%{--<table>--}%
%{--<tr>--}%
%{--<td align="left">--}%
%{--<div class="fldContainer">--}%
%{--<label class="biglabel" for='firstName'><g:message code="leader.firstName.label"/></label><br/>--}%
%{--<g:textField class="loginForm ui-corner-all" name='firstName' value='${createAccount?.firstName}'/>--}%
%{--</div>--}%
%{--<div class="fldContainer">--}%
%{--<label class="biglabel" for='lastName'><g:message code="leader.lastName.label"/></label><br/>--}%
%{--<g:textField class="loginForm ui-corner-all" name='lastName' value='${createAccount?.lastName}'/>--}%
%{--</div>--}%
%{--<div class="fldContainer">--}%
%{--<label class="biglabel" for='email'><g:message code="leader.email.label"/></label><br/>--}%
%{--<g:textField class="loginForm ui-corner-all" name='email' value='${createAccount?.email}'/>--}%
%{--</div>--}%

%{--<div class="fldContainer">--}%
%{--<label class="biglabel" for='unitNumber'><g:message code="label.unitNumber"/></label><br/>--}%
%{--<g:textField class="loginForm ui-corner-all" name='unitNumber' value='${createAccount?.unitNumber}'/>--}%
%{--</div>--}%

%{--<div class="fldContainer">--}%
%{--<label class="biglabel" for='unitNumber'><g:message code="label.scoutid"/></label><br/>--}%
%{--<g:textField class="loginForm ui-corner-all" name='scoutid' value='${createAccount?.scoutid}'/>--}%
%{--</div>--}%
%{--<div class="fldContainer">--}%
%{--<g:submitButton class="ui-button" name="createNewAccount" value="${message(code: 'flow.locateAccount.createAccount.button')}"></g:submitButton>--}%
%{--<g:link class="buttonLink" controller="leader"><g:message code="label.goback"/></g:link>--}%
%{--</div>--}%
%{--</td>--}%
%{--</tr>--}%
%{--</table>--}%
%{--</g:form>--}%
%{--</td>--}%


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

%{--</tr>--}%
%{--</table>--}%

%{--</div>--}%


%{--<g:if test='${openId}'>--}%
%{--Or if you're already a user you can <g:link action='linkAccount'>link this OpenID</g:link> to your account<br/>--}%
%{--<br/>--}%
%{--</g:if>--}%

%{--</div>--}%

</body>
