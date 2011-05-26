<html>
<head>
    <meta content="iwebkit" name="layout"/>
</head>
<body>
<content tag="leftNavigation">
    <iwebkit:leftnavigation navtype="arrow">
        <iwebkit:navelement  content="Profile" controller="leader" action="view" id="${leaderCertification?.leader?.id}" />
    </iwebkit:leftnavigation>
</content>
<g:form action="${postAction}">
    <g:hiddenField name="leaderCertification.id" value="${leaderCertification?.id}"/>
    <g:hiddenField name="leader.id" value="${params['leader.id']}"/>
    <g:hiddenField name="certification.id" value="${params['certification.id']}"/>
    <s:content>

        <g:if test="${flash.error}">
            <s:section code="leaderCertification.create.error">
                <s:msg code="${flash.error}" type="error" />
            </s:section>
        </g:if>

        <s:section code="${leaderCertification.certification.name}">
        %{--<g:message code="leaderCertification.certification.label"/>--}%

            <s:text>
                <g:message code="leaderCertification.create.message"/>
                <ul>
                    <li><g:message code="leaderCertification.create.message1"/></li>
                    <li><g:message code="leaderCertification.create.message2"/></li>
                </ul>
            </s:text>

        </s:section>

        <s:section code="leaderCertification.dateEarned.label">
            <s:bigTextField placeholder="mm/dd/YYYY" name="dateEarned"/>
        </s:section>

        <s:section>

            <iwebkit:submit />

        %{--<s:property code="leaderCertification.dateEarned.label">--}%
        %{----}%
        %{--</s:property>--}%

        </s:section>
    </s:content>

</g:form>
</body>
</html>
