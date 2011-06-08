<div class="leaderResultContainer">
    <g:if test="${results?.size() < 1}">
        <tr>
            <td colspan="10"><h2><g:message code="permissions.index.noRecordsFound"/></h2></td>
        </tr>
    </g:if>
    <g:each in="${results}" var="scoutGroup">
        <div class="shadow unitResult validation ui-corner-all" scoutgroupid="${scoutGroup?.id}">
            <div class="ui-widget-header ui-state-active ui-corner-tr ui-corner-tl unitName">
                ${scoutGroup}
                <span style="float:right">
                    <g:link class="leaderProfileLink" controller="scoutGroup" action="show" id="${scoutGroup?.id}">
                        <g:message code="Show Unit"/></g:link>
                </span>
            </div>
            <s:propertyList>

            </s:propertyList>

        </div>
    </g:each>
</div>
