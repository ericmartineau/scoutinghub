<html>
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="flow.verifyEmail.title"/></title>
</head>

<body>

<s:content>

    <g:form action="accountLink">
        <s:section header="small" code="flow.verifyEmail.title">
            <s:msg type="info" code="flow.verifyEmail.message"/>

            <g:if test="${verifyError}">
                <s:msg type="error" code="${verifyError}"/>
            </g:if>

            <s:bigTextField name="code" placeholder="${message(code:'flow.verifyEmail.code')}" code="${message(code:'flow.verifyEmail.code')}"  />
            <s:submit name="processVerifyEmail" class="ui-button" value="${message(code: 'flow.verifyEmail.button')}"/>


        </s:section>
    </g:form>

</s:content>


</body>
</html>
