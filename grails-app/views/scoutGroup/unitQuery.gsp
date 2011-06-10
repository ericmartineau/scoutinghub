<div class="leaderResultContainer">
    <g:if test="${results?.size() < 1}">
        <tr>
            <td colspan="10"><h2><g:message code="permissions.index.noRecordsFound"/></h2></td>
        </tr>
    </g:if>
    <g:each in="${results}" var="scoutGroup">
        <div class="shadow unitResult validation ui-corner-all" scoutgroupid="${scoutGroup?.id}">
            <div class="ui-widget-header ui-state-active ui-corner-tr ui-corner-tl unitName">
                <g:link class="leaderProfileLink" controller="scoutGroup" action="show" id="${scoutGroup?.id}">${scoutGroup}</g:link>
                <span style="float:right">
                    <g:link class="leaderProfileLink" controller="scoutGroup" action="show" id="${scoutGroup?.id}">
                        <g:message code="Show Unit"/></g:link>
                </span>
            </div>

            <s:propertyList class="vertical-form">
                <g:each in="${scoutGroup.childGroups}" var="childGroup">
                    <s:property code="${childGroup.unitType ?: childGroup.groupType}.label">
                        <g:link class="leaderProfileLink" controller="scoutGroup" action="show" id="${childGroup?.id}">${childGroup}</g:link>
                    </s:property>
                </g:each>
            </s:propertyList>

            <s:propertyList class="vertical-form">
                <g:each in="${scoutGroup.leaderGroups}" var="leader">
                    <s:property code="${leader.leaderPosition}.label">
                        <g:link class="leaderProfileLink" controller="leader" action="view" id="${leader?.leader?.id}">${leader.leader}</g:link>
                    </s:property>
                </g:each>
            </s:propertyList>
        </div>
    </g:each>
</div>
