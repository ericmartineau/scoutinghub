<%@ page import="scoutinghub.CertificationClass" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'certificationClass.label', default: 'CertificationClass')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<s:content>
    <s:section class="floatSection">
        <s:sectionHeader icon="training-icon" code="certificationClass.edit">
            <g:ctxmenu>
                <g:ctxmenuItem>
                    <g:link controller="certificationClass" action="create">
                        <g:inlineIcon class="edit-icon"/>
                        <g:ctxmenuLabel>
                            <g:message code="certificationClass.create"/>
                        </g:ctxmenuLabel>
                    </g:link>
                </g:ctxmenuItem>
            </g:ctxmenu>
        </s:sectionHeader>


        <div class="body">
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${certificationClassInstance}">
                <div class="errors">
                    <g:renderErrors bean="${certificationClassInstance}" as="list"/>
                </div>
            </g:hasErrors>
            <g:form method="post">
                <g:hiddenField name="id" value="${certificationClassInstance?.id}"/>
                <g:hiddenField name="version" value="${certificationClassInstance?.version}"/>
                <div class="dialog">
                    <table>
                        <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="certification"><g:message code="certificationClass.certification.label" default="Certification"/></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: certificationClassInstance, field: 'certification', 'errors')}">
                                <g:select name="certification.id" from="${scoutinghub.Certification.list()}" optionKey="id" value="${certificationClassInstance?.certification?.id}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="classDate"><g:message code="certificationClass.classDate.label" default="Class Date"/></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: certificationClassInstance, field: 'classDate', 'errors')}">
                                <g:datePicker name="classDate" precision="day" value="${certificationClassInstance?.classDate}"/>
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="time"><g:message code="certificationClass.time.label" default="Class Date"/></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: certificationClassInstance, field: 'time', 'errors')}">
                                <g:textField name="time" value="${certificationClassInstance?.time}"/>
                            </td>
                        </tr>

                        %{--<tr class="prop">--}%
                        %{--<td valign="top" class="name">--}%
                        %{--<label for="coordinator"><g:message code="certificationClass.coordinator.label" default="Coordinator" /></label>--}%
                        %{--</td>--}%
                        %{--<td valign="top" class="value ${hasErrors(bean: certificationClassInstance, field: 'coordinator', 'errors')}">--}%
                        %{--<g:select name="coordinator.id" from="${scoutinghub.Leader.list()}" optionKey="id" value="${certificationClassInstance?.coordinator?.id}"  />--}%
                        %{--</td>--}%
                        %{--</tr>--}%
                        %{----}%
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="location"><g:message code="certificationClass.location.label" default="Location"/></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: certificationClassInstance, field: 'location', 'errors')}">
                                <g:select name="location.id" from="${scoutinghub.Address.list()}" optionKey="id" value="${certificationClassInstance?.location?.id}"/>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                </div>

                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                         onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
                </div>
            </g:form>
        </div>

    </s:section>
</s:content>
</body>
</html>
