<g:if test="${availableClasses}">
    <g:if test="${availableClasses?.size() > 0}">
        <g:each in="${availableClasses}" var="certificationClass">
            <div class="training-class">
                <g:formatDate date="${certificationClass?.classDate}"
                              format="EEEE MMM d"/> at ${certificationClass?.location?.locationName}

                <g:link controller="certificationClass" action="register" lbwidth="500" title="${message(code:'certificationClass.label')}" class="ui-button lightbox register-for-training"
                        params="${['leaderId': params.leaderId, 'id': certificationClass?.id]}">
                    <g:message code="certificationClass.registerNow"/>
                </g:link>

            </div>
        </g:each>

    </g:if>
</g:if>

<g:if test="${registeredFor}">
    <g:set value="${registeredFor}" var="certificationClass"/>
    <div class="training-class">
        <g:formatDate date="${certificationClass?.classDate}"
                      format="EEEE MMM d"/> at ${certificationClass?.location?.locationName}
        <g:link controller="certificationClass" action="register" lbwidth="500" title="${message(code:'certificationClass.label')}" class="ui-button lightbox register-for-training"
                params="${['leaderId': params.leaderId, 'id': certificationClass?.id]}">
            <g:message code="certificationClass.viewDetails"/>
        </g:link>

    </div>

</g:if>

