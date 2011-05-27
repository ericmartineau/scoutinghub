<g:form class="dialog-form" controller="certificationClass" action="${registered ? 'processUnRegister' : 'processRegister'}">
    <s:content>
        <s:section class="propTable">
            <g:if test="${registered}">
                <s:msg type="info" code="certificationClass.alreadyRegistered.header" code2="certificationClass.alreadyRegistered.message" />
            </g:if>
            <g:hiddenField name="id" value="${certificationClass?.id}"/>
            <g:hiddenField name="leaderId" value="${leader?.id}"/>

            <s:property code="Registrant">
                ${leader.firstName} ${leader.lastName}
            </s:property>

            <s:property code="Class">
                ${certificationClass.certification.name}
            </s:property>
            <s:property code="Date">
                <g:formatDate date="${certificationClass.classDate}" format="EEEE MMM d"/>
            </s:property>
            <s:property code="Time">
                ${certificationClass.time}
            </s:property>
            <s:property code="Location">
                ${certificationClass.location.locationName}
            %{--<s:address address="${certificationClass.location}"/>--}%
            </s:property>

            <s:property code="Click For Map">
                <s:mapLink address="${certificationClass.location}"/>
            </s:property>

            <div class="buttons">
            <g:if test="${registered}">
                <g:submitButton name="unregister" value="Unregister" />
            </g:if>
            <g:else>
                <g:submitButton name="register" value="Register Now" />
            </g:else>
            </div>

        </s:section>
    </s:content>
</g:form>