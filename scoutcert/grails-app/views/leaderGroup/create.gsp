<%@ page import="scoutinghub.LeaderPositionType" %>
<div class="body">
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${leaderGroupInstance}">
        <g:msgbox type="error">
            <g:renderErrors bean="${leaderGroupInstance}" as="list"/>
        </g:msgbox>

    </g:hasErrors>
    <g:form action="save">
        <div class="dialog">
            <s:bigTextField name="scoutGroupName" otherAttrs="[idField:'scoutGroupId', positionField: 'position']" class="unitSelector" value="${leaderGroupInstance?.scoutGroup}" code="${message(code:'label.unitNumber')}"
                    placeholder="${message(code:'label.unitNumber')}">
            </s:bigTextField>

            <g:hiddenField name="leader.id" value="${leaderGroupInstance?.leader?.id}" />
            %{--<s:unitSelector name="unitNumber" class="unitSelector" value="${leaderGroupInstance?.scoutGroup?.groupLabel}" code="${message(code:'label.unitNumber')}"/>--}%
            <g:hiddenField id="scoutGroupId" name="scoutGroup.id" value="${leaderGroupInstance?.scoutGroup?.id}"/>

            <s:selecter class="selecter" from="${LeaderPositionType.values()}" optionKey="${{it.name()}}" optionValue="${{it.name()?.humanize()}}"
                    id="position" name="position" value="${leaderGroupInstance?.position?.name()}" code="${message(code:'label.unitPosition')}"
                    noSelection="${['':"Select Position"]}"
                    placeholder="${message(code:'label.unitPosition')}"/>
        </div>
        <g:submitToRemote onComplete="decorate()" class="ui-button" update="dialog" url="[action:'save']" name="save" value="Save" />


    </g:form>
</div>
