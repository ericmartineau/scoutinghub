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

    %{--No equivalent of jsp:attribute in jsp, so there's no way to do it later --}%
    <g:set var="menu" scope="request">
        <li><g:link action="foo">Edit My Profile</g:link></li>
    </g:set>

    <s:section class="floatSection">
        <g:header>
            <g:message code="leader.profile.myprofile" />
            <g:ctxmenu>
                %{--<li><g:link><g:message code="leader.profile.edit" /></g:link></li>--}%
                <li><a href="javascript:addScoutingId(${leader.id})"><g:message code="leader.profile.addAnother"/></a></li>
            </g:ctxmenu>
        </g:header>

    %{--<g:set var="menu" value="" scope="request" />--}%
        <s:propertyList>
            <s:property code="leader.firstName.label">${leader?.firstName}</s:property>
            <s:property code="leader.lastName.label">${leader?.lastName}</s:property>
            <s:property code="leader.email.label">${leader?.email ?: message(code: 'leader.email.noneFound')}</s:property>
            <s:property code="leader.phone.label">${leader?.phone ?: message(code: 'leader.phone.noneFound')}</s:property>

            <s:property code="leader.profile.scoutingids">
                <g:if test="${leader?.myScoutingIds?.size()}">
                    <g:each in="${leader.myScoutingIds}" var="myScoutingId">
                        <div class="myId">${myScoutingId.myScoutingIdentifier}</div>
                    </g:each>

                </g:if>
                <g:else>
                    <a href="javascript:addScoutingId(${leader.id})"><g:message code="leader.profile.noneYet"/></a>
                </g:else>

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
        </s:propertyList>
    </s:section>




    <s:section class="floatSection">
        <g:header>
            <g:message code="leader.profile.groups" />
            <g:ctxmenu>
                <li><a href="javascript:addToGroup(${leader.id})"><g:message code="leader.profile.addAnotherUnit" args="[leader.firstName]"/></a></li>
            </g:ctxmenu>
        </g:header>
        <s:propertyList>
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
            </g:if>
            <g:else>
                <s:property><a href="javascript:addToGroup(${leader.id})"><g:message code="leader.profile.noneYet"/></a></s:property>
            </g:else>
        </s:propertyList>
    </s:section>

    <s:section class="floatSection" code="leader.profile.mytraining">
        <g:if test="${!certificationInfo}">
            <s:msg type="warning" code="leader.profile.notInUnit"/>
        </g:if>
        <g:each in="${certificationInfo}" var="certification">
            <s:leaderTraining certificationInfo="${certification}"/>
        </g:each>

    </s:section>
</s:content>

</body>
</html>