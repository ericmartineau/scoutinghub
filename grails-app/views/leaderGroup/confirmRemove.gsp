<g:form class="dialog-form" controller="leaderGroup" action="performRemove" id="${leaderGroup.id}">

    If you proceed:

    <ul class="list">
        <li>${leaderGroup.leader} will be removed from ${leaderGroup.scoutGroup}</li>
        <li>${leaderGroup.leader} will retain all training certifications for the <g:message
                code="${leaderGroup.leaderPosition.name()}.label"/> position</li>
        <li>${leaderGroup.leader} will not be removed from any other units</li>
        <li>${leaderGroup.leader} can be added to other units in the future</li>
    </ul>

    <div class="buttons">
        <g:submitButton name="proceed" value="Proceed"/>
    </div>

</g:form>

