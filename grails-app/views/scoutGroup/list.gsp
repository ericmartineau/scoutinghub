<%@ page import="scoutinghub.ScoutGroup" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'scoutGroup.label', default: 'ScoutGroup')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <s:msg code="${flash.message}" type="info"/>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>

                <g:sortableColumn property="id" title="${message(code: 'scoutGroup.id.label', default: 'Id')}"/>

                <g:sortableColumn property="groupLabel" title="${message(code: 'scoutGroup.groupLabel.label', default: 'Group Label')}"/>

                <g:sortableColumn property="unitType" title="${message(code: 'scoutGroup.unitType.label', default: 'Unit Type')}"/>

                <g:sortableColumn property="groupIdentifier" title="${message(code: 'scoutGroup.groupIdentifier.label', default: 'Group ID')}"/>

                <th><g:message code="scoutGroup.parent.label" default="Parent"/></th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${scoutGroupInstanceList}" status="i" var="scoutGroupInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td><g:link action="show" id="${scoutGroupInstance.id}">${fieldValue(bean: scoutGroupInstance, field: "id")}</g:link></td>

                    <td>${fieldValue(bean: scoutGroupInstance, field: "groupLabel")}</td>

                    <td>${fieldValue(bean: scoutGroupInstance, field: "unitType")}</td>

                    <td>${fieldValue(bean: scoutGroupInstance, field: "groupIdentifier")}</td>

                    <td>${fieldValue(bean: scoutGroupInstance, field: "parent")}</td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${scoutGroupInstanceTotal}"/>
    </div>
</div>
</body>
</html>
