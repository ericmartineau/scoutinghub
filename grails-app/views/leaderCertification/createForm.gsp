    <g:form class="dialog-form" reload="true" controller="leaderCertification" action="saveCertification" style="display: inline;">
        <g:hiddenField name="leaderId" value="${leader.id}"/>
        <g:hiddenField name="certificationId" value="${certification.id}"/>

            <s:sectionHeader code="${certification.name}" />
            <s:text>
                <ul class="list">
                    <li><g:message code="leaderCertification.create.messageA"/></li>
                    <li><g:message code="leaderCertification.create.messageB"/></li>
                    <li><g:message code="leaderCertification.create.scoutIsTrustworthy"/></li>
                </ul>
            </s:text>

            <s:textField type="text" code="leaderCertification.dateEarned.label" placeholder="MM-dd-yyyy" name="dateEarned" size="10" class=""/>

        <s:div class="buttons" />

        <s:submit name="save" value="Save"/>
    </g:form>


    <g:form class="dialog-form" reload="true" controller="leaderCertification" action="deleteCertification" style="display: inline;">
                    <g:hiddenField name="leaderId" value="${leader.id}"/>
                    <g:hiddenField name="certificationId" value="${certification.id}"/>
                    <g:actionSubmit class="btn delete" action="deleteCertification" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </g:form>
