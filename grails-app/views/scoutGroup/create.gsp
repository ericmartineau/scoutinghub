<%@ page import="scoutinghub.ScoutGroup" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'scoutGroup.label', default: 'ScoutGroup')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${scoutGroupInstance}">
        <div class="errors">
            <g:renderErrors bean="${scoutGroupInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save">
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="groupLabel"><g:message code="scoutGroup.groupLabel.label" default="Group Label"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'groupLabel', 'errors')}">
                        <g:textField name="groupLabel" value="${scoutGroupInstance?.groupLabel}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="parent"><g:message code="scoutGroup.parent.label" default="Parent"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'parent', 'errors')}">
                        <g:select name="parent.id" from="${scoutinghub.ScoutGroup.list()}" optionKey="id" value="${scoutGroupInstance?.parent?.id}" noSelection="['null': '']"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="groupType"><g:message code="scoutGroup.groupType.label" default="Group Type"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'groupType', 'errors')}">
                        <g:select name="groupType" from="${scoutinghub.ScoutGroupType?.values()}" value="${scoutGroupInstance?.groupType}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="unitType"><g:message code="scoutGroup.unitType.label" default="Unit Type"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'unitType', 'errors')}">
                        <g:select name="unitType" from="${scoutinghub.ScoutUnitType?.values()}" value="${scoutGroupInstance?.unitType}" noSelection="['': '']"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="groupIdentifier"><g:message code="scoutGroup.groupIdentifier.label" default="Group Identifier"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'groupIdentifier', 'errors')}">
                        <g:textField name="groupIdentifier" value="${scoutGroupInstance?.groupIdentifier}"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
