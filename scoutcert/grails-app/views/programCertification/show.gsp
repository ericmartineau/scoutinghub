
<%@ page import="scoutinghub.ProgramCertification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'programCertification.label', default: 'ProgramCertification')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:header><g:message code="default.show.label" args="[entityName]" /></h1></g:header>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="programCertification.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: programCertificationInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="programCertification.unitType.label" default="Unit Type" /></td>
                            
                            <td valign="top" class="value">${programCertificationInstance?.unitType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="programCertification.required.label" default="Required" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${programCertificationInstance?.required}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="programCertification.certification.label" default="Certification" /></td>
                            
                            <td valign="top" class="value"><g:link controller="certification" action="show" id="${programCertificationInstance?.certification?.id}">${programCertificationInstance?.certification?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="programCertification.positionType.label" default="Position Type" /></td>
                            
                            <td valign="top" class="value">${programCertificationInstance?.positionType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="programCertification.startDate.label" default="Start Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${programCertificationInstance?.startDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="programCertification.endDate.label" default="End Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${programCertificationInstance?.endDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="programCertification.createDate.label" default="Create Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${programCertificationInstance?.createDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="programCertification.updateDate.label" default="Update Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${programCertificationInstance?.updateDate}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${programCertificationInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
