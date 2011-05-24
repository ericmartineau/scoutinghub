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

        function editPermissions(leaderId) {
            createDialog("/scoutcert/leaderGroup/permissions/" + leaderId, {}, {

                    });
        }

        jQuery(document).ready(function() {
            var pct = parseInt(jQuery(this).attr("pct"));
            jQuery("#trainingCompletion").progressbar({value:pct});

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

<s:content class="floatContent profile">

%{--No equivalent of jsp:attribute in jsp, so there's no way to do it later --}%
<g:set var="menu" scope="request">
    <li><g:link action="foo">Edit My Profile</g:link></li>
</g:set>

<s:section class="floatSection myprofile">
    <s:sectionHeader icon="profile-icon" code="leader.profile.myprofile">
        <s:ctxmenu>
            <g:ctxmenuItem class="">
                <g:link style="white-space:nowrap;" action="view" id="${leader.id}" params="[edit:true]">
                    <g:inlineIcon class="edit-icon"/>
                    <g:ctxmenuLabel>
                        <g:message code="leader.profile.edit"/>
                    </g:ctxmenuLabel>
                </g:link>
            </g:ctxmenuItem>
            <g:ctxmenuItem class="">
                <a href="javascript:addScoutingId(${leader.id})">
                    <g:inlineIcon class="add-icon"/>
                    <g:ctxmenuLabel>
                        <g:message code="leader.profile.addAnother"/>
                    </g:ctxmenuLabel>
                </a>
            </g:ctxmenuItem>

        </s:ctxmenu>
    </s:sectionHeader>


<g:set var="menu" value="" scope="request" />
    <s:propertyList class="edit-profile">
        <g:if test="${params.edit}">
            <g:hasErrors bean="${flash.leaderError}">
                <g:msgbox type="error">
                    <g:renderErrors bean="${flash.leaderError}"/>
                </g:msgbox>
            </g:hasErrors>
            <g:form action="saveProfile">
                <g:hiddenField name="id" value="${leader.id}"/>
                <s:bigTextField name="firstName" code="leader.firstName.label" value="${leader?.firstName}"/>
                <s:bigTextField name="lastName" code="leader.lastName.label" value="${leader?.lastName}"/>
                <s:bigTextField name="email" code="leader.email.label" value="${leader?.email}"/>
                <s:bigTextField name="phone" code="leader.phone.label" value="${leader?.phone}"/>
                <s:property>
                    <s:submit class="ui-state-active" id="#profileSubmit" name="submit"
                              value="${message(code:'Save')}"/>
                </s:property>
            </g:form>
        </g:if>

        <g:else>

            <s:div class="alternate-color prop-container">
                <s:property code="leader.firstName.label">${leader?.firstName}</s:property>
                <s:property code="leader.lastName.label">${leader?.lastName}</s:property>
            </s:div>

            <s:div class="prop-container">
                <s:property code="leader.email.label">${leader?.email ?: message(code: 'leader.email.noneFound')}</s:property>
                <s:property code="leader.phone.label">${leader?.phone ?: message(code: 'leader.phone.noneFound')}</s:property>
            </s:div>


            <s:div class="alternate-color prop-container">

                <s:property code="leader.profile.scoutingids">
                    <g:if test="${leader?.myScoutingIds?.size()}">
                        <g:each in="${leader.myScoutingIds}" var="myScoutingId">
                            <div class="myId">${myScoutingId.myScoutingIdentifier}</div>
                        </g:each>

                    </g:if>
                    <g:else>
                        <a href="javascript:addScoutingId(${leader.id})"><g:message
                                code="leader.profile.noneYet"/></a>

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

            </s:div>
        </g:else>
    </s:propertyList>
</s:section>




<s:section class="floatSection">
    <s:sectionHeader icon="units-icon" code="leader.profile.groups">
        <s:ctxmenu>
            <g:ctxmenuItem>
                <a href="javascript:addToGroup(${leader.id})">
                    <g:inlineIcon class="edit-icon"/>
                    <g:ctxmenuLabel>
                        <g:message code="leader.profile.addAnotherUnit" args="[leader.firstName]"/>
                    </g:ctxmenuLabel>

                </a>
            </g:ctxmenuItem>

            <g:ctxmenuItem>
                <a href="javascript:editPermissions(${leader?.id})">
                    <g:inlineIcon class="add-icon"/>
                    <g:ctxmenuLabel>
                        <g:message code="leader.profile.editPermission" args="[leader?.firstName]"/>
                    </g:ctxmenuLabel>
                </a>
            </g:ctxmenuItem>

        </s:ctxmenu>
    </s:sectionHeader>
    <s:propertyList>
        <g:if test="${leader?.groups?.size()}">
            <g:set var="i" value="${0}" scope="request"/>
            <g:each in="${leader.groups}" var="group">
                <g:if test="${request.i%2==0}">
                    <g:set var="i" value="${0}" scope="request"/>
                    <g:if test="${currClass == 'alternate-color'}">
                        <g:set var="currClass" value=""/>
                    </g:if>
                    <g:else>
                        <g:set var="currClass" value="alternate-color"/>
                    </g:else>
                </g:if>
                <g:set var="i" value="${request.i+1}" scope="request"/>
                <s:leaderUnit code="${group?.position}.label" class="${currClass}">
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
                        <div><g:link class="manage-this-unit" controller="scoutGroup" action="show"
                                     id="${group?.scoutGroup?.id}">
                            <g:message code="scoutGroup.manage"
                                       args="[group?.scoutGroup?.groupType?.name()?.humanize()]"/>
                        </g:link></div>
                    </p:canAdministerGroup>
                </s:leaderUnit>
            </g:each>
            <g:if test="${request.i==1}">
                <s:leaderUnit class="${currClass}"/>
            </g:if>
        </g:if>
        <g:else>
            <s:property><a href="javascript:addToGroup(${leader.id})"><g:message
                    code="leader.profile.noneYet"/></a></s:property>
        </g:else>
    </s:propertyList>
</s:section>

<s:section class="floatSection">
    <s:sectionHeader code="leader.profile.mytraining"  icon="training-icon" />

    <g:if test="${!certificationInfo}">
        <s:msg type="warning" code="leader.profile.notInUnit"/>
    </g:if>

    <g:set scope="request" var="certIndex" value="${0}" />
    <g:each in="${certificationInfo}" var="certification">
        <g:if test="${request.certIndex%2 == 0}">

            <g:if test="${request.currClass == 'alternate-color'}">
                <g:set var="currClass" value="" scope="request"/>
            </g:if>
            <g:else>
                <g:set var="currClass" value="alternate-color" scope="request"/>
            </g:else>
        </g:if>
        <g:set var="certIndex" value="${request.certIndex+1}" scope="request"/>
        <s:leaderTraining certificationInfo="${certification}"/>
    </g:each>

    <g:if test="${request.certIndex%2==1}">
        <s:div class="profileCertificationContainer ${request.currClass}"><s:div class="profileCertification"/></s:div>
    </g:if>

</s:section>
</s:content>

</body>
</html>