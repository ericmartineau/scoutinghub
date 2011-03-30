<%@ page import="scoutcert.Certification" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'certification.label', default: 'Certification')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>
<body>
<div class="body">
    <g:header><g:message code="default.edit.label" args="[entityName]"/></g:header>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${certificationInstance}">
        <div class="errors">
            <g:renderErrors bean="${certificationInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form method="post">
        <g:hiddenField name="id" value="${certificationInstance?.id}"/>
        <g:hiddenField name="version" value="${certificationInstance?.version}"/>
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="middle" class="name">
                        <label for="externalId" class="biglabel"><g:message code="certification.externalId.label" default="External Id"/></label>
                    </td>
                    <td valign="middle" class="value ${hasErrors(bean: certificationInstance, field: 'externalId', 'errors')}">
                        <g:textField size="35" name="externalId" class="regularForm ui-corner-all" value="${certificationInstance?.externalId}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="middle" class="name">
                        <label for="name" class="biglabel"><g:message code="certification.name.label" default="Name"/></label>
                    </td>
                    <td valign="middle" class="value ${hasErrors(bean: certificationInstance, field: 'name', 'errors')}">
                        <g:textField size="35" class="regularForm ui-corner-all" name="name" value="${certificationInstance?.name}"/>
                    </td>
                </tr>


                <tr class="prop">
                    <td valign="middle" class="name">
                        <label class="biglabel" for="durationInDays"><g:message code="certification.durationInDays.label" default="Duration In Days"/></label>
                    </td>
                    <td valign="middle" class="value ${hasErrors(bean: certificationInstance, field: 'durationInDays', 'errors')}">
                        <g:textField name="durationInDays" class="regularForm ui-corner-all" value="${fieldValue(bean: certificationInstance, field: 'durationInDays')}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="middle" class="name">
                        <label for="tourPermitRequired" class="biglabel"><g:message code="certification.tourPermitRequired.label" default="Tour Permit Required"/></label>
                    </td>
                    <td valign="middle" class="value ${hasErrors(bean: certificationInstance, field: 'tourPermitRequired', 'errors')}">
                        <g:checkBox name="tourPermitRequired" value="${certificationInstance?.tourPermitRequired}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="description" class="biglabel"><g:message code="certification.description.label" default="Description"/></label>
                    </td>
                    <td valign="middle" class="value ${hasErrors(bean: certificationInstance, field: 'description', 'errors')}">
                        <g:textArea class="regularForm ui-corner-all"  rows="8" cols="45" name="description" value="${certificationInstance?.description}"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
        <div class="buttons">
            <span class="button"><g:actionSubmit class="save ui-button" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
            <span class="button"><g:actionSubmit class="delete ui-button" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
