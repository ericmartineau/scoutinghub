<div class="mergePage">
    <g:msgbox type="warning" code="leader.merge.areYouSure" code2="leader.merge.areYouSureMessage"/>
    <div class="mergeContainer">

        <div class="mergeRow">
            <div class="merge">
                <h1>${leaderA.firstName} ${leaderA.lastName}</h1>
                <div>${leaderA.email ?: "No email"}</div>
                <div>${leaderA.phone ?: "No phone"}</div>
            </div>
            <div class="merge">
                <h1>${leaderB.firstName} ${leaderB.lastName}</h1>
                <div>${leaderB.email ?: "No email"}</div>
                <div>${leaderB.phone ?: "No phone"}</div>
            </div>

        </div>
    </div>
    <div style="padding:15px">
        <g:link class="ui-button ui-button-style" controller="leader" action="doMerge" params="[leaderA:leaderA.id, leaderB:leaderB.id]">
            <g:message code="leader.merge.perform"/>
        </g:link>
    </div>
</div>