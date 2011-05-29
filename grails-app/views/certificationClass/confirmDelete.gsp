<g:form class="lightbox" controller="certificationClass" action="delete" id="${certificationClass.id}">
    <s:msg code="certificationClass.delete.message1" type="warning" />
    <g:message code="certificationClass.delete.message"/>
    <div class="buttons">
        <g:submitButton name="proceed" value="${message(code:'Proceed')}"/>
    </div>
</g:form>