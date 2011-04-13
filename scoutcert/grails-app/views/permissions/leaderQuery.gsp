<h2>Results</h2>
<table cellpadding="0" cellspacing="1" class="tabular">
    <tr>
        <g:tableHeader code="View"/>
        <g:tableHeader code="leader.firstName.label"/>
        <g:tableHeader code="leader.lastName.label"/>
        <g:tableHeader code="leader.email.label"/>
        <g:tableHeader code="label.unitNumber"/>
        <g:tableHeader code="label.position"/>
        %{--<g:each in="${roles}" var="role">--}%
            %{--<g:tableHeader>${role.authority}</g:tableHeader>--}%
        %{--</g:each>--}%

    </tr>

    <g:if test="${results?.size() < 1}">
        <tr>
            <td colspan="10"><h2><g:message code="permissions.index.noRecordsFound"/></h2></td>
        </tr>
    </g:if>
    <g:each in="${results}" var="leader">
        <tr>
            <td class="tabular"><g:link controller="leader" action="view" id="${leader?.id}"><g:message code="View" /></g:link></td>
            <td class="tabular">${leader?.firstName}</td>
            <td class="tabular">${leader?.lastName}</td>
            <td class="tabular">${leader?.email}</td>
            <td class="tabular">
                <g:if test="${leader?.groups}">

                    <g:join in="${leader?.groups?.collect {it.scoutGroup.groupLabel ?: it.scoutGroup.groupIdentifier}}" delimiter=","/>
                </g:if>
                <g:else>
                    None
                </g:else>
            </td>
            <td class="tabular">
                <g:join in="${leader?.groups?.collect {it.position?.name()?.humanize()}}" />
            </td>
            %{--<g:each in="${roles}" var="role">--}%
                %{--<td align="center">--}%
                    %{--<g:checkBox onclick="togglePermission(this, ${leader?.id}, ${role?.id})" checked="${leader.hasAuthority(role)}" />--}%
                %{--</td>--}%
            %{--</g:each>--}%

        </tr>
    </g:each>
</table>