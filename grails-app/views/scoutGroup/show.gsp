
<%@ page import="scoutinghub.ScoutGroup" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'scoutGroup.label', default: 'ScoutGroup')}" />
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
                            <td valign="top" class="name"><g:message code="scoutGroup.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: scoutGroupInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.groupLabel.label" default="Group Label" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: scoutGroupInstance, field: "groupLabel")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.parent.label" default="Parent" /></td>
                            
                            <td valign="top" class="value"><g:link controller="scoutGroup" action="show" id="${scoutGroupInstance?.parent?.id}">${scoutGroupInstance?.parent?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.leftNode.label" default="Left Node" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: scoutGroupInstance, field: "leftNode")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.rightNode.label" default="Right Node" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: scoutGroupInstance, field: "rightNode")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.unitType.label" default="Unit Type" /></td>
                            
                            <td valign="top" class="value">${scoutGroupInstance?.unitType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.createDate.label" default="Create Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${scoutGroupInstance?.createDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.updateDate.label" default="Update Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${scoutGroupInstance?.updateDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.childGroups.label" default="Child Groups" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${scoutGroupInstance.childGroups}" var="c">
                                    <li><g:link controller="scoutGroup" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.groupIdentifier.label" default="Group Identifier" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: scoutGroupInstance, field: "groupIdentifier")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.groupType.label" default="Group Type" /></td>
                            
                            <td valign="top" class="value">${scoutGroupInstance?.groupType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scoutGroup.leaderGroups.label" default="Leader Groups" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${scoutGroupInstance.leaderGroups}" var="l">
                                    <li><g:link controller="leaderGroup" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${scoutGroupInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
