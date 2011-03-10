<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- saved from url=(0056)https://myscouting.scouting.org/Pages/TrainingTools.aspx -->
<html><head>
    <g:layoutHead/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="ROBOTS" content="NOHTMLINDEX">

    <g:javascript library="jquery-min"/>

    <g:javascript library="jquery.cookie"/>
    <g:javascript library="jquery.hotkeys"/>
    <g:javascript library="jquery-ui-1.8.4.custom/js/jquery-ui-1.8.4.custom.min"/>

    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui-1.8.4.custom/css/custom-theme', file: 'jquery-ui-1.8.10.custom.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bsa.css')}"/>

    <g:javascript library="application"/>


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

        <div id="top">
            <div id="logo">

                <table cellpadding="0" cellspacing="0">
                    <tbody><tr>
                        <td><img id="ctl00_HeaderControl_BSALogoImage" src="/scoutcert/images/BsaLogoOriginal.jpg" style="border-width:0px;"></td>
                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>
                            <img id="ctl00_HeaderControl_BSAPatchImage" src="/scoutcert/images/010-GrandCanyon.jpg" style="border-width:0px;">
                        </td>
                    </tr>
                    </tbody></table>

            </div>

        </div>
        <div id="top-nav">
            <!------------- Top Navigation Menu --------------------------------->

        </div>
        <div class="clear">
        </div>
        <div id="navigation">

            <ul>
                <sec:ifAnyGranted roles="ROLE_LEADER">
                    <li class="on"><a href="">My Profile</a></li>
                    <li><a href="">Training</a></li>
                </sec:ifAnyGranted>
                <sec:ifAnyGranted roles="ROLE_UNITADMIN">
                    <li><a href="">Unit Tools</a></li>
                    <li><a href="">Reports</a></li>
                </sec:ifAnyGranted>
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <li><a href="">Setup</a></li>
                </sec:ifAnyGranted>

                <sec:ifLoggedIn>
                    <li><a href="/scoutcert/logout/">Log Out</a></li>
                </sec:ifLoggedIn>

            </ul>
            <div>
            </div>
        </div>
        <div id="content">
            <g:layoutBody/>
        </div>
        <div style="clear: both">
        </div>
        <div id="bsafooter" style="padding-left: 20px">
            <img src="/scoutcert/images/footerbsa.gif" />


        </div>
    </div>
</div>

</body></html>
