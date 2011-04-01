<form>
    <div class="fldContainer">
        %{--<g:message code="leaderCertification.certification.label"/>--}%
        <div class="profileData">${certificationName}</div>
    </div>
    <div class="fldContainer">
        <g:message code="leaderCertification.dateEarned.label"/>
        <div>
            <g:textField class="loginForm ui-corner-all datePicker" name="trainingDate" size="10"/>
            (mm/dd/yyyy)
        </div>
    </div>
</form>