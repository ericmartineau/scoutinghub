<head>
    <meta name='layout' content='main'/>
    <title><g:message code="title.linkaccount"/></title>
    <script type="text/javascript">
        function linkAccount() {
            jQuery("#linkToAccount").hide();
            jQuery("#createAccount").show();
        }
    </script>
</head>

<body>

<div class='body'>

    <g:header><g:message code="linkaccount.searchaccount"/></g:header>

    <table width="100%" id='linkToAccount'>
        <tr>
            <td width="50%" valign="top" align="center">
                <g:link class="ui-button" action="linkAccount"><g:message code="label.haveExistingAccount"/></g:link>
            </td>
            <td width="50%" valign="top" align="center">
                <g:link controller="login" action="createAccount" class="ui-button"><g:message code="label.needToLinkAccount"/></g:link>
            </td>
        </tr>
    </table>




    %{--<g:if test='${openId}'>--}%
    %{--Or if you're already a user you can <g:link action='linkAccount'>link this OpenID</g:link> to your account<br/>--}%
    %{--<br/>--}%
    %{--</g:if>--}%

</div>

</body>
