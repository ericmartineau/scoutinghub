<%@ page import="scoutinghub.Role" %>

<g:form action="savePermissions" id="${leader.id}">
        <sec:ifAllGranted roles="ROLE_ADMIN">
            <p:singlePermission class="admin-permission" label="Global Administrator" id="0"
                                checked="${leader.hasRole('ROLE_ADMIN')}"/>
        %{--<p:singlePermission class="admin-permission" label="Global Administrator" id="admin" checked="${true}"/>--}%
            <g:set var="hadPermissions" value="${true}" scope="request" />
        </sec:ifAllGranted>

        <p:allGroupPermissions leader="${leader}"/>

        <g:if test="${request.hadPermissions}">
            <br/>
            <s:div class="buttons">
                <s:submit name="submit" class="btn btn-primary" value="${message(code: 'Save')}"/>
            </s:div>
        </g:if>
        <g:else>
            <s:text><g:message code="permissions.nothingToShow"/></s:text>
        </g:else>
</g:form>
