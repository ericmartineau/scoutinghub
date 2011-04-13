<%@ page import="scoutcert.Role" %>
<html>
<head>
    <title><g:message code="menu.leader.profile"/></title>
    <g:if test="${session.isMobile == true}">
        <meta name="layout" content="iwebkit"/>
    </g:if>
    <g:else>
        <meta name="layout" content="main"/>
    </g:else>
    <script type="text/javascript">

        function togglePermission(checkbox, leaderid, roleid) {
            jQuery.ajax({
                url:"/scoutcert/permissions/setPermission",
                data: {checked: checkbox.checked, leaderId:leaderid, roleId: roleid}
            });
        }


        function addScoutingId(leaderId) {
            createDialog("/scoutcert/myScoutingId/create", {'leader.id': leaderId}, {
                title: "${message(code:'leader.profile.addScoutingId')}",
                width: 400,
                modal:true
            });

        }

        function addToGroup(leaderId) {
            createDialog("/scoutcert/leaderGroup/create", {'leader.id': leaderId}, {
                title: "${message(code:'leader.profile.addToGroup')}",
                width: 400,
                modal:true
            });
        }

        function markTrainingComplete(leaderId, certificationId) {
            jQuery("#dialog").remove();
            jQuery("<div id='dialog'></div>").load("/scoutcert/leaderCertification/createForm", {certificationId:certificationId}, function(result) {

                jQuery(this).dialog({
                    title: "${message(code:'leader.profile.enterTrainingDetails')}",
                    width: 400,
                    modal:true,
                    buttons: {
                        'Save': function() {
                            var postData = {leaderId:leaderId, certificationId:certificationId, dateEarned:jQuery("#trainingDate").val()};
                            jQuery.getJSON("/scoutcert/leaderCertification/saveCertification", postData, function(json) {
                                if (json.success) {
                                    window.location = "/scoutcert/leader/view/" + leaderId;
                                } else {
                                    alert(json.message)
                                }
                            });

                        }
                    }
                })
            });

        }

        jQuery(document).ready(function() {
            var pct = parseInt(jQuery(this).attr("pct"));
            jQuery("#trainingCompletion").progressbar({value:pct})

            jQuery(".profileCertificationContainer").each(function() {
                var jthis = jQuery(this);
                var certificationId = parseInt(jthis.attr("certificationId"));
                var leaderId = parseInt(jthis.attr("leaderId"));

                if (certificationId > 0) {
                    jQuery.get("/scoutcert/certificationClass/findByCertification", {certificationId:certificationId, leaderId:leaderId},
                            function(data) {
                                if (data) {
                                    jthis.find(".upcomingTrainings").append("<div class='currentTraining ui-corner-all'>" + data + "</div>");

                                }

                            });
                }
            });

        });


    </script>

</head>

<body>

<s:content class="floatContent">
    <s:section code="leader.profile.myprofile" class="floatSection">
        <s:property code="leader.firstName.label">${leader?.firstName}</s:property>
        <s:property code="leader.lastName.label">${leader?.lastName}</s:property>
        <s:property code="leader.email.label">${leader?.email ?: message(code: 'leader.email.noneFound')}</s:property>
        <s:property code="leader.phone.label">${leader?.phone ?: message(code: 'leader.phone.noneFound')}</s:property>

        <s:property code="leader.profile.scoutingids">
            <g:if test="${leader?.myScoutingIds?.size()}">
                <g:each in="${leader.myScoutingIds}" var="myScoutingId">
                    <div class="myId">${myScoutingId.myScoutingIdentifier}</div>
                </g:each>
                <div class="myId">
                    <a href="javascript:addScoutingId(${leader.id})"><g:message code="leader.profile.addAnother"/></a>
                </div>
            </g:if>
            <g:else>
                <a href="javascript:addScoutingId(${leader.id})"><g:message code="leader.profile.noneYet"/></a>
            </g:else>
        %{--<s:bigTextField name="myScoutingIdentifier" placeholder="Do something here" />--}%

        </s:property>

        <s:property code="leader.setupDate.label">
            <g:if test="${leader?.setupDate}">
                <g:formatDate date="${leader?.setupDate}" format="MM-dd-yyyy"/>
            </g:if>
            <g:else>
                Not Set Up Yet
            </g:else>
        </s:property>





    %{--<sec:ifAllGranted roles="ROLE_ADMIN">--}%
    %{----}%
    %{--<s:property code="Permissions" class="permissions">--}%
    %{--<g:each in="${Role.list()}" var="role">--}%
    %{--<s:permission leader="${leader}" role="${role}"/>--}%
    %{--</g:each>--}%
    %{--</s:property>--}%
    %{----}%
    %{--</sec:ifAllGranted>--}%
    </s:section>



%{--<s:section class="floatSection" code="leader.profile.scoutingids">--}%
%{--<g:if test="${leader?.myScoutingIds?.size()}">--}%
%{--<g:each in="${leader.myScoutingIds}" var="myScoutingId">--}%
%{--<s:property>${myScoutingId.myScoutingIdentifier}</s:property>--}%
%{--</g:each>--}%
%{--</g:if>--}%
%{--<s:bigTextField name="myScoutingIdentifier" placeholder="Do something here" />--}%
%{--<s:property><a href="javascript:addScoutingId(${leader.id})"><g:message code="leader.profile.noneYet"/></a></s:property>--}%

