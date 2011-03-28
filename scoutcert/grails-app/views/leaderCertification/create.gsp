

<%@ page import="scoutcert.LeaderCertification" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'leaderCertification.label', default: 'LeaderCertification')}" />
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
            <g:hasErrors bean="${leaderCertificationInstance}">
            <div class="errors">
                <g:renderErrors bean="${leaderCertificationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="certification"><g:message code="leaderCertification.certification.label" default="Certification" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderCertificationInstance, field: 'certification', 'errors')}">
                                    <g:select name="certification.id" from="${scoutcert.Certification.list()}" optionKey="id" value="${leaderCertificationInstance?.certification?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateEarned"><g:message code="leaderCertification.dateEarned.label" default="Date Earned" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderCertificationInstance, field: 'dateEarned', 'errors')}">
                                    <g:datePicker name="dateEarned" precision="day" value="${leaderCertificationInstance?.dateEarned}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="leader"><g:message code="leaderCertification.leader.label" default="Leader" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: leaderCertificationInstance, field: 'leader', 'errors')}">
                                    <g:select name="leader.id" from="${scoutcert.Leader.list()}" optionKey="id" value="${leaderCertificationInstance?.leader?.id}"  />
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
