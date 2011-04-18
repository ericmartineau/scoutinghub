%{--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">--}%
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/3.3.0/build/cssreset/reset-min.css">
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

    <g:javascript library="jquery.ba-throttle-debounce.min"/>
    <g:javascript library="bsa"/>
    <g:javascript library="dropCurves.min"/>

    <g:javascript library="prototype"/>

    <script src="http://maps.google.com/maps?file=api&v=2&sensor=false&key=${grailsApplication.config.google.key}" type="text/javascript"></script>


    <link rel="stylesheet" href="${resource(dir: 'js/jquery.selectBox-1.0.1', file: 'jquery.selectBox.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'js/jquery-ui-1.8.11.custom/css/start', file: 'jquery-ui-1.8.11.custom.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bsa.css')}"/>
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
            <td align="center">
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="center">

                            <div id="logo">
                                <span class="logoBar"><g:link controller="leader" action="index"><img src="/scoutcert/images/ScoutHub.png" style="border-width:0px;"></g:link></span>
                                %{--<span class="logoBar"><img src="/scoutcert/images/010-GrandCanyon.jpg" style="border-width:0px;"></span>--}%
                                <span style="float:right;vertical-align:bottom;margin-top:75px">
                                    <sec:ifLoggedIn>
                                    %{--<div style="text-align:right">--}%
                                        <table width="100%">
                                            <tr>
                                                <td align="right">
                                                    <table>
                                                        <tr>
                                                            <td align="right"><g:textField name="leaderQuery" id="leaderQuery" class="ui-corner-all"/></td>
                                                            <td><a href="javascript:leaderQuery()" style="font-size:14px" class="ui-button ui-button-style">
                                                                <g:message code="permission.index.searchButton"/></a></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>

                                    %{--</div>--}%
                                    </sec:ifLoggedIn>
                                </span>

                            </div>

                        </td>
                    </tr>

                </table>



                <div id="outer" class="shadow ui-corner-all">

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
                                <td valign="middle"><img src="/scoutcert/images/footerbsa.gif"/></td>
                                <td valign="middle" align="right">
                                    <g:pageProperty name="page.footer"/>
                                    <sec:ifLoggedIn>
                                        <div style="display:table-cell; text-align:right">
                                            <table>

                                                <tr>
                                                    <td><g:message code="login.saveTimeLoggingIn"/></td>
                                                    <td><a href="/scoutcert/openId/yahoo?suggest=true"><img src="/scoutcert/images/yahoo-logo-square.png"/></a></td>
                                                    <td><a href="/scoutcert/openId/google?suggest=true"><img src="/scoutcert/images/google-logo-square.png"/></a></td>
                                                    <td><a href="/scoutcert/openId/facebook?suggest=true"><img src="/scoutcert/images/facebook-logo-square.png"/></a></td>
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
