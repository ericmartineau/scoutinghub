<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler" %>
<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="title.importTraining.title"/></title>
</head>

<body>

<div class='body'>

    <table width="100%">
        <tr>
            <td align="center">
                <table>
                    <tr>
                        <td align="left">
                            <g:if test="${flash.fileErrors}">
                                <g:msgbox type="error">
                                    <div class="msg1"><g:message code="training.importTraining.fileErrors"/></div>
                                    <g:each in="${flash.fileErrors}" var="error">
                                        <div class="msg2">- <g:message code="${error.code}" args="${error.data}"/></div>
                                    </g:each>
                                </g:msgbox>
                            </g:if>
                            <g:if test="${flash.message}">
                                <g:msgbox type="error" code="${flash.message}"/>
                            </g:if>
                            <h2><g:message code="training.importTraining.header"/></h2>

                            <table>
                                <tr>
                                    <th>Scout ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Unit #</th>
                                </tr>

                                <g:each in="${importedRecords}" var="record">
                                    <tr>
                                        <td>${record.scoutingId}</td>
                                        <td>${record.firstName} ${record.lastName}</td>
                                        <td>${record.email ?: "Not Available"}</td>
                                        <td>${record.unitNumber}</td>
                                    </tr>
                                </g:each>
                            </table>

                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

</div>

</body>
</html>