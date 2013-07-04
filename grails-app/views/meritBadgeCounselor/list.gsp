<%@ page import="scoutinghub.meritbadge.MeritBadgeCounselor" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="$layoutName" />
        <g:set var="entityName" value="${message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'meritBadgeCounselor.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="originalCertificationDate" title="${message(code: 'meritBadgeCounselor.originalCertificationDate.label', default: 'Original Certification Date')}" />
                        
                            <g:sortableColumn property="recertificationDate" title="${message(code: 'meritBadgeCounselor.recertificationDate.label', default: 'Recertification Date')}" />
                        
                            <th><g:message code="meritBadgeCounselor.leader.label" default="Leader" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${meritBadgeCounselorInstanceList}" status="i" var="meritBadgeCounselorInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${meritBadgeCounselorInstance.id}">${fieldValue(bean: meritBadgeCounselorInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${meritBadgeCounselorInstance.originalCertificationDate}" /></td>
                        
                            <td><g:formatDate date="${meritBadgeCounselorInstance.recertificationDate}" /></td>
                        
                            <td>${fieldValue(bean: meritBadgeCounselorInstance, field: "leader")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${meritBadgeCounselorInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
