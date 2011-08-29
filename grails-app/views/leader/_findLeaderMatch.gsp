<s:msg type="info">
    <div class="msg1">The following records already exist and may be a match:</div>

    <f:leaderList leaders="${leaders}">
        <div>
            <a href="javascript:useLeader(${request.leaderInList.id})">Use This Record</a>
        </div>
    </f:leaderList>
</s:msg>
