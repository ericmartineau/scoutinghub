<%@ page import="scoutinghub.infusionsoft.InfusionsoftFollowUpInfo" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
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
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="infusionsoftFollowUpInfo.id.label" default="Id"/></td>

                <td valign="top" class="value">${fieldValue(bean: infusionsoftFollowUpInfoInstance, field: "id")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message
                        code="infusionsoftFollowUpInfo.invitationFollowUpSequenceId.label"
                        default="Invitation Follow Up Sequence Id"/></td>

                <td valign="top"
                    class="value">${fieldValue(bean: infusionsoftFollowUpInfoInstance, field: "invitationFollowUpSequenceId")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message
                        code="infusionsoftFollowUpInfo.registrationFollowUpSequenceId.label"
                        default="Registration Follow Up Sequence Id"/></td>

                <td valign="top"
                    class="value">${fieldValue(bean: infusionsoftFollowUpInfoInstance, field: "registrationFollowUpSequenceId")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="infusionsoftFollowUpInfo.scoutGroup.label"
                                                         default="Scout Group"/></td>

                <td valign="top" class="value"><g:link controller="scoutGroup" action="show"
                                                       id="${infusionsoftFollowUpInfoInstance?.scoutGroup?.id}">${infusionsoftFollowUpInfoInstance?.scoutGroup?.encodeAsHTML()}</g:link></td>

            </tr>

            </tbody>
        </table>
    </div>

    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${infusionsoftFollowUpInfoInstance?.id}"/>
            <span class="button"><g:actionSubmit class="edit" action="edit"
                                                 value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </g:form>
    </div>
</div>
</body>
</html>
