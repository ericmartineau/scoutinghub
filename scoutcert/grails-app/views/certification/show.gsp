<%@ page import="scoutcert.Certification" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'certification.label', default: 'Certification')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>
<body>
<div class="body">
    <g:header><g:message code="default.show.label" args="[entityName]"/></g:header>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <table class="recordTable" cellspacing="0">
        <tr>
            <td valign="top">
                <g:message code="certification.name.label" default="Name"/>:<br/>
                <span class="profileData">${certificationInstance?.name}</span>
            </td>
            <td valign="top">
                <g:message code="certification.externalId.label" default="External Id"/>:<br/>
                <span class="profileData">${certificationInstance?.externalId}</span>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <g:message code="certification.durationInDays.label" default="Duration In Days"/>:<br/>
                <span class="profileData">${certificationInstance?.durationInDays}</span>
            </td>
            <td valign="top">
                <g:message code="certification.tourPermitRequired.label" default="Tour Permit Required"/>:<br/>
                <span class="profileData"><g:formatBoolean boolean="${certificationInstance?.tourPermitRequired}"/></span>
            </td>
        </tr>
    </table>

    <g:header><g:message code="certification.desription.label" default="Description"/></g:header>
    <div style="font-size:15px" class="groupSection">
        ${fieldValue(bean: certificationInstance, field: "description")}
    </div>

    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${certificationInstance?.id}"/>
            <span class="button"><g:actionSubmit class="edit ui-button" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
            <span class="button"><g:actionSubmit class="delete ui-button" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </g:form>
    </div>
</div>
</body>
</html>
