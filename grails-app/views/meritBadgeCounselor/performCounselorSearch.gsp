<div class="leaderResultContainer">
    <g:if test="${results?.size() < 1}">
        <tr>
            <td colspan="10"><h2><g:message code="permissions.index.noRecordsFound"/></h2></td>
        </tr>
    </g:if>
    <g:each in="${results}" var="leader">

        <g:set var="hasPermissionClass" value=""/>
        <g:set var="hasPermissionInnerClass" value="no-permission"/>

        <p:canAdministerLeader leader="${leader}">
            <g:set var="hasPermissionClass">has-permission</g:set>
            <g:set var="hasPermissionInnerClass">ui-state-active</g:set>
        </p:canAdministerLeader>

        <div class="leaderResult shadow validation ui-corner-all ${hasPermissionClass}" leaderid="${leader.id}">
            <div class="ui-widget-header ${hasPermissionInnerClass} ui-corner-tr ui-corner-tl leaderName">
                ${leader}
                <p:canAdministerLeader leader="${leader}">
                    <span style="float:right">
                        <g:link class="leaderProfileLink" controller="leader" action="view" id="${leader.id}"><g:message
                                code="leader.viewProfile"/></g:link>
                    </span>
                </p:canAdministerLeader>
            </div>
            <s:propertyList>
                <s:property
                        code="leader.email.label">${leader?.email ?: message(code: 'leader.email.noneFound')}</s:property>
                <s:property
                        code="leader.phone.label">${f.formatPhone(phone: leader?.phone) ?: message(code: 'leader.phone.noneFound')}</s:property>
                <s:property
                        code="leader.address.label">${leader?.address1 ?: message(code: 'leader.address.noneFound')}</s:property>

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