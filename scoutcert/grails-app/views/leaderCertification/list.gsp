
<%@ page import="scoutcert.LeaderCertification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'leaderCertification.label', default: 'LeaderCertification')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'leaderCertification.id.label', default: 'Id')}" />
                        
                            <th><g:message code="leaderCertification.certification.label" default="Certification" /></th>
                        
                            <g:sortableColumn property="dateEarned" title="${message(code: 'leaderCertification.dateEarned.label', default: 'Date Earned')}" />
                        
                            <th><g:message code="leaderCertification.leader.label" default="Leader" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${leaderCertificationInstanceList}" status="i" var="leaderCertificationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${leaderCertificationInstance.id}">${fieldValue(bean: leaderCertificationInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: leaderCertificationInstance, field: "certification")}</td>
                        
                            <td><g:formatDate date="${leaderCertificationInstance.dateEarned}" /></td>
                        
                            <td>${fieldValue(bean: leaderCertificationInstance, field: "leader")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${leaderCertificationInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
