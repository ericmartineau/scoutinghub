    <g:form class="dialog-form" reload="true" controller="leaderCertification" action="save">
        <g:hiddenField name="leader.id" value="${leaderCertificationInstance.leader.id}"/>

        <s:section>

            <s:selecter class="selecter"
                           id="unitPosition" name="unitPosition" code="${message(code:'label.unitPosition')}"
                           placeholder="${message(code:'label.unitPosition')}">
                <s:unitSelectorOptions/>
            </s:selecter>
            <s:selecter code="leaderCertification.certification.label" name="certification.id" id="certification"
                        value="${leaderCertificationInstance?.certification?.id}">
                <s:certificationOptions/>
            </s:selecter>

            <s:textField type="text" code="leaderCertification.dateEarned.label" placeholder="MM-dd-yyyy"
                         name="dateEarned" size="10" class="ui-corner-all datePicker"/>

            <s:div class="buttons">
                <s:submit name="save" value="Save"/>
            </s:div>
        </s:section>

    </g:form>
<script type="text/javascript">
    configureDrillDown("#unitPosition", "#certification", "/scoutinghub/leaderCertification/certificationOptions", true);
</script>



