<html>
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="flow.foundMultipleMatches.title"/></title>
</head>

<body>

<s:content>

    <g:form action="accountLink">

        <s:section header="small" code="flow.foundMultipleMatches.title">
            <s:msg type="warning" code="flow.foundMultipleMatches.message"/>
            <g:if test="${error}">
                <s:msg code="${error}"/>
            </g:if>

            <g:if test="${verifyError}">
                <s:msg type="error" code="${verifyError}"/>
            </g:if>

            <g:each in="${leaderSet}" var="leaderInSet">
                <s:pageItem type="radiobutton" name="${leaderInSet?.firstName} ${leaderInSet.lastName}">
                    <s:radioItem name="leaderId" value="${leaderInSet?.id}"/>
                </s:pageItem>
            </g:each>

        </s:section>

        <s:section>
            <s:pageItem type="radiobutton" name="${message(code:'label.noneOfThese')}">
                <s:radioItem name="leaderId" value="0" checked="${true}"/>
            </s:pageItem>
        %{--<div class="fldContainer">--}%
        %{--<g:radio name="leaderId" value="0" checked="${true}"/> <big></big>--}%
        %{--</div>--}%

        </s:section>

        <s:section header="small">
            <s:submit name="selectLeader" value="${message(code: 'flow.foundMultipleMatches.button')}"/>
        </s:section>
    </g:form>
</s:content>

</body>
</html>