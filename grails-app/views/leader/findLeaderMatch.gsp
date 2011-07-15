<s:msg type="info">
    <div class="msg1">The following records already exist and may be a match:</div>

    <div class="msg2">
        <g:each in="${leaders}" var="leader">
            <div>
                ${leader.firstName} ${leader.lastName} (${leader.email ?: "No email"})
                <a href="javascript:useLeader(${leader.id})">Use This Record</a>
            </div>
        </g:each>
    </div>

</s:msg>
