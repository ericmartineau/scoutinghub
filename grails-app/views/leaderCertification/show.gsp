
<%@ page import="scoutinghub.LeaderCertification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'leaderCertification.label', default: 'LeaderCertification')}" />
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
                            <td valign="top" class="name"><g:message code="leaderCertification.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: leaderCertificationInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="leaderCertification.certification.label" default="Certification" /></td>
                            
                            <td valign="top" class="value"><g:link controller="certification" action="show" id="${leaderCertificationInstance?.certification?.id}">${leaderCertificationInstance?.certification?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="leaderCertification.dateEarned.label" default="Date Earned" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${leaderCertificationInstance?.dateEarned}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="leaderCertification.leader.label" default="Leader" /></td>
                            
                            <td valign="top" class="value"><g:link controller="leader" action="show" id="${leaderCertificationInstance?.leader?.id}">${leaderCertificationInstance?.leader?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${leaderCertificationInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
