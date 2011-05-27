<%@ page import="scoutinghub.LeaderPositionType" %>
<g:form class="dialog-form" action="save">
    <div class="dialog">
        <s:bigTextField name="scoutGroupName" otherAttrs="[idField:'scoutGroupId', positionField: 'position']" class="unitSelector" value="${leaderGroupInstance?.scoutGroup}"
                        code="${message(code:'label.unitNumber')}"
                        placeholder="${message(code:'label.unitNumber')}">
        </s:bigTextField>

        <g:hiddenField name="leader.id" value="${leaderGroupInstance?.leader?.id}"/>
        %{--<s:unitSelector name="unitNumber" class="unitSelector" value="${leaderGroupInstance?.scoutGroup?.groupLabel}" code="${message(code:'label.unitNumber')}"/>--}%
        <g:hiddenField id="scoutGroupId" name="scoutGroup.id" value="${leaderGroupInstance?.scoutGroup?.id}"/>

        <s:selecter class="selecter" from="${LeaderPositionType.values()}" optionKey="${{it.name()}}" optionValue="${{it.name()?.humanize()}}"
                    id="position" name="position" value="${leaderGroupInstance?.position?.name()}" code="${message(code:'label.unitPosition')}"
                    noSelection="${['':"Select Position"]}"
                    placeholder="${message(code:'label.unitPosition')}"/>
    </div>
    <div class="buttons">
        <g:submitButton name="save" value="Save"/>
    </div>
</g:form>

