<s:section>

    <g:if test="${flash.message}">
        <s:msg type="warn" code="${flash.error}" code2="${flash.error2}"/>
    </g:if>
    <g:if test="${flash.error}">
        <s:msg type="error" code="${flash.error}" code2="${flash.error2}"/>
    </g:if>

    <g:if test="${flash.info}">
        <s:msg type="info" code="${flash.info}" code2="${flash.info2}"/>
    </g:if>


    <g:hasErrors bean="${flash.errorObj}">
        <s:msg type="error">
            <g:renderErrors bean="${flash.errorObj}" as="list"/>
        </s:msg>
    </g:hasErrors>

</s:section>
