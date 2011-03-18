<%@ page contentType="text/html" %>

<html>
<head>
    <link rel="stylesheet" href="${resource(absolute: true, dir: 'js/jquery-ui-1.8.4.custom/css/custom-theme', file: 'jquery-ui-1.8.10.custom.css')}"/>
    <link rel="stylesheet" href="${resource(absolute: true, dir: 'css', file: 'bsa.css')}"/>

</head>
<body>

<div class="mainBody">
    <div id="outer">

        <table height="100%" class="ms-siteaction" cellpadding="0" cellspacing="0">
            <tbody><tr>
                <td class="ms-siteactionsmenu" id="siteactiontd">

                </td>
            </tr>
            </tbody></table>

        <div>
            <div id="logo">

                <table cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td><g:link controller="leader" action="index"><img alt="Boy Scouts of America" src="${resource(absolute: true, dir: "images", file: "BsaLogoOriginal.jpg")}"/></g:link></td>
                        <td align="right">
                            <img alt="Grand Canyon Council" src="${resource(absolute: true, dir: "images", file: "010-GrandCanyon.jpg")}" style="border-width:0px;">
                        </td>
                    </tr>
                </table>

            </div>

        </div>
        <div class="clear">
        </div>



        <div id="content">

            <h1>This email contains a code to help us verify your email address.  Please copy and paste the
            code below into the appropriate box to continue.</h1>

            %{--<h3 style="margin-bottom:0px; ">Click the Following Link:</h3>--}%
            %{--<a href="${grailsApplication.config.grails.serverURL}/login/emailVerify?code=${hash}">${grailsApplication.config.grails.serverURL}/login/emailVerify?code=${hash}</a>--}%

            <h3 style="margin-bottom:0px;margin-top:20px">Confirmation Code:</h3>
            <big>${hash}</big>

            <p style="margin-top:20px">
                Thanks,<br/>
                ScoutCert
            </p>
        </div>

    </div>
</div>
</body></html>