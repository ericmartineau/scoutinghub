

<%@ page import="scoutinghub.meritbadge.MeritBadgeCounselor" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${meritBadgeCounselorInstance}">
            <div class="errors">
                <g:renderErrors bean="${meritBadgeCounselorInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="originalCertificationDate"><g:message code="meritBadgeCounselor.originalCertificationDate.label" default="Original Certification Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meritBadgeCounselorInstance, field: 'originalCertificationDate', 'errors')}">
                                    <g:datePicker name="originalCertificationDate" precision="day" value="${meritBadgeCounselorInstance?.originalCertificationDate}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="recertificationDate"><g:message code="meritBadgeCounselor.recertificationDate.label" default="Recertification Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meritBadgeCounselorInstance, field: 'recertificationDate', 'errors')}">
                                    <g:datePicker name="recertificationDate" precision="day" value="${meritBadgeCounselorInstance?.recertificationDate}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="leader"><g:message code="meritBadgeCounselor.leader.label" default="Leader" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meritBadgeCounselorInstance, field: 'leader', 'errors')}">
                                    <g:select name="leader.id" from="${scoutinghub.Leader.list()}" optionKey="id" value="${meritBadgeCounselorInstance?.leader?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
