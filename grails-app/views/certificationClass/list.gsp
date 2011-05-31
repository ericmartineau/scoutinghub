<%@ page import="scoutinghub.CertificationClass" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="${layoutName}"/>
    <g:set var="entityName" value="${message(code: 'certificationClass.label', default: 'CertificationClass')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<s:content>
    <s:section>
        <g:if test="${flash.message}">
            <s:msg type="info" code="${flash.message}"/>
        </g:if>

        <s:sectionHeader icon="training-icon" code="certificationClass.list">
            <g:ctxmenu>
                <g:ctxmenuItem>
                    <g:link controller="certificationClass" action="create">
                        <g:inlineIcon class="edit-icon"/>
                        <g:ctxmenuLabel>
                            <g:message code="certificationClass.create"/>
                        </g:ctxmenuLabel>
                    </g:link>
                </g:ctxmenuItem>
            </g:ctxmenu>
        </s:sectionHeader>

        <div class="list">
            <table width="100%">
                <thead>
                <tr>

                    <g:sortableColumn property="id" title="${message(code: 'certificationClass.id.label', default: 'Id')}"/>

                    <th><g:message code="certificationClass.certification.label" default="Certification"/></th>

                    <g:sortableColumn property="classDate" title="${message(code: 'certificationClass.classDate.label', default: 'Class Date')}"/>

                    %{--<th><g:message code="certificationClass.coordinator.label" default="Coordinator" /></th>--}%

                    <th><g:message code="certificationClass.location.time" default="Time"/></th>
                    <th><g:message code="certificationClass.location.label" default="Location"/></th>

                </tr>
                </thead>
                <tbody>
                <g:each in="${certificationClassInstanceList}" status="i" var="certificationClassInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                        <td><g:link action="show" id="${certificationClassInstance.id}">${fieldValue(bean: certificationClassInstance, field: "id")}</g:link></td>

                        <td>${fieldValue(bean: certificationClassInstance, field: "certification")}</td>

                        <td><g:formatDate date="${certificationClassInstance.classDate}" format="MM-dd-yyyy"/></td>

                        %{--<td>${fieldValue(bean: certificationClassInstance, field: "coordinator")}</td>--}%

                        <td>${fieldValue(bean: certificationClassInstance, field: "time")}</td>
                        <td>${fieldValue(bean: certificationClassInstance, field: "location")}</td>

                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>

        <div class="paginateButtons">
            <g:paginate total="${certificationClassInstanceTotal}"/>
        </div>

    </s:section>
</s:content>

</body>
</html>
