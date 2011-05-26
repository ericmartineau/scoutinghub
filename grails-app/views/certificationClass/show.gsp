
<%@ page import="scoutinghub.CertificationClass" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'certificationClass.label', default: 'CertificationClass')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certificationClass.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: certificationClassInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certificationClass.certification.label" default="Certification" /></td>
                            
                            <td valign="top" class="value"><g:link controller="certification" action="show" id="${certificationClassInstance?.certification?.id}">${certificationClassInstance?.certification?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certificationClass.classDate.label" default="Class Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${certificationClassInstance?.classDate}" /></td>
                            
                        </tr>
                    
                        %{--<tr class="prop">--}%
                            %{--<td valign="top" class="name"><g:message code="certificationClass.coordinator.label" default="Coordinator" /></td>--}%
                            %{----}%
                            %{--<td valign="top" class="value"><g:link controller="leader" action="show" id="${certificationClassInstance?.coordinator?.id}">${certificationClassInstance?.coordinator?.encodeAsHTML()}</g:link></td>--}%
                            %{----}%
                        %{--</tr>--}%
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certificationClass.location.label" default="Location" /></td>
                            
                            <td valign="top" class="value"><g:link controller="address" action="show" id="${certificationClassInstance?.location?.id}">${certificationClassInstance?.location?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="certificationClass.registrants.label" default="Registrants" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${certificationClassInstance.registrants}" var="r">
                                    <li><g:link controller="leader" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${certificationClassInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
