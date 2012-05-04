

<%@ page import="scoutinghub.ProgramCertification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'programCertification.label', default: 'ProgramCertification')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:header><g:message code="default.edit.label" args="[entityName]" /></g:header>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${programCertificationInstance}">
            <div class="errors">
                <g:renderErrors bean="${programCertificationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${programCertificationInstance?.id}" />
                <g:hiddenField name="version" value="${programCertificationInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            %{--<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="unitType"><g:message code="programCertification.unitType.label" default="Unit Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: programCertificationInstance, field: 'unitType', 'errors')}">
                                    <g:select name="unitType" from="${scoutinghub.ScoutUnitType?.values()}" value="${programCertificationInstance?.unitType}" noSelection="['': '']" />
                                </td>
                            </tr>--}%
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="required"><g:message code="programCertification.required.label" default="Required" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: programCertificationInstance, field: 'required', 'errors')}">
                                    <g:checkBox name="required" value="${programCertificationInstance?.required}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="certification"><g:message code="programCertification.certification.label" default="Certification" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: programCertificationInstance, field: 'certification', 'errors')}">
                                    <g:select name="certification.id" from="${scoutinghub.Certification.list()}" optionKey="id" value="${programCertificationInstance?.certification?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="positionType"><g:message code="programCertification.positionType.label" default="Position Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: programCertificationInstance, field: 'positionType', 'errors')}">
                                    <g:select name="positionType" from="${scoutinghub.LeaderPositionType?.values()}" value="${programCertificationInstance?.positionType}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="startDate"><g:message code="programCertification.startDate.label" default="Start Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: programCertificationInstance, field: 'startDate', 'errors')}">
                                    <g:datePicker name="startDate" precision="day" value="${programCertificationInstance?.startDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endDate"><g:message code="programCertification.endDate.label" default="End Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: programCertificationInstance, field: 'endDate', 'errors')}">
                                    <g:datePicker name="endDate" precision="day" value="${programCertificationInstance?.endDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createDate"><g:message code="programCertification.createDate.label" default="Create Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: programCertificationInstance, field: 'createDate', 'errors')}">
                                    <g:datePicker name="createDate" precision="day" value="${programCertificationInstance?.createDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="updateDate"><g:message code="programCertification.updateDate.label" default="Update Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: programCertificationInstance, field: 'updateDate', 'errors')}">
                                    <g:datePicker name="updateDate" precision="day" value="${programCertificationInstance?.updateDate}" noSelection="['': '']" />
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
