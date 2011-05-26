<html>
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="flow.selectUsernameAndPassword.title"/></title>
</head>

<body>

<s:content>
    <g:form action="accountLink">
        <s:section code="flow.selectUsernameAndPassword.title">
            <s:msg type="info" code="flow.selectUsernameAndPassword.message"/>

            <g:if test="${error}">
                <s:msg type="error" code="${error}"/>
            </g:if>

            <s:bigTextField name="username" code="${message(code:'label.username')}" placeholder="${message(code:'label.username')}"
                    value="${createAccount.username ?: createAccount.email}" />
            <s:bigTextField type="password" name="password" code="${message(code:'label.password')}" placeholder="${message(code:'label.password')}" />

            <s:bigTextField type="password" name="confirmPassword" code="${message(code:'label.confirmPassword')}" placeholder="${message(code:'label.confirmPassword')}" />

            <s:submit name="submitUsernameAndPassword" class="ui-button" value="${message(code: 'flow.selectUsernameAndPassword.button')}"/>

        </s:section>
    </g:form>

</s:content>




</body>
</html>
