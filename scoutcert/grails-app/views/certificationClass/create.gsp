<%@ page import="scoutinghub.CertificationClass" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'certificationClass.label', default: 'CertificationClass')}"/>
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
    <g:hasErrors bean="${certificationClassInstance}">
        <div class="errors">
            <g:renderErrors bean="${certificationClassInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save">
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
                        <label for="classDate"><g:message code="certificationClass.time.label" default="Time"/></label>
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

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="location"><g:message code="certificationClass.location.label" default="Location"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: certificationClassInstance, field: 'location', 'errors')}">
                        <g:select id="locationId" name="location.id" from="${scoutinghub.Address.list()}" optionKey="id" value="${certificationClassInstance?.location?.id}"/>
                        <a style="font-size:13px" href="javascript:addAddress('locationId')"><g:message code="address.create"/></a>
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
