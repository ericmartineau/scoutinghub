<div class="mergePage">
    <g:msgbox type="warning" code="leader.merge.areYouSure" code2="leader.merge.areYouSureMessage"/>
    <div class="mergeContainer">

        <div class="mergeRow">
            <div class="merge">
                <h1>${leaderA}</h1>
                <div>${leaderA.email ?: "No email"}</div>
                <div>${f.formatPhone(phone: leaderA.phone) ?: "No phone"}</div>
                <div>${leaderA.address1 ?: "No address"}</div>
                <g:each in="${leaderA.myScoutingIds}" var="scoutingId">
                    <div>${scoutingId.myScoutingIdentifier}</div>
                </g:each>
            </div>
            <div class="merge">
                <h1>${leaderB}</h1>
                <div>${leaderB.email ?: "No email"}</div>
                <div>${f.formatPhone(phone: leaderB.phone) ?: "No phone"}</div>
                <div>${leaderB.address1 ?: "No address"}</div>
                <g:each in="${leaderB.myScoutingIds}" var="scoutingId">
                    <div>${scoutingId.myScoutingIdentifier}</div>
                </g:each>

            </div>

        </div>
    </div>
    <div style="padding:15px">
        <g:link class="btn btn-primary" controller="leader" action="doMerge" params="[leaderA:leaderA.id, leaderB:leaderB.id]">
            <g:message code="leader.merge.perform"/>
        </g:link>
    </div>
</div>