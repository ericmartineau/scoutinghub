<%@ page import="scoutcert.Certification" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'certification.label', default: 'Certification')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>
<body>
<div class="body">
    <g:header><g:message code="default.list.label" args="[entityName]"/></g:header>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="nav">
        <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></span>
    </div>
    <div class="list">
        <table cellpadding="0" cellspacing="0" width="100%">
            <thead>
            <tr>

                <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="id" title="${message(code: 'certification.id.label', default: 'Id')}"/>

                <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="externalId" title="${message(code: 'certification.externalId.label', default: 'External Id')}"/>

                <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="name" title="${message(code: 'certification.name.label', default: 'Name')}"/>

                %{--<g:sortableColumn class="ui-widget-header  tableHeader" property="description" title="${message(code: 'certification.description.label', default: 'Description')}" />--}%

                <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="durationInDays" title="${message(code: 'certification.durationInDays.label', default: 'Duration In Days')}"/>

                <g:sortableColumn class="ui-state-hover ui-widget-header tableHeader" property="tourPermitRequired" title="${message(code: 'certification.tourPermitRequired.label', default: 'Tour Permit Required')}"/>

            </tr>
            </thead>
            <tbody>
            <g:each in="${certificationInstanceList}" status="i" var="certificationInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td class="tabular"><g:link action="show" id="${certificationInstance.id}">${fieldValue(bean: certificationInstance, field: "id")}</g:link></td>

                    <td class="tabular">${fieldValue(bean: certificationInstance, field: "externalId")}</td>

                    <td class="tabular">${fieldValue(bean: certificationInstance, field: "name")}</td>

                    %{--<td class="tabular">${fieldValue(bean: certificationInstance, field: "description")}</td>--}%

                    <td class="tabular">${fieldValue(bean: certificationInstance, field: "durationInDays")}</td>

                    <td class="tabular"><g:formatBoolean boolean="${certificationInstance.tourPermitRequired}"/></td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${certificationInstanceTotal}"/>
    </div>
</div>
</body>
</html>
