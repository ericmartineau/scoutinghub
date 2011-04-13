<g:if test="${availableClasses}">
    <g:if test="${availableClasses?.size() > 0}">
        <img src="/scoutcert/images/next.png" width="32" style="float:left"/>
        <div class="profileData msg1 registerHeader">
            <g:message code="certificationClass.findByCertification.available"/>
            <div style="font-size:10px">(Click a link for more info)</div>
        </div>
        <g:each in="${availableClasses}" var="certificationClass">
            <div class="trainingClass">
                <g:link class="registerForTraining" controller="certificationClass" action="register" params="${['leaderId': params.leaderId, 'id': certificationClass?.id]}">
                    <g:formatDate date="${certificationClass?.classDate}" format="EEEE MMM d"/> at ${certificationClass?.location?.locationName}
                </g:link>

            </div>
        </g:each>

    </g:if>
</g:if>

<g:if test="${registeredFor}">

    <img src="/scoutcert/images/next.png" width="32" style="float:left"/>
    <div class="profileData msg1 registerHeader">
        <g:message code="certificationClass.findByCertification.registered"/>
        <div style="font-size:10px">Don't forget to attend</div>
    </div>

    <g:set value="${registeredFor}" var="certificationClass"/>
    <div class="trainingClass">
        <g:link class="registerForTraining" controller="certificationClass" action="register" params="${['leaderId': params.leaderId, 'id': certificationClass?.id]}">
            <g:formatDate date="${certificationClass?.classDate}" format="EEEE MMM d"/> at ${certificationClass?.location?.locationName}
        </g:link>

    </div>

</g:if>

