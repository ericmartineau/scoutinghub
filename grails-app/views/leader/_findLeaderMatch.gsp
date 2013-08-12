<s:msg type="info">
    <div class="msg1">The following records already exist and may be a match:</div>

    <f:leaderList leaders="${leaders}">
        <td>
            <a href="javascript:useLeader(${request.leaderInList.id})">Use This Record</a>
        </td>
    </f:leaderList>
</s:msg>
