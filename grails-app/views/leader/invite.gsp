<s:content>
    <g:if test="${leader.email == null}">
        <s:msg type="warning" code="leader.invite.noemail" />
    </g:if>
    <g:else>
        <s:text>
            <g:message code="leader.invite.message" args="[leader.toString(), leader.email]"/>
        </s:text>

        <g:form class="dialog-form" name="leader" action="sendInvite">
            <s:section>

                <g:hiddenField name="id" value="${leader?.id}"/>
                <s:div class="buttons">
                    <s:submit name="send" value="${message(code:'leader.invite.send')}"/>
                </s:div>
            </s:section>
        </g:form>
    </g:else>

</s:content>
