<%@ page import="scoutinghub.Role" %>
<html>
<head>
    <title><g:message code="menu.leader.profile"/></title>
    <meta name="layout" content="${layoutName}"/>
    <r:script>

        function togglePermission(checkbox, leaderid, roleid) {
            jQuery.ajax({
                url: "/scoutinghub/permissions/setPermission",
                data: {checked: checkbox.checked, leaderId: leaderid, roleId: roleid}
            });
        }

        function enterTrainingDetails(id) {
            jQuery("#completeTraining" + id).click();
        }

        function ignoreDuplicate(leaderIdA, leaderIdB) {
            jQuery.getJSON("/scoutinghub/leader/ignoreDuplicate", {leaderA: leaderIdA, leaderB: leaderIdB}, function (json) {
                if (json.success) {
                    window.location.reload();
                }
            });
        }

        jQuery(document).ready(function () {
            var pct = parseInt(jQuery(this).attr("pct"));
            jQuery("#trainingCompletion").progressbar({value: pct});

            jQuery(".leader-unit").mouseover(
                    function () {
                        jQuery(".remove-button", this).show();
                    }
            ).mouseout(function () {
                        jQuery(".remove-button", this).hide();
                    });

            jQuery(".profileCertificationContainer").each(function () {
                var jthis = jQuery(this);
                var certificationId = parseInt(jthis.attr("certificationId"));
                var leaderId = parseInt(jthis.attr("leaderId"));

                if (certificationId > 0) {
                    jQuery.get("/scoutinghub/certificationClass/findByCertification", {certificationId: certificationId, leaderId: leaderId},
                            function (data) {
                                if (data) {
                                    jthis.find(".upcomingTrainings").append("<div class='currentTraining ui-corner-all'>" + data + "</div>");

                                }
                            });
                }
            });

        });
    </r:script>

</head>

<body>

<s:content med="true">

