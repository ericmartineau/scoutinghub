<form>
    <div class="fldContainer" style="text-align:left">
        %{--<g:message code="leaderCertification.certification.label"/>--}%
        <div class="profileData">${certificationName}</div>
        <div style="margin-top:10px">
            <g:message code="leaderCertification.create.message"/>
            <ul>
                <li><g:message code="leaderCertification.create.message1"/></li>
                <li><g:message code="leaderCertification.create.message2"/></li>
            </ul>
        </div>
    </div>
    <div class="fldContainer" style="text-align:left">
        <g:message code="leaderCertification.dateEarned.label"/>
        <div>
            <g:textField style="font-size:18px" class="ui-corner-all datePicker" name="trainingDate" size="10"/>
        </div>
    </div>
</form>