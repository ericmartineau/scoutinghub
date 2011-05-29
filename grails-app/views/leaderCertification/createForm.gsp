<s:content>
    <g:form class="dialog-form" reload="true" controller="leaderCertification" action="saveCertification">
        <g:hiddenField name="leaderId" value="${leader.id}"/>
        <g:hiddenField name="certificationId" value="${certification.id}"/>

        <s:section>
            <s:sectionHeader code="${certification.name}" />
            <s:text>
                <ul class="list">
                    <li><g:message code="leaderCertification.create.messageA"/></li>
                    <li><g:message code="leaderCertification.create.messageB"/></li>
                    <li><g:message code="leaderCertification.create.scoutIsTrustworthy"/></li>
                </ul>
            </s:text>

            <s:textField code="leaderCertification.dateEarned.label" placeholder="${message(code:'leaderCertification.dateEarned.label')} (MM-dd-yyyy)" name="dateEarned" size="10" class="ui-corner-all datePicker"/>

            <s:div class="buttons">
                <s:submit name="save" value="Save"/>
            </s:div>
        </s:section>

    </g:form>
</s:content>