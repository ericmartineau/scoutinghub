<%@ page import="scoutinghub.CertificationClass" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="${layoutName}"/>
    <g:set var="entityName" value="${message(code: 'certificationClass.label', default: 'CertificationClass')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<s:content class="floatContainer">
    <s:section class="floatSection profile">
        <g:if test="${flash.message}">
            <s:msg code="${flash.message}" type="info"/>
        </g:if>

        <s:sectionHeader icon="training-icon" code="certificationClass.show">
            <g:ctxmenu>
                <g:ctxmenuItem>
                    <g:link controller="certificationClass" action="edit" id="${certificationClassInstance.id}">
                        <g:inlineIcon class="edit-icon"/>
                        <g:ctxmenuLabel>
                            <g:message code="certificationClass.editThis"/>
                        </g:ctxmenuLabel>
                    </g:link>
                </g:ctxmenuItem>

                <g:ctxmenuItem>
                    <g:link class="lightbox" title="${message(code:'certificationClass.delete')}" controller="certificationClass" action="confirmDelete" id="${certificationClassInstance.id}">
                        <g:inlineIcon class="delete-icon"/>
                        <g:ctxmenuLabel>
                            <g:message code="certificationClass.deleteThis"/>
                        </g:ctxmenuLabel>
                    </g:link>
                </g:ctxmenuItem>

            </g:ctxmenu>
        </s:sectionHeader>

        <s:propertyList>
            <s:div class="alternate-color prop-container">
                <s:property code="certificationClass.certification.label">${certificationClassInstance?.certification?.encodeAsHTML()}</s:property>
                <s:property code="certificationClass.location.label">${certificationClassInstance?.location?.encodeAsHTML()}</s:property>

            </s:div>

            <s:div class="prop-container">
                <s:property code="certificationClass.classDate.label"><g:formatDate date="${certificationClassInstance?.classDate}" format="MM-dd-yyyy"/></s:property>
                <s:property code="certificationClass.time.label">${certificationClassInstance?.time}</s:property>
            </s:div>

        </s:propertyList>

    </s:section>

    <s:section class="floatSection">
        <s:sectionHeader icon="profile-icon" code="certificationClass.registrants"/>

        <div class="list">
            <table width="100%">
                <thead>
                <tr>
                    <th>Leader</th>
                    <th>Unit</th>
                    <th>Position</th>
                    <th>BSA ID</th>
                    <th>View Profile</th>
                    <th>Unregister</th>
                </tr>
                </thead>
                <g:each in="${certificationClassInstance?.registrants}" var="leader">
                    <tr>
                        <td>${leader}</td>
                        <td>
                            <g:if test="${leader?.groups?.size() > 0}">
                                ${leader?.groups?.iterator()?.next()?.scoutGroup}
                            </g:if>
                        </td>
                        <td>
                            <g:if test="${leader?.groups?.size() > 0}">
                                <g:message code="${leader?.groups?.iterator()?.next()?.leaderPosition}.label" />
                            </g:if>
                        </td>

                        <td>
                            <g:if test="${leader?.myScoutingIds?.size() > 0}">
                                ${leader?.myScoutingIds?.iterator()?.next()?.myScoutingIdentifier}
                            </g:if>

                        </td>

                        <td align="center"><g:link controller="leader" action="view" id="${leader.id}"><g:message code="leader.viewProfile"/></g:link></td>
                        <td align="center">
                            <g:link controller="certificationClass" action="confirmUnregister" id="${certificationClassInstance.id}"
                                    params="[leaderId:leader.id]" class="lightbox" title="${message(code:'certificationClass.unregisterFromClass')}">
                                <g:message code="certificationClass.unregister"/>
                            </g:link></td>
                    </tr>

                </g:each>
            </table>
        </div>

    </s:section>

</s:content>
</body>
</html>
