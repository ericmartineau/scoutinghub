<%@ page import="scoutinghub.infusionsoft.InfusionsoftFollowUpInfo" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${infusionsoftFollowUpInfoInstance}">
        <div class="errors">
            <g:renderErrors bean="${infusionsoftFollowUpInfoInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form method="post">
        <g:hiddenField name="id" value="${infusionsoftFollowUpInfoInstance?.id}"/>
        <g:hiddenField name="version" value="${infusionsoftFollowUpInfoInstance?.version}"/>
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="invitationFollowUpSequenceId"><g:message
                                code="infusionsoftFollowUpInfo.invitationFollowUpSequenceId.label"
                                default="Invitation Follow Up Sequence Id"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: infusionsoftFollowUpInfoInstance, field: 'invitationFollowUpSequenceId', 'errors')}">
                        <g:textField name="invitationFollowUpSequenceId"
                                     value="${fieldValue(bean: infusionsoftFollowUpInfoInstance, field: 'invitationFollowUpSequenceId')}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="registrationFollowUpSequenceId"><g:message
                                code="infusionsoftFollowUpInfo.registrationFollowUpSequenceId.label"
                                default="Registration Follow Up Sequence Id"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: infusionsoftFollowUpInfoInstance, field: 'registrationFollowUpSequenceId', 'errors')}">
                        <g:textField name="registrationFollowUpSequenceId"
                                     value="${fieldValue(bean: infusionsoftFollowUpInfoInstance, field: 'registrationFollowUpSequenceId')}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="scoutGroup"><g:message code="infusionsoftFollowUpInfo.scoutGroup.label"
                                                           default="Scout Group"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: infusionsoftFollowUpInfoInstance, field: 'scoutGroup', 'errors')}">
                        <g:select name="scoutGroup.id" from="${scoutinghub.ScoutGroup.list()}" optionKey="id"
                                  value="${infusionsoftFollowUpInfoInstance?.scoutGroup?.id}"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:actionSubmit class="save" action="update"
                                                 value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
