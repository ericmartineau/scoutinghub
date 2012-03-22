<%@ page import="scoutinghub.infusionsoft.InfusionsoftSettings" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="${layoutName}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${infusionsoftSettingsInstance}">
        <div class="errors">
            <g:renderErrors bean="${infusionsoftSettingsInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save">
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="infusionsoftKey"><g:message code="infusionsoftSettings.infusionsoftKey.label" default="Infusionsoft Key"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: infusionsoftSettingsInstance, field: 'infusionsoftKey', 'errors')}">
                        <g:textField name="infusionsoftKey" value="${infusionsoftSettingsInstance?.infusionsoftKey}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="infusionsoftUrl"><g:message code="infusionsoftSettings.infusionsoftUrl.label" default="Infusionsoft Url"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: infusionsoftSettingsInstance, field: 'infusionsoftUrl', 'errors')}">
                        <g:textField name="infusionsoftUrl" value="${infusionsoftSettingsInstance?.infusionsoftUrl}"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
