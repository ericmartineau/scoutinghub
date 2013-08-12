<%@ page import="scoutinghub.meritbadge.MeritBadge; scoutinghub.meritbadge.MeritBadgeCounselor" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="${layoutName}"/>
    <g:set var="entityName" value="${message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
    <r:require module="meritBadgeCounselor"/>
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
            <s:sectionHeader icon="user" code="meritBadgeCounselor.editProfile"/>

            <s:propertyList class="edit-profile row-fluid">

                <div class="span8">
                    <s:textField type="text" code="meritBadgeCounselor.originalCertificationDate.label"
                                 name="originalCertificationDate"
                                 value="${meritBadgeCounselorInstance.originalCertificationDate}"/>
                    <s:textField type="text" code="meritBadgeCounselor.recertificationDate.label"
                                 name="recertificationDate"
                                 value="${meritBadgeCounselorInstance?.recertificationDate}"/>
                </div>

            </s:propertyList>



                <s:propertyList class="thumbnails full-width-property">
                    <g:each in="${MeritBadge.listOrderByName()}" var="meritBadge">
                        <div class="span3">
                            <s:checkbox code="${meritBadge.name}" id="mb${meritBadge.id}" name="badges"
                                        value="${meritBadge.id}"
                                        checked="${meritBadgeCounselorInstance?.badges?.contains(meritBadge)}"/>
                        </div>
                    </g:each>
                </s:propertyList>



                <div class="buttons">
                    <g:actionSubmit class="btn btn-primary" action="update"
                                                         value="${message(code: 'default.button.update.label', default: 'Update')}"/>
                    <g:actionSubmit class="btn btn-danger" action="delete"
                                                         value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                         onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                </div>

        </s:section>

    </s:content>
</g:form>

</body>
</html>
