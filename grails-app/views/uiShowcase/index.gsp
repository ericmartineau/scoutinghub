<html>
<head>
    <meta name="layout" content="${layoutName}"/>
    <title><g:message code="uiShowcase.title"/></title>
</head>

<body>

<s:content>
%{--If you have a simple header, you can simply add a code to the section tag--}%
    <s:section code="uiShowcase.header1">
        <s:text>
            If you have a simple header, you can simply add a message code to the section tag
        </s:text>
    </s:section>

    <s:section>
        <s:sectionHeader code="uiShowcase.header2" icon="units-icon">
            <s:ctxmenu>
                <g:ctxmenuItem>
                    <g:link style="white-space:nowrap;" action="index">
                        <g:inlineIcon class="edit-icon"/>
                        <g:ctxmenuLabel>
                            <g:message code="uiShowcase.ctxMenu1"/>
                        </g:ctxmenuLabel>
                    </g:link>
                </g:ctxmenuItem>

                <g:ctxmenuItem>
                    <g:link style="white-space:nowrap;" action="index">
                        <g:inlineIcon class="add-icon"/>
                        <g:ctxmenuLabel>
                            <g:message code="uiShowcase.ctxMenu2"/>
                        </g:ctxmenuLabel>
                    </g:link>
                </g:ctxmenuItem>
            </s:ctxmenu>

        </s:sectionHeader>
        <s:text>
            You can use our more advanced sectionHeader tag to add a context menu to your section.  The context menus
      don't currently show up for the iPhone.
        </s:text>

    </s:section>

    <s:section code="uiShowcase.msgSection">
        <s:msg code="uiShowcase.msgError" code2="uiShowcase.msgError2" type="error"/>
        <s:msg code="uiShowcase.msgInfo" type="info"/>
        <s:msg type="warning" code="uiShowcase.msgWarning" code2="You could pass the actual message here - maybe an exception message - but you really should use the i18n bundles"/>
    </s:section>

    <s:section code="uiShowcase.propertyList">
        <s:propertyList>
            <s:div class="alternate-color">
                <s:property code="uiShowcase.prop1.label">Bobert</s:property>
                <s:property code="uiShowcase.prop2.label">Jones</s:property>
            </s:div>

            <s:div>
                <s:property code="uiShowcase.prop3.label"><g:formatDate date="${new Date()}" format="MM-dd-yyyy"/></s:property>
                <s:property code="uiShowcase.prop4.label">Bob</s:property>
            </s:div>

        </s:propertyList>
    </s:section>


    <g:form controller="uiShowcase" action="index">
        <s:section>

            <s:sectionHeader code="uiShowcase.formSection" icon="profile-icon"/>
            <s:bigTextField name="field1" code="uiShowcase.prop1.label" value="${params.field1}"/>
            <s:bigTextField name="field2" code="uiShowcase.prop2.label" value="${params.field2}"/>
            <s:bigTextField class="datePicker" name="field3" code="uiShowcase.prop3.label" value="${params.field3}"/>
            <s:bigTextField name="field4" code="uiShowcase.prop4.label" value="${params.field4}"/>
            <s:submit name="submit" value="Submit"/>
        </s:section>
    </g:form>

    <g:form controller="uiShowcase" action="index">
        <s:section>
            <s:sectionHeader code="uiShowcase.propertyFormSection" icon="training-icon"/>
            <s:propertyList>
                <s:div class="alternate-color">

                    <s:textField name="field1" code="uiShowcase.prop1.label" value="${params.field1}"/>
                    <s:textField name="field2" code="uiShowcase.prop2.label" value="${params.field2}"/>
                </s:div>

                <s:div>
                    <s:textField class="datePicker" name="field3" code="uiShowcase.prop3.label" value="${params.field3}"/>
                    <s:textField name="field4" code="uiShowcase.prop4.label" value="${params.field4}"/>
                </s:div>
            </s:propertyList>
            <s:submit name="submit" value="Submit"/>
        </s:section>
    </g:form>

    <s:section code="uiShowcase.ajaxDialogSection">
        <s:text>Sometimes you will want to pop up a dialog box: one that might contain a confirmation or even a form.  This is an interaction
            that is more difficult to make consistent for a mobile device and browser.  The code for this example can also be found in
            UIShowcaseController.groovy and grails-app/views/uiShowcase/dialog.gsp
        </s:text>
        <s:text>

            <p>The basic concept is this:</p>
            <ol class="list">
                <li>Create a link where the href is a controller/action that contains your lightbox content.</li>
                <li>
                    Add a css class of "lightbox" to the link, which will indicate that you want the link opened in a
                    lightbox instead on navigating directly to the page.
                </li>
                <li>When you create the gsp for the lightbox content, create it as a fragment (leave out the html and body tags)</li>
            </ol>

        </s:text>
        <s:propertyList>
        %{--The title attribute of the link will get copied to the lightbox title--}%
            <s:linker img="training-current" controller="uiShowcase" action="dialog" class="ui-button lightbox" title="${message(code:'uiShowcase.dialogTitle')}">
                <g:message code="uiShowcase.linkTitle"/>
            </s:linker>
        </s:propertyList>

    </s:section>

</s:content>
</body>
</html>