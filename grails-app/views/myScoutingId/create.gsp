<g:form class="dialog-form" name="myScoutingIdCreate" action="save">
    <div class="dialog">
        <s:bigTextField code="${message(code:'myScoutingId.myScoutingIdentifier.label')}" name="myScoutingIdentifier" placeholder="${message(code:'myScoutingId.myScoutingIdentifier.label')}"/>
        <g:hiddenField name="leader.id" value="${myScoutingIdInstance?.leader?.id}"/>
    </div>
    <div class="buttons">
        <g:submitButton name="save" value="Save" />
    </div>
</g:form>