%{--<g:else>--}%
%{--</g:else>--}%
%{--</s:section>--}%

    <s:section class="floatSection" code="leader.profile.groups">
        <g:if test="${leader?.groups?.size()}">
            <g:each in="${leader.groups}" var="group">
                <s:property code="${group?.position}.label">
                    ${group?.scoutGroup?.groupLabel ?: group?.scoutGroup?.groupIdentifier}
                    <g:if test="${group?.admin}">(admin)</g:if>
                    <g:else>
                        <g:ifNotSelf leader="${leader}">
                            <p:canAdministerGroup scoutGroup="${group?.scoutGroup}">

                                <g:link controller="scoutGroup" action="makeAdmin" id="${group?.id}">
                                    <div><g:message code="scoutGroup.makeAdmin"
                                            args="[leaderName(leader:leader, selfName:'yourself'), group?.scoutGroup?.groupType?.name()?.humanize()]"/></div>
                                </g:link>
                            </p:canAdministerGroup>
                        </g:ifNotSelf>
                    </g:else>
                    <p:canAdministerGroup scoutGroup="${group?.scoutGroup}">
                        <div><g:link controller="scoutGroup" action="show" id="${group?.scoutGroup?.id}">
                            <g:message code="scoutGroup.manage" args="[group?.scoutGroup?.groupType?.name()?.humanize()]"/>
                        </g:link></div>
                    </p:canAdministerGroup>
                </s:property>
            </g:each>
            <s:property><a href="javascript:addToGroup(${leader.id})"><g:message code="leader.profile.addAnotherUnit"/></a></s:property>

        </g:if>

        <g:else>
            <s:property><a href="javascript:addToGroup(${leader.id})"><g:message code="leader.profile.noneYet"/></a></s:property>
        </g:else>

    </s:section>

    <s:section class="floatSection" code="leader.profile.mytraining">

    %{--<table cellpadding="5">--}%
    %{--<tr>--}%
    %{--<td>--}%
    %{--<g:header><g:message code="leader.profile.myprofile"/></g:header>--}%

    %{--<g:header><g:message code=""/></g:header>--}%

    %{--<table class="recordTable" cellspacing="0">--}%
    %{--<tr>--}%
    %{--<td valign="top">--}%
    %{--<g:message code="leader.firstName.label"/>:<br/>--}%
    %{--<span class="profileData">${leader?.firstName}</span>--}%
    %{--</td>--}%
    %{--<td valign="top">--}%
    %{--<g:message code="leader.lastName.label"/>:<br/>--}%
    %{--<span class="profileData">${leader?.lastName}</span>--}%
    %{--</td>--}%
    %{--</tr>--}%
    %{--<tr>--}%
    %{--<td valign="top">--}%
    %{--<g:message code="leader.email.label"/>:<br/>--}%
    %{--<span class="profileData">${leader?.email}</span>--}%
    %{--</td>--}%
    %{--<td valign="top">--}%
    %{--<g:message code="leader.setupDate.label"/>:<br/>--}%
    %{--<span class="profileData">--}%
    %{--<g:if test="${leader?.setupDate}">--}%
    %{--<g:formatDate date="${leader?.setupDate}" format="MM-dd-yyyy"/>--}%
    %{--</g:if>--}%
    %{--<g:else>--}%
    %{--Not Set Up Yet--}%
    %{--</g:else>--}%
    %{--</span>--}%
    %{--</td>--}%
    %{--</tr>--}%
    %{--<tr>--}%
    %{--<td valign="top">--}%
    %{--<g:message code="leader.profile.scoutingids"/>:<br/>--}%
    %{--<g:if test="${leader?.myScoutingIds?.size()}">--}%
    %{--<g:each in="${leader.myScoutingIds}" var="myScoutingId">--}%
    %{--<div class="profileData">${myScoutingId.myScoutingIdentifier}</div>--}%
    %{--</g:each>--}%
    %{--</g:if>--}%
    %{--<g:else>--}%
    %{--<div class="profileData">None (Yet)</div>--}%
    %{--</g:else>--}%
    %{--</td>--}%
    %{--<td valign="top">--}%
    %{--<g:if test="${leader?.groups?.size()}">--}%
    %{--<g:each in="${leader.groups}" var="group">--}%
    %{--<div class="fldContainer">--}%
    %{--<g:message code="${group?.position}.label"/>--}%
    %{--<div class="profileData">--}%
    %{--${group?.scoutGroup?.groupLabel ?: group?.scoutGroup?.groupIdentifier}<br/>--}%
    %{--</div>--}%

    %{--</table>--}%
    %{--<g:header><g:message code="leader.profile.mytraining"/></g:header>--}%
    %{--<g:if test="${trainingCompletePct > 0}">--}%
    %{--<div id="trainingCompletion" pct="${trainingCompletePct}"></div>--}%
    %{--</g:if>--}%

    %{--<div class="groupSection">--}%
        <g:if test="${!certificationInfo}">
            <s:msg type="warning" code="leader.profile.notInUnit"/>
        </g:if>
        <g:each in="${certificationInfo}" var="certification">
            <s:leaderTraining certificationInfo="${certification}"/>
        </g:each>

    %{--</div>--}%

    %{--</td>--}%
    %{--<td valign="top" width="200">--}%
    %{--<g:header><g:message code="ui.tools"/></g:header>--}%
    %{--<div class="groupSection">--}%
    %{--<div class="toolboxItem"><a href="javascript:attachScoutingId(${leader?.id})"><g:message code="leader.profile.linkScoutingId"/></a></div>--}%
    %{--<div class="toolboxItem"><a href="javascript:attachUnit(${leader?.id})"><g:message code="leader.profile.addToUnit"/></a></div>--}%
    %{--</div>--}%
    %{--</td>--}%
    %{--</tr>--}%

    %{--</table>--}%

    </s:section>
</s:content>

</body>
</html>