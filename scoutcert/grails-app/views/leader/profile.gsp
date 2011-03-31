<html>
<head>
    <title><g:message code="menu.leader.profile"/></title>
    <meta name="layout" content="main"/>
    <script type="text/javascript">
        function attachScoutingId(leaderId) {
            alert("Not implemented")
        }

        function attachUnit(leaderId) {
            alert("Not implemented")
        }
    </script>

</head>

<body>

<table cellpadding="5">
    <tr>
        <td>
            <g:header><g:message code="leader.profile.myprofile"/></g:header>

            <table class="recordTable" cellspacing="0">
                <tr>
                    <td valign="top">
                        <g:message code="leader.firstName.label"/>:<br/>
                        <span class="profileData">${leader?.firstName}</span>
                    </td>
                    <td valign="top">
                        <g:message code="leader.lastName.label"/>:<br/>
                        <span class="profileData">${leader?.lastName}</span>
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <g:message code="leader.email.label"/>:<br/>
                        <span class="profileData">${leader?.email}</span>
                    </td>
                    <td valign="top">
                        <g:message code="leader.setupDate.label"/>:<br/>
                        <span class="profileData">
                            <g:if test="${leader?.setupDate}">
                                <g:formatDate date="${leader?.setupDate}" format="MM-dd-yyyy"/>
                            </g:if>
                            <g:else>
                                Not Set Up Yet
                            </g:else>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <g:message code="leader.profile.scoutingids"/>:<br/>
                        <g:if test="${leader?.myScoutingIds?.size()}">
                            <g:each in="${leader.myScoutingIds}" var="myScoutingId">
                                <div class="profileData">${myScoutingId.myScoutingIdentifier}</div>
                            </g:each>
                        </g:if>
                        <g:else>
                            <div class="profileData">None (Yet)</div>
                        </g:else>
                    </td>
                    <td valign="top">
                        <g:if test="${leader?.groups?.size()}">
                            <g:each in="${leader.groups}" var="group">
                                <div class="fldContainer">
                                    <g:message code="${group?.position}.label"/>
                                    <div class="profileData">
                                        ${group?.scoutGroup?.groupLabel ?: group?.scoutGroup?.groupIdentifier}<br/>
                                    </div>


                                </div>
                            </g:each>
                        </g:if>
                        <g:else>
                            <g:message code="leader.profile.groups"/>:
                            <div class="profileData">None (Yet)</div>
                        </g:else>
                    </td>
                </tr>
            </table>
            <g:header><g:message code="leader.profile.mytraining"/></g:header>

            <div class="groupSection">
                <g:if test="${leader.certifications?.size() == 0}">
                    <div class="profileData">No Training Completed</div>
                </g:if>
                <g:each in="${leader.certifications}" var="certification">
                    <div class="fldContainer">
                        Good until <g:formatDate date="${certification.goodUntilDate()}" format="MM-dd-yyyy"/><br/>
                        <div class="profileData">${certification.certification.name}</div>
                        ${certification.enteredType} by ${certification.enteredBy} <g:formatDate date="${certification.dateEntered}" format="MM-yyyy"/>

                    </div>
                </g:each>
            </div>

        </td>
        <td valign="top" width="200">
            <g:header><g:message code="ui.tools"/></g:header>
            <div class="groupSection">
                <div class="toolboxItem"><a href="javascript:attachScoutingId(${leader?.id})"><g:message code="leader.profile.linkScoutingId"/></a></div>
                <div class="toolboxItem"><a href="javascript:attachUnit(${leader?.id})"><g:message code="leader.profile.addToUnit"/></a></div>
            </div>
        </td>
    </tr>

</table>

</body>
</html>