<html>
<head>
    <title><g:message code="permissions.index.title"/></title>
    <meta name="layout" content="main"/>
    <g:javascript library="jquery.ba-throttle-debounce.min"/>
    <script type="text/javascript">
        function leaderQuery() {
            var searchTerm = jQuery("#leaderQuery").val();
            jQuery("#searchResults").load("/scoutcert/permissions/leaderQuery", {leaderQuery:searchTerm});
        }

        function togglePermission(checkbox, leaderid, roleid) {
            jQuery.ajax({
                    url:"/scoutcert/permissions/setPermission",
                    data: {checked: checkbox.checked, leaderId:leaderid, roleId: roleid}
            });
        }

        jQuery(document).ready(function() {
            jQuery("#leaderQuery").keypress(jQuery.throttle(250, leaderQuery)).focus();
        });

    </script>

</head>

<body>

<div class="fldContainer">
    <label class="biglabel"><g:message code="permissions.index.selectLeader"/></label>
    <table cellpadding="0" cellspacing="0">
        <tr>
            <td><g:textField name="leaderQuery" id="leaderQuery" class="loginForm ui-corner-all"/></td>
            <td><a href="javascript:leaderQuery()" class="ui-button"><g:message code="permission.index.searchButton"/></a></td>
        </tr>
    </table>

</div>

<div class="fldContainer" id="searchResults">
</div>

</body>
</html>