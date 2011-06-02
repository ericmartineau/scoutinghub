<%@ page import="scoutinghub.Role" %><g:form action="savePermissions" id="${leader.id}">

    <sec:ifAllGranted roles="ROLE_ADMIN">
        <p:singlePermission class="admin-permission" label="Global Administrator" id="admin"
                            checked="${leader.hasRole('ROLE_ADMIN')}"/>
        %{--<p:singlePermission class="admin-permission" label="Global Administrator" id="admin" checked="${true}"/>--}%
    </sec:ifAllGranted>

    <p:allGroupPermissions leader="${leader}"/>

    <g:if test="${request.hadPermissions}">
        <s:div class="buttons">
            <g:submitButton name="submit" class="ui-button" value="${message(code: 'Save')}"/>
        </s:div>
    </g:if>
    <g:else>
        <g:message code="permissions.nothingToShow"/>
    </g:else>
</g:form>
