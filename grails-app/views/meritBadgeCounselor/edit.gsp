<%@ page import="scoutinghub.meritbadge.MeritBadge; scoutinghub.meritbadge.MeritBadgeCounselor" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<g:form method="post">
    <g:hiddenField name="id" value="${meritBadgeCounselorInstance?.id}"/>
    <g:hiddenField name="version" value="${meritBadgeCounselorInstance?.version}"/>
    <s:content>
        <g:hasErrors bean="${flash.leaderError}">
            <s:msg type="error">
                <g:renderErrors bean="${flash.leaderError}"/>
            </s:msg>
        </g:hasErrors>

        <s:section class="floatSection">
            <s:sectionHeader icon="profile-icon" code="meritBadgeCounselor.editProfile"/>

            <s:propertyList class="edit-profile">
                <s:div class="alternate-color">
                    <s:dateField type="text" code="meritBadgeCounselor.originalCertificationDate.label" name="originalCertificationDate" value="${meritBadgeCounselorInstance.originalCertificationDate}" />
                    <s:dateField type="text" code="meritBadgeCounselor.recertificationDate.label" name="recertificationDate" value="${meritBadgeCounselorInstance?.recertificationDate}"/>
                </s:div>

                <s:div>
                <s:property class="full-width-property">
                    <g:each in="${MeritBadge.listOrderByName()}" var="meritBadge">
                        <div class="merit-badge-checkbox">
                        <g:checkBox id="mb${meritBadge.id}" name="badges" value="${meritBadge.id}"
                                    checked="${meritBadgeCounselorInstance?.badges?.contains(meritBadge)}"/> <label
                            for="mb${meritBadge.id}">${meritBadge.name}</label>
                        </div>

                    </g:each>
                </s:property>

                </s:div>


                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                         onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
                </div>
            </s:propertyList>
        </s:section>

    </s:content>
</g:form>

</body>
</html>
