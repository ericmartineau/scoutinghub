<html>
<head>
    <title><g:message code="menu.training.report"/></title>
    <meta name="layout" content="${layoutName}"/>
    <script>
        jQuery(document).ready(function() {
            jQuery.getJSON("/scoutcert/training/getFilters", {}, function(json) {
                jQuery("#filterName").selectBox();
                jQuery("#filterName").selectBox("options", json);
                jQuery("#filterName").selectBox("value", "${session.filterName}");
            });

        });
    </script>
</head>

<body>
<s:content>
    <s:section>
        <s:msg type="info">

            <g:form action="trainingReport" name="filterForm">
                <div class="msg1"><g:message code="training.report.selectFilter"/></div>
                <g:select from="[]" id="filterName" name="filterName" onChange="document.filterForm.submit()" value="${session.filterName}"/>
                <g:hiddenField name="id" value="${reportGroup?.id}"/>
            </g:form>

        </s:msg>

    </s:section>
    <table width="500" id="trainingTable">
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
            %{--<tr>--}%
            %{--<td colspan="2">--}%
            %{--<g:header>Leaders</g:header>--}%
            %{--</td>--}%
            %{--</tr>--}%
                <g:each in="${reportGroup?.leaderGroups}" var="leaderGroup">
                    <tr>
                        <td class="trainingReportUnit"><g:link controller="leader" action="view" id="${leaderGroup.leader.id}">${leaderGroup.leader.firstName} ${leaderGroup.leader.lastName}</g:link></td>
                        <td><div class="progress" value="${(int) leaderGroup.pctTrained}"></div></td>

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
            <g:if test="${certificationReport.count > 0}">
                <tr>
                    <td class="trainingReportUnit">
                        <div class="msg1">
                            <g:link action="trainingReport" id="${certificationReport.scoutGroup.id}">
                                ${certificationReport.scoutGroup}
                            </g:link>
                        </div>
                        %{--<div style="font-size:small">--}%
                            %{--<g:if test="${certificationReport.count > 1}">--}%
                                %{--${certificationReport.count} leaders--}%
                            %{--</g:if>--}%
                            %{--<g:else>--}%
                                %{--${certificationReport.count} leader--}%
                            %{--</g:else>--}%
                        %{--</div>--}%

                    </td>
                    %{--<td>${(int)certificationReport.pctTrained}%</td>--}%
                    <td><div class="progress trainingReportBar" value="${certificationReport.pctTrained}"></div></td>

                </tr>
            </g:if>

        </g:each>
    </table>
</s:content>
</body>
</html>