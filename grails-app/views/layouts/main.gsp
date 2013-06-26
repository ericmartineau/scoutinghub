%{--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">--}%
<!DOCTYPE html>
<html>
<head>
    <link rel="SHORTCUT ICON" href="/scoutinghub/favicon.ico" />

    <link rel="stylesheet" type="text/css" href="/scoutinghub/css/reset-min.css">
    %{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'vanilla.css')}"/>--}%


    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="ROBOTS" content="NOHTMLINDEX">

    <title><g:layoutTitle/></title>

    %{--<g:javascript library="jquery-min"/>--}%
    <g:javascript library="jquery-1.5.1.min"/>


    <g:javascript library="jquery.cookie"/>
    <g:javascript library="jquery.hotkeys"/>
    <g:javascript library="jquery-ui-1.8.11.custom/development-bundle/ui/jquery-ui-1.8.11.custom"/>
    %{--<g:javascript library="jquery.selectBox-1.0.1/jquery.selectBox.min"/>--}%
    <g:javascript library="jquery.selectbox"/>
    <g:javascript library="jquery.qtip.min"/>

    <g:javascript library="jquery.ba-throttle-debounce.min"/>
    <g:javascript library="bsa"/>
    <g:javascript library="bsa-unit-autocomplete"/>
    %{--<g:javascript library="dropCurves.min"/>--}%

    <g:javascript library="prototype"/>

    <script src="http://maps.google.com/maps?file=api&v=2&sensor=false&key=${grailsApplication.config.google.key}" type="text/javascript"></script>


    <link rel="stylesheet" href="${resource(dir: 'js/jquery.selectBox-1.0.1', file: 'jquery.selectBox.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui-1.8.11.custom/css/start', file: 'jquery-ui-1.8.11.custom.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bsa.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.qtip.min.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'js/themes/default', file: 'style.css')}"/>

    <g:javascript library="application"/>
    <g:layoutHead/>

    <style type="text/css">
    /*.jstree-default .jstree-clicked {*/
    /*background: #164184;*/
    /*color:#FFF;*/
    /*border: 1px solid #FFF;*/
    %{----}%
    /*}*/
    %{----}%
    /*.jstree-default .jstree-hovered {*/
    /*background: #164184;*/
    /*color:#FFF;*/
    /*border: 1px solid #FFF;*/
    %{----}%
    /*}*/

    .jstree a {
        font-size: 14px;
        padding: 4px;
    }

    </style>
</head>
<body>

<div style="display:none; width:800px; height:600px" id="mapper">&nbsp;</div>

<div class="mainBody">

    <table cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" style="padding:0px;margin:0px;">

                <div id="outer" class="shadow ui-corner-all">
                    <div id="logo">
                        <span class="logoBar"><g:link controller="leader" action="index">
                            <img src="/scoutinghub/images/full-header.jpg" border="0"/>

                        </g:link></span>
                        <span class="logoBar"><img src="/scoutinghub/images/010-GrandCanyon.jpg" style="border-width:0px;"></span>

                    </div>

                    <g:menu/>

                    <div id="content">
                        <table width="100%" cellpadding="0" cellspacing="0">
                            <tr>
                                <td align="center">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><g:layoutBody/></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="clear: both">
                    </div>
                    <div id="bsafooter" class="ui-corner-bl ui-corner-br" style="padding-left: 20px">
                        <table width="100%" cellpadding="0" cellspacing="0">
                            <tr>
                                <td valign="middle"><img src="/scoutinghub/images/footerbsa.gif"/></td>
                                <td align="left" class="footer-version">
                                    Copyright &copy; <g:formatDate date="${new Date()}" format="yyyy"/> ScoutingHub Version <g:meta name="app.version"/><br/>
                                </td>
                                <td valign="middle" align="right">
                                    <g:pageProperty name="page.footer"/>
                                    <sec:ifLoggedIn>
                                        <div style="display:table-cell; text-align:right">
                                            <table width="100%">

                                                <tr>
                                                    <td><g:message code="login.saveTimeLoggingIn"/></td>
                                                    <td><a href="/scoutinghub/openId/aol?suggest=true"><img src="/scoutinghub/images/social/PNG/32px/aol.png"/></a></td>
                                                    <td><a href="/scoutinghub/openId/yahoo?suggest=true"><img src="/scoutinghub/images/social/PNG/32px/yahoo.png"/></a></td>
                                                    <td><a href="/scoutinghub/openId/google?suggest=true"><img src="/scoutinghub/images/social/PNG/32px/google.png"/></a></td>
                                                    <td><a href="/scoutinghub/openId/facebook?suggest=true"><img src="/scoutinghub/images/social/PNG/32px/facebook.png"/></a></td>
                                                    %{--<td><a href="/scoutinghub/openId/google?suggest=true"><img src="/scoutinghub/images/google-logo-square.png"/></a></td>--}%
                                                    %{--<td><a href="/scoutinghub/openId/google?suggest=true"><img src="/scoutinghub/images/google-logo-square.png"/></a></td>--}%
                                                    %{--<td><a href="/scoutinghub/openId/facebook?suggest=true"><img src="/scoutinghub/images/facebook-logo-square.png"/></a></td>--}%
                                                    %{--<td><fb:login-button class="fbconnect_login" size="large" length="long" background="white"--}%
                                                    %{--onlogin="javascript:FB.Connect.requireSession(facebook_onlogin);">Facebook</fb:login-button></td>--}%
                                                </tr>

                                            </table>

                                        </div>
                                    </sec:ifLoggedIn>
                                </td>
                            </tr>
                        </table>

                    </div>
                </div>
            </td>
        </tr>
    </table>

</div>



</body></html>
