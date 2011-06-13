<%@ page import="scoutinghub.Role" %>

<g:form action="savePermissions" id="${leader.id}">
    <s:content><s:section>

        <sec:ifAllGranted roles="ROLE_ADMIN">
            <p:singlePermission class="admin-permission" label="Global Administrator" id="0"
                                checked="${leader.hasRole('ROLE_ADMIN')}"/>
        %{--<p:singlePermission class="admin-permission" label="Global Administrator" id="admin" checked="${true}"/>--}%
            <g:set var="hadPermissions" value="${true}" scope="request" />
        </sec:ifAllGranted>

        <p:allGroupPermissions leader="${leader}"/>

        <g:if test="${request.hadPermissions}">
            <s:div class="buttons">
                <s:submit name="submit" class="ui-button" value="${message(code: 'Save')}"/>
            </s:div>
        </g:if>
        <g:else>
            <s:text><g:message code="permissions.nothingToShow"/></s:text>
        </g:else>

    </s:section></s:content>
</g:form>
