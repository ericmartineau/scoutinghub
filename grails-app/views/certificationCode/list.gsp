
<%@ page import="scoutinghub.CertificationCode" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'certificationCode.label', default: 'CertificationCode')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'certificationCode.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="code" title="${message(code: 'certificationCode.code.label', default: 'Code')}" />
                        
                            <th><g:message code="certificationCode.certification.label" default="Certification" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${certificationCodeInstanceList}" status="i" var="certificationCodeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${certificationCodeInstance.id}">${fieldValue(bean: certificationCodeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: certificationCodeInstance, field: "code")}</td>
                        
                            <td>${fieldValue(bean: certificationCodeInstance, field: "certification")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${certificationCodeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
