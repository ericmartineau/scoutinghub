<html>
<head>
    <title><g:message code="menu.training.report"/></title>
    <meta name="layout" content="${layoutName}"/>
    <script>
        jQuery(document).ready(function() {
//            jQuery("#filterName").selectBox();

//            jQuery.getJSON("/scoutinghub/training/getFilters", {}, function(json) {
//                jQuery("#filterName").selectBox("options", json);
            %{--jQuery("#filterName").selectBox("value", "${session.filterName}");--}%
//            });

        });
    </script>
</head>

<body>
<s:content>

    <g:form action="trainingReport" name="filterForm">
        <s:section>
            <s:sectionHeader code="training.report.title" icon="training-icon"/>
            <s:propertyList class="training-filter alternate-color">
                <s:selecter code="training.report.selectFilter" id="filterName" name="filterName" onChange="document.filterForm.submit()"
                            value="${session.filterName}">
                    <g:each in="${allFilters}" var="categoryEntry">
                        <optgroup label="${message(code: categoryEntry.key + ".label")}">
                            <g:each in="${categoryEntry.value}" var="optionEntry">
                                <g:selectOption value="${optionEntry.key}"><g:message code="${optionEntry.key}.label"/></g:selectOption>
                            </g:each>
                        </optgroup>
                    </g:each>
                </s:selecter>
            </s:propertyList>
        </s:section>
        <g:hiddenField name="id" value="${reportGroup?.id}"/>
    </g:form>


    <s:section class="training-filters">
        <g:if test="${reportGroup}">
            <g:set var="headerCode" value="${reportGroup?.toString()}" />
        </g:if>
        <g:else>
            <g:set var="headerCode" value="trainingReport.defaultHeader" />
        </g:else>
        <s:sectionHeader code="${headerCode}" icon="units-icon">
            <g:if test="${reportGroup?.parent}">
                <p:canAdministerGroup scoutGroup="${reportGroup.parent}">
                    <s:ctxmenu>
                        <s:ctxmenuItem code="${reportGroup?.parent?.toString()}" controller="training" action="trainingReport"
                                       id="${reportGroup.parent?.id}"/>
                    </s:ctxmenu>

                </p:canAdministerGroup>
            </g:if>
        </s:sectionHeader>

        <s:div class="training-result-table">
            <s:browser>

                <s:div class="tr">
                    <div class='td ui-state-active training-header'>Leader/Group</div>

                    <div class='td ui-state-active training-header'>Type</div>

                    <div class='td ui-state-active training-header'>Percent Trained</div>
                </s:div>

            </s:browser>


            <g:if test="${filteredLeaderList?.size() > 0}">
                <g:each in="${filteredLeaderList}" var="leaderGroup">
                    <s:leaderTrainingRollup leaderGroup="${leaderGroup}"/>
                </g:each>
            %{--<g:if test="${reports.size() > 0}">--}%
            %{--<tr>--}%
            %{--<td colspan="2">--}%
            %{--<hr/>--}%
            %{--</td>--}%
            %{--</tr>--}%
            %{--</g:if>--}%
            </g:if>

            <g:each in="${reports}" var="certificationReport">
                <g:if test="${certificationReport.count > 0}">
                    <s:trainingRollup controller="training" action="trainingReport" id="${certificationReport.scoutGroup.id}"
                                      message="${certificationReport.scoutGroup}" pct="${certificationReport.pctTrained}"
                                      typeCode="${certificationReport.scoutGroup.unitType ?: certificationReport.scoutGroup.groupType}.label"/>
                </g:if>

            </g:each>
        </s:div>

    </s:section>

</s:content>
</body>
</html>