

<%@ page import="scoutinghub.LeaderGroup" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'leaderGroup.label', default: 'LeaderGroup')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${leaderGroupInstance}">
            <div class="errors">
                <g:renderErrors bean="${leaderGroupInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${leaderGroupInstance?.id}" />
                <g:hiddenField name="version" value="${leaderGroupInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createDate"><g:message code="leaderGroup.createDate.label" default="Create Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderGroupInstance, field: 'createDate', 'errors')}">
                                    <g:datePicker name="createDate" precision="day" value="${leaderGroupInstance?.createDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="updateDate"><g:message code="leaderGroup.updateDate.label" default="Update Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderGroupInstance, field: 'updateDate', 'errors')}">
                                    <g:datePicker name="updateDate" precision="day" value="${leaderGroupInstance?.updateDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="admin"><g:message code="leaderGroup.admin.label" default="Admin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderGroupInstance, field: 'admin', 'errors')}">
                                    <g:checkBox name="admin" value="${leaderGroupInstance?.admin}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="leader"><g:message code="leaderGroup.leader.label" default="Leader" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderGroupInstance, field: 'leader', 'errors')}">
                                    <g:select name="leader.id" from="${scoutinghub.Leader.list()}" optionKey="id" value="${leaderGroupInstance?.leader?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="position"><g:message code="leaderGroup.position.label" default="Position" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderGroupInstance, field: 'position', 'errors')}">
                                    <g:select name="position" from="${scoutinghub.LeaderPositionType?.values()}" value="${leaderGroupInstance?.position}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="scoutGroup"><g:message code="leaderGroup.scoutGroup.label" default="Scout Group" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderGroupInstance, field: 'scoutGroup', 'errors')}">
                                    <g:select name="scoutGroup.id" from="${scoutinghub.ScoutGroup.list()}" optionKey="id" value="${leaderGroupInstance?.scoutGroup?.id}"  />
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
