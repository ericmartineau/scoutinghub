<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<g:if test="${flash.error}">
    <g:msgbox type="error" code="${flash.error}" code2="${flash.error2}" />
</g:if>

<g:if test="${flash.info}">
    <g:msgbox type="info" code="${flash.info}" code2="${flash.info2}" />
</g:if>


<g:hasErrors bean="${flash.errorObj}">
    <g:msgbox type="error">
        <g:renderErrors bean="${flash.errorObj}" as="list"/>
    </g:msgbox>
</g:hasErrors>