

<%@ page import="scoutcert.Certification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'certification.label', default: 'Certification')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${certificationInstance}">
            <div class="errors">
                <g:renderErrors bean="${certificationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="externalId"><g:message code="certification.externalId.label" default="External Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'externalId', 'errors')}">
                                    <g:textField name="externalId" value="${certificationInstance?.externalId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="certification.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${certificationInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="certification.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${certificationInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="durationInDays"><g:message code="certification.durationInDays.label" default="Duration In Days" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'durationInDays', 'errors')}">
                                    <g:textField name="durationInDays" value="${fieldValue(bean: certificationInstance, field: 'durationInDays')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tourPermitRequired"><g:message code="certification.tourPermitRequired.label" default="Tour Permit Required" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'tourPermitRequired', 'errors')}">
                                    <g:checkBox name="tourPermitRequired" value="${certificationInstance?.tourPermitRequired}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
