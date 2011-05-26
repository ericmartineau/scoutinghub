
<%@ page import="scoutcert.LeaderGroup" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'leaderGroup.label', default: 'LeaderGroup')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'leaderGroup.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="createDate" title="${message(code: 'leaderGroup.createDate.label', default: 'Create Date')}" />
                        
                            <g:sortableColumn property="updateDate" title="${message(code: 'leaderGroup.updateDate.label', default: 'Update Date')}" />
                        
                            <g:sortableColumn property="admin" title="${message(code: 'leaderGroup.admin.label', default: 'Admin')}" />
                        
                            <th><g:message code="leaderGroup.leader.label" default="Leader" /></th>
                        
                            <g:sortableColumn property="position" title="${message(code: 'leaderGroup.position.label', default: 'Position')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${leaderGroupInstanceList}" status="i" var="leaderGroupInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${leaderGroupInstance.id}">${fieldValue(bean: leaderGroupInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${leaderGroupInstance.createDate}" /></td>
                        
                            <td><g:formatDate date="${leaderGroupInstance.updateDate}" /></td>
                        
                            <td><g:formatBoolean boolean="${leaderGroupInstance.admin}" /></td>
                        
                            <td>${fieldValue(bean: leaderGroupInstance, field: "leader")}</td>
                        
                            <td>${fieldValue(bean: leaderGroupInstance, field: "position")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${leaderGroupInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
