<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler" %>
<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="training.importStatus.title"/></title>
    <script type="text/javascript">
        jQuery(document).ready(refreshStatus);

        function refreshStatus() {
            jQuery.getJSON("/scoutcert/training/importStatusPoll", "", function(json) {
                for (i = 0; i < json.sheets.length; i++) {
                    var sheet = json.sheets[i];
                    var statusDiv = jQuery("#status" + sheet.index);
                    if (statusDiv.length == 0) {
                        window.location.reload()
                    }
                    var value = 100 * sheet.totalComplete / sheet.totalToImport

                    var html = sheet.totalComplete + "/" + sheet.totalToImport
                    if (sheet.totalErrors > 0) {
                        html += "&nbsp;&nbsp;<a href='javascript:showErrors(" + i + ")'>(" + sheet.totalErrors + " errors)</a>"

                    }
                    if (sheet.importStatus == "Processing") {
                        statusDiv.progressbar(({ value: value }));
                        statusDiv.find(".statustext").html(html)
                    } else if (sheet.importStatus == "Complete") {
                        statusDiv.progressbar(({value:100}));
                        statusDiv.find(".statustext").html(html)
                    } else if (sheet.importStatus == "Waiting") {
                        statusDiv.progressbar(({value:0}));
                        statusDiv.find(".statustext").html("Waiting...")
                    }
                }
                if (json.alive) {
                    setTimeout(refreshStatus, 100)
                }
            });
        }

        function showErrors(sheetIndex) {
            jQuery("#errDialog").remove();
            jQuery("<div id='errDialog'></div>").load("/scoutcert/training/getImportErrors", {sheetIndex:sheetIndex}, function() {
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
                            <g:if test="${!session?.importJob?.alive}">
                                <g:msgbox type="info">
                                    <div class="msg1"><g:message code="training.importStatus.importComplete"/></div>
                                </g:msgbox>
                            </g:if>

                            <g:if test="${flash.message}">
                                <g:msgbox type="error" code="${flash.message}"/>
                            </g:if>
                            <h2><g:message code="training.importStatus.header"/></h2>
                            <g:if test="${session?.importJob?.sheetsToImport}">
                                <table>

                                    <g:each in="${session?.importJob?.sheetsToImport}" var="importSheet">
                                        <tr>
                                            <td>${importSheet.sheetName}</td>
                                            <td><div style="width:200px" id="status${importSheet.index}" class="progressbar">
                                                <span class="statustext" style="position:absolute; margin-left:10px; margin-top:5px"></span>
                                            </div></td>
                                        </tr>

                                    </g:each>

                                </table>

                            </g:if>

                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

</div>

</body>
</html>