
<%@ page import="scoutcert.LeaderCertification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'leaderCertification.label', default: 'LeaderCertification')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:header><g:message code="default.list.label" args="[entityName]" /></g:header>
        <div class="nav">
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="id" title="${message(code: 'leaderCertification.id.label', default: 'Id')}" />
                            <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="certification" title="${message(code: 'leaderCertification.certification.label', default: 'Certification')}" />
                        
                            <!--<th><g:message code="leaderCertification.certification.label" default="Certification" /></th>-->
                        
                            <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="dateEarned" title="${message(code: 'leaderCertification.dateEarned.label', default: 'Date Earned')}" />
                            <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="dateEarned" title="${message(code: 'leaderCertification.leader.label', default: 'Leader')}" />
                            <!--<th><g:message code="leaderCertification.leader.label" default="Leader" /></th>-->
                        
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
