<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler" %>
<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="training.importStatus.title"/></title>
    <script type="text/javascript">
        jQuery(document).ready(refreshStatus);

        function refreshStatus() {
            jQuery.getJSON("/scoutinghub/training/simpleImportStatusPoll", "", function(json) {

                var statusDiv = jQuery("#importStatus");
                if (statusDiv.length == 0) {
                    window.location.reload()
                }
                var value = 100 * json.totalCompleted / json.totalToImport

                var html = json.totalCompleted + "/" + json.totalToImport
//                if (sheet.totalErrors > 0) {
//                    html += "&nbsp;&nbsp;<a href='javascript:showErrors(" + i + ")'>(" + sheet.totalErrors + " errors)</a>"
%{----}%
//                }
                statusDiv.progressbar(({ value: value }));
                statusDiv.find(".statustext").html(html);

                if (json.alive) {
                    setTimeout(refreshStatus, 100)
                }
            });
        }

        function showErrors(sheetIndex) {
            jQuery("#errDialog").remove();
            jQuery("<div id='errDialog'></div>").load("/scoutinghub/training/getImportErrors", {sheetIndex:sheetIndex}, function() {
                jQuery(this).dialog({title:"Errors While Importing", height:300,width:500});
            });
        }

    </script>
</head>

<body>

<div class='body'>

    <table width="100%">
        <tr>
            <td align="center">
                <table>
                    <tr>
                        <td align="left">
                            <g:if test="${!session?.simpleImportJob?.alive}">
                                <g:msgbox type="info">
                                    <div class="msg1"><g:message code="training.importStatus.importComplete"/></div>
                                </g:msgbox>
                            </g:if>

                            <g:if test="${flash.message}">
                                <g:msgbox type="error" code="${flash.message}"/>
                            </g:if>
                            <h2><g:message code="training.importStatus.header"/></h2>

                            <div style="width:200px" id="importStatus" class="progressbar">
                                <span class="statustext" style="position:absolute; margin-left:10px; margin-top:5px" />
                            </div>

                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

</div>

</body>
</html>