%{--No equivalent of jsp:attribute in jsp, so there's no way to do it later --}%
<g:set var="menu" scope="request">
    <li><g:link action="foo">Edit My Profile</g:link></li>
</g:set>

<g:if test="${duplicates?.size()}">
    <s:browser>
        <s:section>
            <s:msg type="warning">
                <div class="msg1">
                    <g:message code="leader.profile.duplicate"/>
                </div>

                <div class="msg2">
                    <g:message code="leader.profile.duplicate2"/>
                    <b>
                        <g:message code="leader.profile.duplicate_verify"/>
                    </b>
                </div>
                <f:leaderList leaders="${duplicates}">
                    <div><a href="javascript:openMergeLeaderDialog(${request.leaderInList.id},${leader.id})">Definitely a Match</a>
                    </div>

                    <div><a href="javascript:ignoreDuplicate(${leader.id}, ${request.leaderInList.id})">Not a Match</a>
                    </div>
                </f:leaderList>

            </s:msg>
        </s:section>
    </s:browser>
</g:if>
<g:elseif test="${leader.certifications?.size() == 0 && leader.myScoutingIds?.size() == 0}">
    <s:section row="true">
        <s:msg type="warning">
            <h2><g:message code="leader.profile.nolink"/></h2>

            <div class="msg2">
                <g:message code="leader.profile.nolinkdescription"/>
                <ul class="list">
                    <li>
                        <strong>
                            <g:link title="${message(code: 'leader.profile.addScoutingId')}" lbwidth="500"
                                    class="lightbox"
                                    controller="myScoutingId" action="create" params="['leader.id': leader.id]">
                                <g:message code="leader.profile.nolinkdescription_item1"/>
                            </g:link>
                        </strong>
                    </li>
                    <li>
                        <strong><g:link controller="leader" action="view" id="${leader.id}"
                                        params="[edit: true]"><g:message
                                    code="leader.profile.nolinkdescription_item2"/></g:link></strong>
                    </li>
                    <li>
                        <g:message code="leader.profile.nolinkdescription_item3"/>
                    </li>
                </ul>
            </div>

        </s:msg>
    </s:section>
</g:elseif>

<g:form action="saveProfile">
    <s:rowFluid>
        <s:column>
            <s:section>
                <s:sectionHeader icon="user" code="${leader.toString()}">
                    <s:ctxmenu>
                        <g:ctxmenuItem img="edit-icon" controller="leader" action="view" id="${leader.id}"
                                       params="[edit: true]" iconType="edit" code="leader.profile.edit">
                        %{--<s:linker style="white-space:nowrap;" action="view" id="${leader.id}" params="[edit:true]">--}%
                        %{--<g:inlineIcon class="edit-icon"/>--}%
                        %{--<g:ctxmenuLabel>--}%
                        %{--<g:message code="leader.profile.edit"/>--}%
                        %{--</g:ctxmenuLabel>--}%
                        %{--</s:linker>--}%
                        </g:ctxmenuItem>
                        <g:ctxmenuItem>
                            <s:linker img="add-icon" title="${message(code: 'leader.profile.addScoutingId')}"
                                      lbwidth="500"
                                      class="lightbox"
                                      controller="myScoutingId" action="create" params="['leader.id': leader.id]">
                                <g:inlineIcon class="add-icon"/>
                                <g:ctxmenuLabel><g:message code="leader.profile.addAnother"/></g:ctxmenuLabel>
                            </s:linker>
                        </g:ctxmenuItem>
                    </s:ctxmenu>
                </s:sectionHeader>


                <g:set var="menu" value="" scope="request"/>
                <s:propertyList class="form-horizontal edit-profile">

                    <g:if test="${leader?.email}">
                        <s:property code="leader.email.label" text="true">
                            ${leader?.email ?: message(code: 'leader.email.noneFound')}
                        </s:property>
                    </g:if>

                    <g:if test="${leader?.phone}">
                        <s:property text="true"
                                    code="leader.phone.label">${f.formatPhone(phone: leader?.phone) ?: message(code: 'leader.phone.noneFound')}</s:property>
                    </g:if>

                    <g:if test="${leader?.address1}">
                        <s:property text="true"
                                    code="leader.address.label">${leader?.address1 ?: "Not set"}</s:property>
                    </g:if>


                    <s:property text="true" code="leader.profile.scoutingids">
                        <g:if test="${leader?.myScoutingIds?.size()}">
                            <g:each in="${leader.myScoutingIds}" var="myScoutingId">
                                <div class="myId">${myScoutingId.myScoutingIdentifier}</div>
                            </g:each>

                        </g:if>
                        <g:else>
                            <g:link title="${message(code: 'leader.profile.addScoutingId')}" lbwidth="500"
                                    class="lightbox"
                                    controller="myScoutingId" action="create" params="['leader.id': leader.id]">
                                <g:message code="leader.profile.noneYet"/>
                            </g:link>

                        </g:else>

                    </s:property>

                    <s:property text="true" code="leader.setupDate.label">
                        <g:if test="${leader?.setupDate}">
                            <g:formatDate date="${leader?.setupDate}" format="MM-dd-yyyy"/>
                            <g:if test="${leader?.username != leader?.email}">
                                <br/>
                                <g:message code="leader.profile.username"/>: ${leader?.username}
                            </g:if>
                        </g:if>
                        <g:elseif test="${leader.email != null}">
                            <g:link title="${message(code: 'leader.profile.invite')}" lbwidth="500" class="lightbox"
                                    controller="leader" action="invite" id="${leader.id}">
                                <g:message code="leader.profile.invite"/>
                            </g:link>
                        </g:elseif>
                        <g:else>
                            <g:message code="leader.profile.enterEmailToInvite"/>
                        </g:else>
                    </s:property>

                </s:propertyList>
            </s:section>

        </s:column>

        <s:column>
            <s:section>
                <s:sectionHeader icon="unit" code="leader.profile.groups">
                    <s:ctxmenu>
                        <g:ctxmenuItem>
                            <s:linker img="edit-icon" class="lightbox"
                                      title="${message(code: 'leader.profile.addToGroup')}"
                                      lbwidth="475"
                                      controller="leaderGroup" action="create" params="['leader.id': leader.id]">
                                <g:inlineIcon class="edit-icon"/>
                                <g:ctxmenuLabel>
                                    <g:message code="leader.profile.addAnotherUnit" args="[leader.firstName]"/>
                                </g:ctxmenuLabel>
                            </s:linker>
                        </g:ctxmenuItem>

                        <g:ctxmenuItem>
                            <s:linker img="add-icon" title="${message(code: 'leaderGroup.permissions', args: [leader])}"
                                      controller="leaderGroup" action="permissions" id="${leader.id}" class="lightbox">
                                <g:inlineIcon class="add-icon"/>
                                <g:ctxmenuLabel>
                                    <g:message code="leader.profile.editPermission" args="[leader?.firstName]"/>
                                </g:ctxmenuLabel>
                            </s:linker>
                        </g:ctxmenuItem>

                    </s:ctxmenu>
                </s:sectionHeader>

                <s:propertyList class="thumbnails">
                    <g:if test="${leader?.groups?.size()}">
                        <g:each in="${leader.groups}" var="group" status="i">
                            <s:leaderUnit leaderGroup="${group}" code="${group?.leaderPosition}.label"
                                          class="${currClass} span6">
                                ${group?.scoutGroup}

                            %{--<p:canAdministerGroup leader="${leader}"--}%
                            %{--scoutGroup="${group?.scoutGroup}">(admin)</p:canAdministerGroup>--}%
                            %{--<p:canAdministerGroup scoutGroup="${group?.scoutGroup}">--}%
                            %{--<div><g:link class="manage-this-unit" controller="scoutGroup" action="show"--}%
                            %{--id="${group?.scoutGroup?.id}">--}%
                            %{--<g:message code="scoutGroup.manage"--}%
                            %{--args="[group?.scoutGroup?.groupType?.name()?.humanize()]"/>--}%
                            %{--</g:link></div>--}%
                            %{--</p:canAdministerGroup>--}%
                            </s:leaderUnit>
                            <g:set var="grpI" value="${i}" scope="request"/>
                        </g:each>
                        <g:if test="${request.grpI % 2 == 0}">
                            <s:leaderUnit class="${currClass}"/>
                        </g:if>
                    </g:if>
                    <g:else>
                        <s:property>
                            <g:link class="lightbox" title="${message(code: 'leader.profile.addToGroup')}" lbwidth="600"
                                    controller="leaderGroup" action="create" params="['leader.id': leader.id]">
                                <g:message code="leader.profile.noneYet" args="[leader.firstName]"/>
                            </g:link>
                        </s:property>
                    </g:else>
                </s:propertyList>
            </s:section>
        </s:column>

    </s:rowFluid>

    <g:if test="${meritBadgeCounselors?.size() > 0}">
        <s:rowFluid>
            <s:column span="12">
                <g:each in="${meritBadgeCounselors}" var="meritBadgeCounselor">

                    <s:section span="6" class="floatSection myprofile">
                        <s:sectionHeader icon="user" code="leader.profile.meritBadgeCounselor">
                            <s:ctxmenu>

                                <g:ctxmenuItem>
                                    <s:linker controller="meritBadgeCounselor" action="edit"
                                              params="['id': meritBadgeCounselor.id]">
                                        <g:inlineIcon class="edit-icon"/>
                                        <g:ctxmenuLabel><g:message
                                                code="leader.profile.editMeritBadgeCounselor"/></g:ctxmenuLabel>
                                    </s:linker>
                                </g:ctxmenuItem>

                            </s:ctxmenu>
                        </s:sectionHeader>

                        <s:propertyList>
                            <s:property class="full-width-property">
                                <g:each in="${meritBadgeCounselor.sortedMeritBadges}" var="meritBadge">
                                    <div class="merit-badge-checkbox">
                                        ${meritBadge.name}
                                    </div>
                                </g:each>
                            </s:property>
                        </s:propertyList>
                    </s:section>

                </g:each>
            </s:column>
        </s:rowFluid>
    </g:if>
</g:form>


<s:rowFluid>

    <g:if test="${certificationInfo?.size() > 0}">
        <s:column span="6">
            <s:section span="6" class="floatSection">
                <s:sectionHeader code="leader.profile.mytraining" icon="training"/>

                <g:if test="${!certificationInfo}">
                    <s:msg type="warning" code="leader.profile.notInUnit"/>
                </g:if>


                <g:each in="${certificationInfo}" var="certification">
                    <s:leaderTraining certificationInfo="${certification}"/>
                </g:each>

            </s:section>
        </s:column>
    </g:if>

    <s:column>
        <s:section span="6" class="floatSection">
            <s:sectionHeader code="leader.profile.myothertraining" icon="training">
                <s:ctxmenu>
                    <g:ctxmenuItem iconType="add">
                        <s:linker img="edit-icon" class="lightbox"
                                  title="${message(code: 'leader.profile.addAdditionalLinkCtx')}"
                                  lbwidth="600"
                                  controller="leaderCertification" action="create" params="['leader.id': leader.id]">
                            <g:inlineIcon class="add-icon"/>
                            <g:ctxmenuLabel>
                                <g:message code="leader.profile.addAdditionalLinkCtx"/>
                            </g:ctxmenuLabel>
                        </s:linker>
                    </g:ctxmenuItem>

                </s:ctxmenu>
            </s:sectionHeader>

            <g:if test="${!extraCertificationInfo}">
                <s:text>
                    <g:message code="leader.profile.addAdditional"/>
                    <s:linker img="edit-icon" class="lightbox"
                              title="${message(code: 'leader.profile.addAdditionalLink')}"
                              lbwidth="600"
                              controller="leaderCertification" action="create" params="['leader.id': leader.id]">
                        <g:message code="leader.profile.addAdditionalLink"/>
                    </s:linker>
                </s:text>
            </g:if>

            <g:set scope="request" var="certIndex" value="${0}"/>
            <g:each in="${extraCertificationInfo}" var="certification">
                <s:leaderTraining certificationInfo="${certification}"/>
            </g:each>

        </s:section>
    </s:column>
</s:rowFluid>

</s:content>

</body>
</html>