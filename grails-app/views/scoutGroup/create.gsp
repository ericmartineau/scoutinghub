<%@ page import="scoutinghub.ScoutGroup" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'scoutGroup.label', default: 'ScoutGroup')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
%{--<div class="nav">--}%
%{--<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[message(code:entityName)]"/></g:link></span>--}%
%{--</div>--}%

<s:content>
    <s:section>
        <s:sectionHeader icon="units-icon"><g:message code="default.create.label" args="[message(code:entityName)]"/></s:sectionHeader>
        <g:if test="${flash.message}">
            <s:msg type="info" code="${flash.message}"/>
        </g:if>
        <g:hasErrors bean="${scoutGroupInstance}">
            <s:msg type="error">
                <g:renderErrors bean="${scoutGroupInstance}" as="list"/>
            </s:msg>
        </g:hasErrors>
        <g:form action="save">

            <s:propertyList class="vertical-form">
                <s:textField class="alternate-color" size="40" name="groupLabel" value="${scoutGroupInstance?.groupLabel}" code="scoutGroup.groupLabel.label"/>
                <s:textField name="parentNumber" otherAttrs="[idField:'parent.id']" class="unitSelector unit-selector-style" value="${scoutGroupInstance?.parent}"
                             code="${message(code:'scoutGroup.parent.label')}"/>


            %{--<s:unitSelector name="unitNumber" class="unitSelector" value="${createAccount?.unitNumber}" code="${message(code:'label.unitNumber')}"/>--}%
                <g:hiddenField name="parent.id" value="${scoutGroupInstance?.parent?.id}"/>

                %{--<s:selecter code="scoutGroup.parent.label" name="parent.id" from="${scoutinghub.ScoutGroup.list()}" optionKey="id" value="${scoutGroupInstance?.parent?.id}"--}%
                            %{--noSelection="['null': '']"/>--}%
                <s:selecter class="alternate-color" code="scoutGroup.groupType.label" name="groupType" from="${scoutinghub.ScoutGroupType?.values()}" value="${scoutGroupInstance?.groupType}"/>
                <s:selecter code="scoutGroup.unitType.label" name="unitType" from="${scoutinghub.ScoutUnitType?.values()}" value="${scoutGroupInstance?.unitType}" noSelection="['': '']"/>
                <s:textField class="alternate-color" code="scoutGroup.groupIdentifier.label" name="groupIdentifier" value="${scoutGroupInstance?.groupIdentifier}"/>
            </s:propertyList>


            <div class="buttons">
                <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
            </div>
        </g:form>
    </s:section>
</s:content>
</body>
</html>
