<g:form class="dialog-form" action="processUnRegister" id="${certificationClass.id}">

    <g:hiddenField name="leaderId" value="${leader.id}" />


    <g:message code="certificationClass.confirmUnregister.message" args="[leader, certificationClass.certification.name, formatDate(date:certificationClass.classDate, format: 'MM-dd-yyyy')]"/>

    <div class="buttons">
        <g:submitButton name="process" value="${message(code:'Process')}"/>
    </div>
    </g:form>