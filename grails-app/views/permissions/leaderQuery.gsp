<h2>To merge duplicate records, just drag one of the records onto the other.</h2>

<div class="leaderResultContainer">
    <g:if test="${results?.size() < 1}">
        <tr>
            <td colspan="10"><h2><g:message code="permissions.index.noRecordsFound"/></h2></td>
        </tr>
    </g:if>
    <g:each in="${results}" var="leader">
        <div class="shadow leaderResult validation ui-corner-all" leaderid="${leader.id}">
            <div class="ui-widget-header ui-state-active ui-corner-tr ui-corner-tl leaderName">
                ${leader?.firstName} ${leader?.lastName}
                <span style="float:right">
                    <g:link class="leaderProfileLink" controller="leader" action="view" id="${leader.id}"><g:message code="leader.viewProfile"/></g:link>
                </span>
            </div>
            <s:propertyList>
                <s:property code="leader.email.label">${leader?.email ?: message(code: 'leader.email.noneFound')}</s:property>
                <s:property code="leader.phone.label">${leader?.phone ?: message(code: 'leader.phone.noneFound')}</s:property>

                <g:each in="${leader.groups}" var="group">
                    <s:property code="${group?.position}.label">
                        ${group?.scoutGroup}
                        <g:if test="${group?.admin}">(admin)</g:if>
                    </s:property>
                </g:each>
            </s:propertyList>


            %{--<tr>--}%
            %{--<td class="tabular"><g:link controller="leader" action="view" id="${leader?.id}"><g:message code="View"/></g:link></td>--}%
            %{--<td class="tabular">${leader?.firstName}</td>--}%
            %{--<td class="tabular">${leader?.lastName}</td>--}%
            %{--<td class="tabular">${leader?.email ?: "None"}</td>--}%
            %{--<td class="tabular">--}%
            %{--<g:if test="${leader?.groups}">--}%
            %{--${leader?.groups?.iterator()?.next()?.scoutGroup?.toString() ?: "None"}--}%
            %{--<g:join in="${leader?.groups?.collect {it.scoutGroup.groupLabel ?: it.scoutGroup.groupIdentifier}}" delimiter=","/>--}%

            %{--</g:if>--}%
            %{--<g:else>--}%
            %{--None--}%
            %{--</g:else>--}%
            %{--</td>--}%
            %{--<td class="tabular">--}%
            %{--${leader?.groups?.iterator()?.next()?.leaderPosition?.name()?.humanize() ?: "None"}--}%

            %{--</td>--}%
            %{--<g:each in="${roles}" var="role">--}%
            %{--<td align="center">--}%
            %{--<g:checkBox onclick="togglePermission(this, ${leader?.id}, ${role?.id})" checked="${leader.hasAuthority(role)}" />--}%
            %{--</td>--}%
            %{--</g:each>--}%

            %{--</tr>--}%
        </div>
    </g:each>
</div>
