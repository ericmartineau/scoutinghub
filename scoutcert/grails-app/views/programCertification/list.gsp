
<%@ page import="scoutinghub.ProgramCertification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'programCertification.label', default: 'ProgramCertification')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:header><g:message code="default.list.label" args="[entityName]"/></g:header>
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
                        
                            <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="id" title="${message(code: 'programCertification.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="unitType" title="${message(code: 'programCertification.unitType.label', default: 'Unit Type')}" />
                        
                            <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="required" title="${message(code: 'programCertification.required.label', default: 'Required')}" />

                            <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="certification" title="${message(code: 'programCertification.certification.label', default: 'Certification')}" />

                            <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="positionType" title="${message(code: 'programCertification.positionType.label', default: 'Position Type')}" />
                        
                            <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="startDate" title="${message(code: 'programCertification.startDate.label', default: 'Start Date')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${programCertificationInstanceList}" status="i" var="programCertificationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${programCertificationInstance.id}">${fieldValue(bean: programCertificationInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: programCertificationInstance, field: "unitType")}</td>
                        
                            <td><g:formatBoolean boolean="${programCertificationInstance.required}" /></td>
                        
                            <td>${fieldValue(bean: programCertificationInstance, field: "certification")}</td>
                        
                            <td>${fieldValue(bean: programCertificationInstance, field: "positionType")}</td>
                        
                            <td><g:formatDate date="${programCertificationInstance.startDate}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${programCertificationInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
