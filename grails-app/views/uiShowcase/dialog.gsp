<g:form class="dialog-form" action="processDialog">
    <s:content>
        <s:section code="uiShowcase.dialog.header">
            <s:text>
                If your dialog needs to execute some code, you can simply add the "dialog-form" class to the
                form tag.  This will convert the form submission to be ajaxified.  You need to make the processing page return JSON (see
                the "processConfirmation" action in the UiShowcaseController for an example).  You can test error scenarios below with
                the checkbox/toggle switch.
            </s:text>

            <s:checkbox class="padded" name="submitWithError" code="Click this to force an error"/>
            <s:submit name="submitWithoutError" value="Submit"/>
        </s:section>

    </s:content>
</g:form>