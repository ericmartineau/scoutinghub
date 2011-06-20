<%@ page import="scoutinghub.infusionsoft.InfusionsoftSettings" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'infusionsoftSettings.label', default: 'InfusionsoftSettings')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>

                <g:sortableColumn property="id" title="${message(code: 'infusionsoftSettings.id.label', default: 'Id')}"/>

                <g:sortableColumn property="infusionsoftKey" title="${message(code: 'infusionsoftSettings.infusionsoftKey.label', default: 'Infusionsoft Key')}"/>

                <g:sortableColumn property="infusionsoftUrl" title="${message(code: 'infusionsoftSettings.infusionsoftUrl.label', default: 'Infusionsoft Url')}"/>

            </tr>
            </thead>
            <tbody>
            <g:each in="${infusionsoftSettingsInstanceList}" status="i" var="infusionsoftSettingsInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td><g:link action="show" id="${infusionsoftSettingsInstance.id}">${fieldValue(bean: infusionsoftSettingsInstance, field: "id")}</g:link></td>

                    <td>${fieldValue(bean: infusionsoftSettingsInstance, field: "infusionsoftKey")}</td>

                    <td>${fieldValue(bean: infusionsoftSettingsInstance, field: "infusionsoftUrl")}</td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${infusionsoftSettingsInstanceTotal}"/>
    </div>
</div>
</body>
</html>
