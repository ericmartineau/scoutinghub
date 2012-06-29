<%@ page import="scoutinghub.infusionsoft.InfusionsoftFollowUpInfo" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'infusionsoftFollowUpInfo.label', default: 'InfusionsoftFollowUpInfo')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>

                <g:sortableColumn property="id"
                                  title="${message(code: 'infusionsoftFollowUpInfo.id.label', default: 'Id')}"/>

                <g:sortableColumn property="invitationFollowUpSequenceId"
                                  title="${message(code: 'infusionsoftFollowUpInfo.invitationFollowUpSequenceId.label', default: 'Invitation Follow Up Sequence Id')}"/>

                <g:sortableColumn property="registrationFollowUpSequenceId"
                                  title="${message(code: 'infusionsoftFollowUpInfo.registrationFollowUpSequenceId.label', default: 'Registration Follow Up Sequence Id')}"/>

                <th><g:message code="infusionsoftFollowUpInfo.scoutGroup.label" default="Scout Group"/></th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${infusionsoftFollowUpInfoInstanceList}" status="i" var="infusionsoftFollowUpInfoInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td><g:link action="show"
                                id="${infusionsoftFollowUpInfoInstance.id}">${fieldValue(bean: infusionsoftFollowUpInfoInstance, field: "id")}</g:link></td>

                    <td>${fieldValue(bean: infusionsoftFollowUpInfoInstance, field: "invitationFollowUpSequenceId")}</td>

                    <td>${fieldValue(bean: infusionsoftFollowUpInfoInstance, field: "registrationFollowUpSequenceId")}</td>

                    <td>${fieldValue(bean: infusionsoftFollowUpInfoInstance, field: "scoutGroup")}</td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${infusionsoftFollowUpInfoInstanceTotal}"/>
    </div>
</div>
</body>
</html>
