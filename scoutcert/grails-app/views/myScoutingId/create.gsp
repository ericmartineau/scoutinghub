<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<g:if test="${flash.error}">
    <g:msgbox type="error" code="${flash.error}" code2="${flash.error2}" />
</g:if>

<g:if test="${flash.info}">
    <g:msgbox type="info" code="${flash.info}" code2="${flash.info2}" />
</g:if>


<g:hasErrors bean="${myScoutingIdInstance}">
    <g:msgbox type="error">
        <g:renderErrors bean="${myScoutingIdInstance}" as="list"/>
    </g:msgbox>
</g:hasErrors>
<g:form name="myScoutingIdCreate" action="save">
    <div class="dialog">
        <s:bigTextField code="${message(code:'myScoutingId.myScoutingIdentifier.label')}" name="myScoutingIdentifier" placeholder="${message(code:'myScoutingId.myScoutingIdentifier.label')}"/>
        <g:hiddenField name="leader.id" value="${myScoutingIdInstance?.leader?.id}"/>
    </div>
    <g:submitToRemote onComplete="decorate()" class="ui-button" update="dialog" url="[action:'save']" name="save" value="Save" />
</g:form>
