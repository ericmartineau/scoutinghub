<g:form action="savePermissions" id="${leader.id}">

    <h1><g:message code="leaderGroup.permissions" args="[leader]"/></h1>
    <p:allGroupPermissions leader="${leader}"/>

    <hr>
    <g:submitButton name="submit" class="ui-button" value="${message(code: 'Save')}"/>
</g:form>
