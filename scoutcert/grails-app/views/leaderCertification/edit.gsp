

<%@ page import="scoutcert.LeaderCertification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'leaderCertification.label', default: 'LeaderCertification')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:header><g:message code="default.edit.label" args="[entityName]" /></g:header>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${leaderCertificationInstance}">
            <div class="errors">
                <g:renderErrors bean="${leaderCertificationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${leaderCertificationInstance?.id}" />
                <g:hiddenField name="version" value="${leaderCertificationInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="certification"><g:message code="leaderCertification.certification.label" default="Certification" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderCertificationInstance, field: 'certification', 'errors')}">
                                    <g:select name="certification.id" from="${scoutcert.Certification.list()}" optionKey="id" value="${leaderCertificationInstance?.certification?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateEarned"><g:message code="leaderCertification.dateEarned.label" default="Date Earned" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderCertificationInstance, field: 'dateEarned', 'errors')}">
                                    <g:datePicker name="dateEarned" precision="day" value="${leaderCertificationInstance?.dateEarned}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="leader"><g:message code="leaderCertification.leader.label" default="Leader" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderCertificationInstance, field: 'leader', 'errors')}">
                                    <g:select name="leader.id" from="${scoutcert.Leader.list()}" optionKey="id" value="${leaderCertificationInstance?.leader?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
