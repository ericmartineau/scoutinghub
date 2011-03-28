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
                        <g:message code="leader.profile.units"/>:
                        <g:if test="${leader?.units?.size()}">
                            <g:each in="${leader.units}" var="unit">
                                <div class="profileData">${unit.unitLabel ?: unit.unitIdentifier}</div>
                            </g:each>
                        </g:if>
                        <g:else>
                            <div class="profileData">None (Yet)</div>
                        </g:else>
                    </td>
                </tr>

            </table>
        </td>
        <td valign="top" width="200">
            <g:header><g:message code="ui.tools"/></g:header>
            <div class="groupSection">
                <div class="toolboxItem"><a class="ui-button" href="javascript:attachScoutingId(${leader?.id})"><g:message code="leader.profile.linkScoutingId"/></a></div>
                <div class="toolboxItem"><a class="ui-button" href="javascript:attachUnit(${leader?.id})"><g:message code="leader.profile.addToUnit"/></a></div>
            </div>
        </td>
    </tr>

</table>

</body>
</html>