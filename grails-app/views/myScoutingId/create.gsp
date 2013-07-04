<g:renderErrors bean="${myScoutingIdInstance}"/>

<g:form class="dialog-form" name="myScoutingIdCreate" action="save">
    <s:section>
        <g:hiddenField name="leader.id" value="${myScoutingIdInstance?.leader?.id}"/>
        <s:textField placeholder="Enter here" code="${message(code: 'myScoutingId.myScoutingIdentifier.label')}"
                     name="myScoutingIdentifier"/>
        <s:div class="buttons">
            <s:submit name="save" value="Save"/>
        </s:div>
    </s:section>
</g:form>

