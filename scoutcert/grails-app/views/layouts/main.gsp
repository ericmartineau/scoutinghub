<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="ROBOTS" content="NOHTMLINDEX">

    <title><g:layoutTitle/></title>

    <g:javascript library="jquery-min"/>

    <g:javascript library="jquery.cookie"/>
    <g:javascript library="jquery.hotkeys"/>
    <g:javascript library="jquery-ui-1.8.4.custom/js/jquery-ui-1.8.4.custom.min"/>

    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui-1.8.4.custom/css/custom-theme', file: 'jquery-ui-1.8.10.custom.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bsa.css')}"/>


    <g:javascript library="application"/>
    <g:layoutHead/>
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
                        <td><g:link controller="leader" action="index"><img src="/scoutcert/images/BsaLogoOriginal.jpg" style="border-width:0px;"></g:link></td>
                        <td align="right">
                            <img src="/scoutcert/images/010-GrandCanyon.jpg" style="border-width:0px;">
                        </td>
                    </tr>
                </table>

            </div>

        </div>
        <div class="clear">
        </div>

        <g:menu/>

        <div id="content">
            <g:layoutBody/>
        </div>
        <div style="clear: both">
        </div>
        <div id="bsafooter" style="padding-left: 20px">
            <img src="/scoutcert/images/footerbsa.gif"/>

        </div>
    </div>
</div>

</body></html>
