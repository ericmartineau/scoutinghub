

<%@ page import="scoutcert.Certification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'certification.label', default: 'Certification')}" />
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
            <g:hasErrors bean="${certificationInstance}">
            <div class="errors">
                <g:renderErrors bean="${certificationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${certificationInstance?.id}" />
                <g:hiddenField name="version" value="${certificationInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="externalId"><g:message code="certification.externalId.label" default="External Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'externalId', 'errors')}">
                                    <g:textField name="externalId" value="${certificationInstance?.externalId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="certification.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${certificationInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="certification.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" cols="40" rows="5" value="${certificationInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="durationInDays"><g:message code="certification.durationInDays.label" default="Duration In Days" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'durationInDays', 'errors')}">
                                    <g:textField name="durationInDays" value="${fieldValue(bean: certificationInstance, field: 'durationInDays')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tourPermitRequired"><g:message code="certification.tourPermitRequired.label" default="Tour Permit Required" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'tourPermitRequired', 'errors')}">
                                    <g:checkBox name="tourPermitRequired" value="${certificationInstance?.tourPermitRequired}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createDate"><g:message code="certification.createDate.label" default="Create Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'createDate', 'errors')}">
                                    <g:datePicker name="createDate" precision="day" value="${certificationInstance?.createDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="updateDate"><g:message code="certification.updateDate.label" default="Update Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'updateDate', 'errors')}">
                                    <g:datePicker name="updateDate" precision="day" value="${certificationInstance?.updateDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="leaderCertifications"><g:message code="certification.leaderCertifications.label" default="Leader Certifications" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationInstance, field: 'leaderCertifications', 'errors')}">
                                    
<ul>
<g:each in="${certificationInstance?.leaderCertifications?}" var="l">
    <li><g:link controller="leaderCertification" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="leaderCertification" action="create" params="['certification.id': certificationInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'leaderCertification.label', default: 'LeaderCertification')])}</g:link>

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
