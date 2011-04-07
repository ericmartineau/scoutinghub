<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
        xmlns:fb="http://www.facebook.com/2008/fbml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.facebook.com/2008/fbml ">
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="menu.login"/></title>

</head>

<body>

<s:content class="twoContent">

%{--<s:smallHeader><g:message code=""/></s:smallHeader>--}%
    <form action='${daoPostUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
        <s:section header="small" code="login.enteruandp" class="twoSection">
            <g:if test='${flash.message}'>
                <s:msg type="error" code="${flash.message}"/>
            </g:if>

            <s:bigTextField name="j_username" code="${message(code:'label.username')}" placeholder="${message(code:'label.username')}"/>
            <s:bigTextField type="password" name="j_password" code="${message(code:'label.password')}" placeholder="${message(code:'label.password')}"/>
            <s:div class="loginPlusRememberMe">
                <s:checkbox class='rememberMe' code='label.rememberMe' name='${rememberMeParameter}'/>
                <s:submit name="login" class="ui-button" value="${message(code: 'label.login')}"/>
            </s:div>
        </s:section>

    </form>

    <s:section header="small" code="login.firstTimeHere" class="twoSection">
        <s:bigButton controller="login" action="accountLink" name="createAccount" value="${message(code:'label.createAccount')}"/>
    </s:section>

</s:content>




<div id="fb-root"></div>
<script
        src="http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php/en_US"
        type="text/javascript"></script>
<script type="text/javascript">
    //    FB.init("d6fc406cd3f5f8d3458eda5bd4e19e75", "/scoutcert/static/xd_receiver.html");

    function facebook_onlogin() {
        FB.Connect.ifUserConnected(function () {
            window.location = '/scoutcert/openId/facebook';
        });
    }
    <!--
    jQuery(document).ready(function() {
        document.forms['loginForm'].elements['j_username'].focus();
        FB_RequireFeatures(["XFBML"], function() {

            FB.Facebook.init("d6fc406cd3f5f8d3458eda5bd4e19e75", "/scoutcert/static/xd_receiver.html");
            FB.Facebook.get_initialized().waitUntilReady(function() {
//                setTimeout(function() {
                jQuery(".FB_login_button").find("img").attr("src", "/scoutcert/images/facebook.jpg")
//                }, 150)
            })


        });
    });


    // -->
</script>
</body>
</html>
