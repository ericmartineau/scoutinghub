<html>
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="leader.create.title"/></title>
    <script type="text/javascript">

        function useLeader(leaderId) {
            jQuery.getJSON("/scoutinghub/leader/getLeaderDetails/" + leaderId, {}, function(json) {
                jQuery("#firstName").val(json.firstName);
                jQuery("#lastName").val(json.lastName);
                jQuery("#email").val(json.email);
                jQuery("#scoutid").val(json.scoutid);
                jQuery("#id").val(json.id);
                jQuery(".foundIssues").html("");
            });
        }

        function findMatch() {
            var id = jQuery("#id").val();
            var firstName = jQuery("#firstName").val();
            var lastName = jQuery("#lastName").val();
            var email = jQuery("#email").val();
            var scoutid = jQuery("#scoutid").val();

            var postData = {id:id, firstName:firstName, lastName:lastName, email:email, scoutid:scoutid};
            if (!id) {

                jQuery.ajax({
                    url: "/scoutinghub/leader/findLeaderMatch",
                    data: postData,
                    success: function(data) {
                        if (data) {
                            jQuery(".foundIssues").html(data);
                        }
                    }
                });
            } else {
                jQuery.getJSON("/scoutinghub/leader/recheckLeaderMatch", postData, function(json) {
                    if(!json.check) {
                        jQuery("#id").val("");
                        findMatch();
                    }
                });

            }
        }

        jQuery(document).ready(function() {
            keypressDelay('findMatchFirstName', jQuery("#firstName"), findMatch, 300);
            keypressDelay('findMatchLastName', jQuery("#lastName"), findMatch, 300);
            keypressDelay('findMatchEmail', jQuery("#email"), findMatch, 300);
            keypressDelay('findMatchScoutid', jQuery("#scoutid"), findMatch, 300);


        });
    </script>
</head>

<body>

<s:content>

    <g:form action="accountLink">

        <s:section>
            <s:sectionHeader icon="profile-icon" code="flow.locateAccount.createNewAccount"/>

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

            <s:propertyList class="vertical-form">
                <g:hiddenField name="id" id="id" value=""/>
                <s:textField name="email" value="${createAccount?.email}" code="${message(code:'leader.email.label')}"
                             placeholder="${message(code:'leader.email.label')}"/>
                <s:textField name="firstName" value="${createAccount?.firstName}" code="${message(code:'leader.firstName.label')}"
                             placeholder="${message(code:'leader.firstName.label')}"/>
                <s:textField name="lastName" value="${createAccount?.lastName}" code="${message(code:'leader.lastName.label')}"
                             placeholder="${message(code:'leader.lastName.label')}"/>
                <s:textField name="scoutid" value="${createAccount?.scoutid}" code="${message(code:'label.scoutid')}"
                             placeholder="${message(code:'label.scoutid')}"/>

            </s:propertyList>
            <s:div class="foundIssues">

            </s:div>

        </s:section>

        <s:section>
            <s:sectionHeader code="flow.locateAccount.unitInfo" icon="units-icon"/>
        %{--<s:text>--}%
        %{--<g:message code="flow.locateAccount.unitCreateLater"/>--}%
        %{--</s:text>--}%



            <s:propertyList class="vertical-form">

                <s:selecter class="selecter"
                            id="unitPosition" name="unitPosition" value="${createAccount?.unitPosition?.name()}" code="${message(code:'label.unitPosition')}"
                            placeholder="${message(code:'label.unitPosition')}">
                    <s:unitSelectorOptions/>
                </s:selecter>

                <s:textField name="unitNumber" otherAttrs="[idfield:'unitNumberId', positionfield:'unitPosition']" class="unitSelector unit-selector-style" value="${scoutGroup}"
                             code="${message(code:'label.unit')}"
                             placeholder="${message(code:'label.unit')}"/>

            </s:propertyList>


        %{--<s:unitSelector name="unitNumber" class="unitSelector" value="${createAccount?.unitNumber}" code="${message(code:'label.unitNumber')}"/>--}%
            <g:hiddenField name="unitNumberId" value="${scoutGroup?.id}"/>

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
</html>
