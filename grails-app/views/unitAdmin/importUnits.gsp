<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler" %>
<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="unitAdmin.importUnit.title"/></title>
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
                                    <div class="msg1"><g:message code="unitAdmin.importUnit.fileErrors" /></div>
                                    <g:each in="${flash.fileErrors}" var="error">
                                        <div class="msg2">- <g:message code="${error.code}" args="${error.data}" /></div>
                                    </g:each>
                                </g:msgbox>
                            </g:if>
                            <g:if test="${flash.message}">
                                <g:msgbox type="error" code="${flash.message}" />
                            </g:if>
                            <h2><g:message code="unitAdmin.importUnit.header"/></h2>

                            <g:uploadForm action="processImportUnits" enctype="multipart/form-data">
                                <div class="fldContainer">
                                    <g:message code="unitAdmin.importUnit.spreadsheet.label"/><br/>
                                    <input type="file" name="spreadsheet"/>
                                </div>

                                <div class="fldContainer">
                                    <g:submitButton class="ui-button" name="upload" value="${message(code:'unitAdmin.importUnit.submit')}"/>
                                </div>

                            </g:uploadForm>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

</div>

</body>
</html>