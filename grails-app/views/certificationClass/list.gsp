
<%@ page import="scoutcert.CertificationClass" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'certificationClass.label', default: 'CertificationClass')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'certificationClass.id.label', default: 'Id')}" />
                        
                            <th><g:message code="certificationClass.certification.label" default="Certification" /></th>
                        
                            <g:sortableColumn property="classDate" title="${message(code: 'certificationClass.classDate.label', default: 'Class Date')}" />
                        
                            %{--<th><g:message code="certificationClass.coordinator.label" default="Coordinator" /></th>--}%
                        
                            <th><g:message code="certificationClass.location.time" default="Time" /></th>
                            <th><g:message code="certificationClass.location.label" default="Location" /></th>

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${certificationClassInstanceList}" status="i" var="certificationClassInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${certificationClassInstance.id}">${fieldValue(bean: certificationClassInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: certificationClassInstance, field: "certification")}</td>
                        
                            <td><g:formatDate date="${certificationClassInstance.classDate}" format="MM-dd-yyyy"/></td>

                            %{--<td>${fieldValue(bean: certificationClassInstance, field: "coordinator")}</td>--}%
                        
                            <td>${fieldValue(bean: certificationClassInstance, field: "time")}</td>
                            <td>${fieldValue(bean: certificationClassInstance, field: "location")}</td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${certificationClassInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
