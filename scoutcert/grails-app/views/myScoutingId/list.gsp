
<%@ page import="scoutcert.MyScoutingId" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'myScoutingId.label', default: 'MyScoutingId')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'myScoutingId.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="myScoutingIdentifier" title="${message(code: 'myScoutingId.myScoutingIdentifier.label', default: 'My Scouting Identifier')}" />
                        
                            <g:sortableColumn property="createDate" title="${message(code: 'myScoutingId.createDate.label', default: 'Create Date')}" />
                        
                            <g:sortableColumn property="updateDate" title="${message(code: 'myScoutingId.updateDate.label', default: 'Update Date')}" />
                        
                            <th><g:message code="myScoutingId.leader.label" default="Leader" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${myScoutingIdInstanceList}" status="i" var="myScoutingIdInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${myScoutingIdInstance.id}">${fieldValue(bean: myScoutingIdInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: myScoutingIdInstance, field: "myScoutingIdentifier")}</td>
                        
                            <td><g:formatDate date="${myScoutingIdInstance.createDate}" /></td>
                        
                            <td><g:formatDate date="${myScoutingIdInstance.updateDate}" /></td>
                        
                            <td>${fieldValue(bean: myScoutingIdInstance, field: "leader")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${myScoutingIdInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
