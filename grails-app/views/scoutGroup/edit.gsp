

<%@ page import="scoutinghub.ScoutGroup" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'scoutGroup.label', default: 'ScoutGroup')}" />
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
            <g:hasErrors bean="${scoutGroupInstance}">
            <div class="errors">
                <g:renderErrors bean="${scoutGroupInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${scoutGroupInstance?.id}" />
                <g:hiddenField name="version" value="${scoutGroupInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="groupLabel"><g:message code="scoutGroup.groupLabel.label" default="Group Label" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'groupLabel', 'errors')}">
                                    <g:textField name="groupLabel" value="${scoutGroupInstance?.groupLabel}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="parent"><g:message code="scoutGroup.parent.label" default="Parent" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'parent', 'errors')}">
                                    <g:select name="parent.id" from="${scoutinghub.ScoutGroup.list()}" optionKey="id" value="${scoutGroupInstance?.parent?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="leftNode"><g:message code="scoutGroup.leftNode.label" default="Left Node" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'leftNode', 'errors')}">
                                    <g:textField name="leftNode" value="${fieldValue(bean: scoutGroupInstance, field: 'leftNode')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="rightNode"><g:message code="scoutGroup.rightNode.label" default="Right Node" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'rightNode', 'errors')}">
                                    <g:textField name="rightNode" value="${fieldValue(bean: scoutGroupInstance, field: 'rightNode')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="unitType"><g:message code="scoutGroup.unitType.label" default="Unit Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'unitType', 'errors')}">
                                    <g:select name="unitType" from="${scoutinghub.ScoutUnitType?.values()}" value="${scoutGroupInstance?.unitType}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createDate"><g:message code="scoutGroup.createDate.label" default="Create Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'createDate', 'errors')}">
                                    <g:datePicker name="createDate" precision="day" value="${scoutGroupInstance?.createDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="updateDate"><g:message code="scoutGroup.updateDate.label" default="Update Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'updateDate', 'errors')}">
                                    <g:datePicker name="updateDate" precision="day" value="${scoutGroupInstance?.updateDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="childGroups"><g:message code="scoutGroup.childGroups.label" default="Child Groups" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'childGroups', 'errors')}">
                                    
<ul>
<g:each in="${scoutGroupInstance?.childGroups?}" var="c">
    <li><g:link controller="scoutGroup" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="scoutGroup" action="create" params="['scoutGroup.id': scoutGroupInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'scoutGroup.label', default: 'ScoutGroup')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="groupIdentifier"><g:message code="scoutGroup.groupIdentifier.label" default="Group Identifier" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'groupIdentifier', 'errors')}">
                                    <g:textField name="groupIdentifier" value="${scoutGroupInstance?.groupIdentifier}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="groupType"><g:message code="scoutGroup.groupType.label" default="Group Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'groupType', 'errors')}">
                                    <g:select name="groupType" from="${scoutinghub.ScoutGroupType?.values()}" value="${scoutGroupInstance?.groupType}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="leaderGroups"><g:message code="scoutGroup.leaderGroups.label" default="Leader Groups" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scoutGroupInstance, field: 'leaderGroups', 'errors')}">
                                    
<ul>
<g:each in="${scoutGroupInstance?.leaderGroups?}" var="l">
    <li><g:link controller="leaderGroup" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="leaderGroup" action="create" params="['scoutGroup.id': scoutGroupInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup')])}</g:link>

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
