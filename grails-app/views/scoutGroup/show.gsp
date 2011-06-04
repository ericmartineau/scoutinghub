<%@ page import="scoutinghub.ScoutGroup" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'scoutGroup.label', default: 'ScoutGroup')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<g:form>
    <s:content>
        <s:section>
            <s:sectionHeader icon="units-icon"><g:message code="default.edit.label" args="[message(code:entityName)]"/></s:sectionHeader>
            <g:if test="${flash.message}">
                <s:msg code="${flash.message}" type="info"/>
            </g:if>
            <s:propertyList class="vertical-form">
                <s:textField class="alternate-color" size="40" name="groupLabel" value="${scoutGroupInstance?.groupLabel}" code="scoutGroup.groupLabel.label"/>
                <s:selecter code="scoutGroup.parent.label" name="parent.id" from="${scoutinghub.ScoutGroup.list()}" optionKey="id" value="${scoutGroupInstance?.parent?.id}"
                            noSelection="['null': '']"/>
                <s:selecter class="alternate-color" code="scoutGroup.groupType.label" name="groupType" from="${scoutinghub.ScoutGroupType?.values()}" value="${scoutGroupInstance?.groupType}"/>
                <s:selecter code="scoutGroup.unitType.label" name="unitType" from="${scoutinghub.ScoutUnitType?.values()}" value="${scoutGroupInstance?.unitType}" noSelection="['': '']"/>
                <s:textField class="alternate-color" code="scoutGroup.groupIdentifier.label" name="groupIdentifier" value="${scoutGroupInstance?.groupIdentifier}"/>
            </s:propertyList>

            <div class="buttons">

                <g:hiddenField name="id" value="${scoutGroupInstance?.id}"/>
                <span class="button"><g:actionSubmit class="update" action="update" value="${message(code: 'default.button.edit.label', default: 'Update')}"/></span>
                <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>

            </div>
        </s:section>
    </s:content>

</g:form>
</body>
</html>
