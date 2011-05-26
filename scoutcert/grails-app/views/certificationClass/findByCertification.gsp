<g:if test="${availableClasses}">
    <g:if test="${availableClasses?.size() > 0}">
    %{--<img src="../images/star.png" width="16" style="float:left"/>--}%
    %{----}%
    %{--<div class="register-header">--}%
    %{--<g:message code="certificationClass.findByCertification.available"/>--}%
    %{--</div>--}%
        <g:each in="${availableClasses}" var="certificationClass">
            <div class="training-class">
                <g:formatDate date="${certificationClass?.classDate}"
                              format="EEEE MMM d"/> at ${certificationClass?.location?.locationName}

                <g:link class="ui-button register-for-training" controller="certificationClass" action="register"
                        params="${['leaderId': params.leaderId, 'id': certificationClass?.id]}">
                    <g:message code="certificationClass.registerNow"/>

                </g:link>

            </div>
        </g:each>

    </g:if>
</g:if>

<g:if test="${registeredFor}">

%{--<img src="/scoutinghub/images/next.png" width="32" style="float:left"/>--}%

%{--<div class="profileData msg1 registerHeader">--}%
%{--<g:message code="certificationClass.findByCertification.registered"/>--}%
%{--<div style="font-size:10px">Don't forget to attend</div>--}%
%{--</div>--}%

    <g:set value="${registeredFor}" var="certificationClass"/>
    <div class="training-class">
        <g:formatDate date="${certificationClass?.classDate}"
                      format="EEEE MMM d"/> at ${certificationClass?.location?.locationName}
        <g:link class="ui-button register-for-training" controller="certificationClass" action="register"
                params="${['leaderId': params.leaderId, 'id': certificationClass?.id]}">
            <g:message code="certificationClass.viewDetails"/>

        </g:link>

    </div>

</g:if>

