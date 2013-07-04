<%@ page import="scoutinghub.LeaderPositionType" %>
<g:form autocomplete="off" class="dialog-form" action="save">
    <s:section>
        <s:bigSelecter class="selecter" id="leaderPosition" name="leaderPosition"
                       value="${leaderGroupInstance?.leaderPosition?.name()}"
                       code="${message(code: 'label.unitPosition')}"
                       placeholder="${message(code: 'label.unitPosition')}">
            <s:unitSelectorOptions/>
        </s:bigSelecter>

        <s:bigTextField size="38" name="scoutGroupName"
                        otherAttrs="[idField: 'scoutGroupId', positionField: 'leaderPosition']" class="unitSelector"
                        value="${leaderGroupInstance?.scoutGroup}"
                        code="${message(code: 'label.unitNumber')}"
                        placeholder="${message(code: 'label.unitNumber')}"/>



        <g:hiddenField name="leader.id" value="${leaderGroupInstance?.leader?.id}"/>

        <g:hiddenField id="scoutGroupId" name="scoutGroup.id" value="${leaderGroupInstance?.scoutGroup?.id}"/>


        <s:div class="buttons">
            <s:submit name="save" value="Save"/>
        </s:div>
    </s:section>
</g:form>

