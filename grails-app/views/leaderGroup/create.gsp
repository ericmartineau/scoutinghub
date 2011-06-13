<%@ page import="scoutinghub.LeaderPositionType" %>
<g:form class="dialog-form" action="save">
    <s:content>
        <s:section>
            <s:textField size="38" name="scoutGroupName" otherAttrs="[idField:'scoutGroupId', positionField: 'leaderPosition']" class="unitSelector" value="${leaderGroupInstance?.scoutGroup}"
                         code="${message(code:'label.unitNumber')}"
                         placeholder="${message(code:'label.unitNumber')}"/>


            <g:hiddenField name="leader.id" value="${leaderGroupInstance?.leader?.id}"/>

            <g:hiddenField id="scoutGroupId" name="scoutGroup.id" value="${leaderGroupInstance?.scoutGroup?.id}"/>

            <s:selecter class="selecter" from="${LeaderPositionType.values()}" optionKey="${{it.name()}}" optionValue="${{it.name()?.humanize()}}"
                        id="leaderPosition" name="leaderPosition" value="${leaderGroupInstance?.leaderPosition?.name()}" code="${message(code:'label.unitPosition')}"
                        noSelection="${['':"Select Position"]}"
                        placeholder="${message(code:'label.unitPosition')}"/>

            <s:div class="buttons">
                <s:submit name="save" value="Save"/>
            </s:div>
        </s:section>
    </s:content>
</g:form>

