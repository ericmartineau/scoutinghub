<tr>
    <td class="level${index}">${leaderGroup.leader}</td>
    <td><g:message code="${leaderGroup.leaderPosition}.label" /></td>
    <td><g:formatNumber number="${leaderGroup.pctTrained}"/>%</td>
    <td><f:missingTrainingCodes leaderGroup="${leaderGroup}" /></td>
    <td>${leaderGroup.leader.setupDate != null ? "true" : "false"}</td>
</tr>