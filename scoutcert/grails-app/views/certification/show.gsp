
<%@ page import="scoutcert.Certification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'certification.label', default: 'Certification')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:header><g:message code="default.show.label" args="[entityName]" /></g:header>
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
                            <td valign="top" class="name"><g:message code="certification.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certification.externalId.label" default="External Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: "externalId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certification.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certification.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certification.durationInDays.label" default="Duration In Days" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: "durationInDays")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certification.tourPermitRequired.label" default="Tour Permit Required" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${certificationInstance?.tourPermitRequired}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certification.createDate.label" default="Create Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${certificationInstance?.createDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certification.updateDate.label" default="Update Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${certificationInstance?.updateDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certification.leaderCertifications.label" default="Leader Certifications" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${certificationInstance.leaderCertifications}" var="l">
                                    <li><g:link controller="leaderCertification" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${certificationInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
