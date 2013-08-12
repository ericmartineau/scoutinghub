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
    <s:div class="hidden-phone">
        <s:section>
            <s:msg type="warning">
                <h3>
                    <g:message code="leader.profile.duplicate"/>
                </h3>


                <h4>
                    <g:message code="leader.profile.duplicate2"/>
                    <b>
                        <g:message code="leader.profile.duplicate_verify"/>
                    </b>
                </h4>

            </s:msg>

            <f:leaderList leaders="${duplicates}">
                <td><a href="javascript:openMergeLeaderDialog(${request.leaderInList.id},${leader.id})">Definitely a Match</a></td>
                <td><a href="javascript:ignoreDuplicate(${leader.id}, ${request.leaderInList.id})">Not a Match</a></td>
            </f:leaderList>

        </s:section>
    </s:div>
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
                                      lbwidth="500" menu="true"
                                      class="lightbox"
                                      controller="myScoutingId" action="create" params="['leader.id': leader.id]">
                                <g:inlineIcon class="add-icon"/>
                                <g:ctxmenuLabel><g:message code="leader.profile.addAnother"/></g:ctxmenuLabel>
                            </s:linker>
                        </g:ctxmenuItem>
                    </s:ctxmenu>
                </s:sectionHeader>


                <g:set var="menu" value="" scope="request"/>
                <s:propertyList class=" edit-profile">
                    <g:if test="${params.edit == null}">
                    %{--<g:if test="${leader?.email}">--}%
                        <s:property code="leader.email.label" text="true">
                            ${leader?.email ?: message(code: 'leader.email.noneFound')}
                        </s:property>
                    %{--</g:if>--}%

                    %{--<g:if test="${leader?.phone}">--}%
                        <s:property text="true"
                                    code="leader.phone.label">${f.formatPhone(phone: leader?.phone) ?: message(code: 'leader.phone.noneFound')}</s:property>
                    %{--</g:if>--}%

                    %{--<g:if test="${leader?.address1}">--}%
                        <s:property text="true"
                                    code="leader.address.label">${leader?.address1 ?: "Not set"}</s:property>
                    %{--</g:if>--}%


                        <s:property text="true" code="leader.profile.scoutingids">
                            <g:each in="${leader.myScoutingIds}" var="myScoutingId">
                                <div class="myId">${myScoutingId.myScoutingIdentifier}
                                    <g:link controller="myScoutingId" action="delete" id="${myScoutingId.id}"><i class="red icon-trash"></i></g:link>
                                </div>
                            </g:each>
                            <div class="myId">
                                <g:if test="${leader.myScoutingIds?.size() > 0}">
                                    <g:link title="${message(code: 'leader.profile.addScoutingId')}" lbwidth="500"
                                            class="lightbox"
                                            controller="myScoutingId" action="create" params="['leader.id': leader.id]">
                                        <g:message code="leader.profile.addBsaId"/>
                                    </g:link>
                                </g:if>
                                <g:else>
                                    <g:link title="${message(code: 'leader.profile.addScoutingId')}" lbwidth="500"
                                            class="lightbox"
                                            controller="myScoutingId" action="create" params="['leader.id': leader.id]">
                                        <g:message code="leader.profile.noneYet"/>
                                    </g:link>
                                </g:else>

                            </div>

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
                    </g:if>
                    <g:else>
                        <script type="text/javascript">

                            jQuery(document).ready(function () {
                                var $username = jQuery("[name='username']");
                                var $email = jQuery("[name='email']");
                                var oldValue = $email.val();
                                $email.change(function () {

                                    var newValue = $email.val();
                                    if ($username.val() == oldValue) {
                                        $username.val(newValue);
                                    }
                                    oldValue = $email.val();
                                });

                            });
                        </script>
                        <g:hasErrors bean="${flash.leaderError}">
                            <s:msg type="error">
                                <g:renderErrors bean="${flash.leaderError}"/>
                            </s:msg>
                        </g:hasErrors>

                        <g:hiddenField name="id" value="${leader.id}"/>


                        <s:textField name="firstName" code="leader.firstName.label" value="${leader?.firstName}"/>
                        <s:textField name="middleName" code="leader.middleName.label" value="${leader?.middleName}"/>
                        <s:textField name="lastName" code="leader.lastName.label" value="${leader?.lastName}"/>
                        <s:textField name="email" code="leader.email.label" value="${leader?.email}"/>
                        <s:textField name="phone" code="leader.phone.label"
                                     value="${f.formatPhone(phone: leader?.phone)}"/>
                        <s:textField name="username" code="leader.profile.username" value="${leader?.username}"/>
                        <s:textField type="password" name="password" code="leader.profile.password"/>

                        <s:textField name="address1" code="leader.address1.label" value="${leader?.address1}"/>
                        <s:textField name="address2" code="leader.address2.label" value="${leader?.address2}"/>
                        <s:textField name="city" code="leader.city.label" value="${leader?.city}"/>
                        <s:textField name="state" code="leader.state.label" value="${leader?.state}"/>
                        <s:textField name="postalCode" code="leader.postalCode.label" value="${leader?.postalCode}"/>

                        <s:submit name="submit" value="${message(code: 'Save')}"/>

                    </g:else>



                </s:propertyList>
            </s:section>

        </s:column>

        <s:column>
            <s:section>
                <s:sectionHeader icon="unit" code="leader.profile.groups">
                    <s:ctxmenu>
                        <g:ctxmenuItem>
                            <s:linker menu="true" img="edit-icon" class="lightbox"
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
                            <s:linker menu="true" img="add-icon" title="${message(code: 'leaderGroup.permissions', args: [leader])}"
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
                            <s:leaderUnit leaderGroup="${group}" code="${group?.leaderPosition}.label" class="span12">
                                ${group?.scoutGroup}
                            </s:leaderUnit>
                        </g:each>
                        %{--<div class="leader-unit add-unit span6">--}%
                            %{--<div class="leader-unit-position">--}%
                                %{--<h5>--}%
                                    %{--<g:link class="lightbox" title="${message(code: 'leader.profile.addToGroup')}"--}%
                                            %{--lbwidth="600"--}%
                                            %{--controller="leaderGroup" action="create" params="['leader.id': leader.id]">--}%
                                        %{--<g:message code="leader.profile.addPosition" args="[leader.firstName]"/>--}%
                                    %{--</g:link>--}%
                                %{--</h5>--}%
                            %{--</div>--}%
                        %{--</div>--}%
                    </g:if>

                </s:propertyList>
            </s:section>
        </s:column>

    </s:rowFluid>

    <g:if test="${meritBadgeCounselors?.size() > 0}">
        <s:row>
            <s:column span="12">
                <g:each in="${meritBadgeCounselors}" var="meritBadgeCounselor">

                    <s:section span="6" class="floatSection myprofile">
                        <s:sectionHeader icon="user" code="leader.profile.meritBadgeCounselor">
                            <s:ctxmenu>

                                <g:ctxmenuItem>
                                    <s:linker controller="meritBadgeCounselor" action="edit"
                                              params="['id': meritBadgeCounselor.id]" menu="true">
                                        <g:inlineIcon class="edit-icon"/>
                                        <g:ctxmenuLabel><g:message
                                                code="leader.profile.editMeritBadgeCounselor"/></g:ctxmenuLabel>
                                    </s:linker>
                                </g:ctxmenuItem>

                            </s:ctxmenu>
                        </s:sectionHeader>

                        <s:propertyList class="thumbnails merit-badge-list">
                            <g:each in="${meritBadgeCounselor.sortedMeritBadges}" var="meritBadge">
                                <div class="span3">
                                    <h5>${meritBadge.name}</h5>
                                </div>
                            </g:each>
                        </s:propertyList>
                    </s:section>

                </g:each>
            </s:column>
        </s:row>
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
                        <s:linker menu="true" img="edit-icon" class="lightbox"
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