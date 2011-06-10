<html>
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="flow.suggestSocialLoginSuccess.title"/></title>
</head>

<body>

<s:content>
    <s:section>
        <s:sectionHeader><g:message code="flow.suggestSocialLoginSuccess.header" args="[provider]"/></s:sectionHeader>
        <s:text><g:message code="flow.suggestSocialLoginSuccess.msg1" args="[provider]"/><img align="top" src="/scoutinghub/images/${provider?.toLowerCase()}-logo-square.png" />
            %{--<g:message code="flow.suggestSocialLoginSuccess.msg2" />--}%
        </s:text>

        <s:div style="margin-top:30px">
            <s:linker controller="leader" action="index"><g:message code="flow.suggestSocialLoginSuccess.goToProfile"/></s:linker>
        </s:div>

    </s:section>
</s:content>



</body>
</html>