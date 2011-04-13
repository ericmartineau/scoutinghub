<html>
<head>
    <title><g:message code="menu.training.report"/></title>
    <meta name="layout" content="${layoutName}"/>

</head>

<body>
<s:content>
    <table width="500">
        <g:if test="${reportGroup}">
            <tr>
                <td colspan="2">

                    <div style="overflow:hidden;">
                        <h1 style="margin-top:8px; margin-bottom:8px; vertical-align:middle;float:left">${reportGroup}</h1>
                        <div style="float:right; margin-top:8px; margin-bottom:8px;">
                            <g:if test="${reportGroup.parent}">
                                <p:canAdministerGroup scoutGroup="${reportGroup.parent}">
                                    <g:link class="ui-button" action="trainingReport" id="${reportGroup.parent?.id}">Go Back</g:link>
                                </p:canAdministerGroup>
                            </g:if>
                        </div>
                    </div>

                </td>
            </tr>
            <g:if test="${reportGroup?.leaderGroups?.size() > 0}">
                <tr>
                    <td colspan="2">
                        <g:header>Leaders</g:header>
                    </td>
                </tr>
                <g:each in="${reportGroup?.leaderGroups.collect{it.leader}}" var="leader">
                    <tr>
                        <td class="trainingReportUnit"><g:link controller="leader" action="view" id="${leader.id}">${leader.firstName} ${leader.lastName}</g:link></td>
                        <td><div class="progress" value="${(int) leader.pctTrained}"></div></td>
                    </tr>
                </g:each>
                <g:if test="${reports.size() > 0}">
                    <tr>
                        <td colspan="2">
                            <hr/>
                        </td>
                    </tr>
                </g:if>
            </g:if>

        </g:if>



        <g:each in="${reports}" var="certificationReport">
            <tr>
                <td class="trainingReportUnit"><g:link action="trainingReport" id="${certificationReport.scoutGroup.id}">${certificationReport.scoutGroup}</g:link></td>
                %{--<td>${(int)certificationReport.pctTrained}%</td>--}%
                <td><div class="progress trainingReportBar" value="${certificationReport.pctTrained}"></div></td>

            </tr>

        </g:each>
    </table>
</s:content>
</body>
</html>