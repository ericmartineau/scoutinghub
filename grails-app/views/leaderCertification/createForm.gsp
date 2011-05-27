<g:form class="dialog-form" reload="true" controller="leaderCertification" action="saveCertification">
    <g:hiddenField name="leaderId" value="${leader.id}"/>
    <g:hiddenField name="certificationId" value="${certification.id}"/>

    <div class="fldContainer" style="text-align:left">
        %{--<g:message code="leaderCertification.certification.label"/>--}%
        <div class="headerText">${certificationName}</div>
        <div style="margin-top:10px">
            <ul class="list">
                <li><g:message code="leaderCertification.create.messageA"/></li>
                <li><g:message code="leaderCertification.create.messageB"/></li>
                <li><g:message code="leaderCertification.create.scoutIsTrustworthy"/></li>
            </ul>
        </div>
    </div>
    <s:property code="leaderCertification.dateEarned.label">
            <g:textField style="font-size:18px" class="ui-corner-all datePicker" name="dateEarned" size="10"/>
    </s:property>

    <div class="buttons">
        <g:submitButton name="save" value="Save" />
    </div>
</g:form>