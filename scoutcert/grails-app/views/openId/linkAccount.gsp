<head>
    <meta name='layout' content='main'/>
    <title><g:message code="title.linkaccount"/></title>
</head>

<body>

<div class='body'>

    <g:header><g:message code="header.linkAccount"/></g:header>
    <div class="explain" style="margin-bottom:20px"><g:message code="linkAccount.instructions"/></div>

    <g:hasErrors bean="${command}">
        <div class="errors">
            <g:renderErrors bean="${command}" as="list"/>
        </div>
    </g:hasErrors>

    <g:if test='${flash.error}'>
        <div class="errors"><g:message code="${flash.error}"/></div>
    </g:if>

    <g:if test='${flash.successMessage}'>
        ${flash.successMessage}
    </g:if>

    <g:else>

        <g:form action='linkAccount'>
            <div class="fldContainer">
                <label class="biglabel" for='username'><g:message code="label.username"/></label><br/>
                <g:textField class="loginForm ui-corner-all" name='username' value='${command?.username}'/>
            </div>

            <div class="fldContainer">
                <label class="biglabel" for='password'><g:message code="label.password"/></label><br/>
                <g:textField class="loginForm ui-corner-all" name='password' value='${command?.password}'/>
            </div>

            <div class="fldContainer">
                <input type="submit" class="ui-button" value="${message(code: 'label.linkAccount')}">
                <span style="margin-left: 20px">
                    <g:link action="createAccount"><g:message code="label.goback"/></g:link>
                </span>
            </div>

        </g:form>

    </g:else>

</div>

<script>
    (function() {
        document.getElementById('username').focus();
    })();
</script>

</body>
