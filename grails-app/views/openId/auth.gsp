<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:fb="http://www.facebook.com/2008/fbml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.facebook.com/2008/fbml ">
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="menu.login"/></title>
</head>

<body>

<form action='${daoPostUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
    <s:content small="true">
        <s:rowFluid>
            <s:column>
                <s:section code="login.enteruandp" center="true">

                    <h2><g:message code="auth.signin"/></h2>
                    <g:if test='${flash.message}'>
                        <s:msg type="error" code="${flash.message}"/>
                    </g:if>

                    <s:bigTextField name="j_username" code="${message(code: 'label.username')}"
                                    placeholder="${message(code: 'label.username')}"/>
                    <s:bigTextField type="password" name="j_password" code="${message(code: 'label.password')}"
                                    placeholder="${message(code: 'label.password')}"/>

                    <s:checkbox class='rememberMe' code='label.rememberMe' name='${rememberMeParameter}'/>
                    <br/>
                    <s:submit class="btn-large" name="login" value="${message(code: 'label.login')}"/>
                </s:section>

            </s:column>

            <s:column>
                <s:section code="login.firstTimeHere" class="center">
                    <h2><g:message code="auth.create"/></h2>

                    <s:bigButton class="btn-success" controller="login" action="accountLink" name="createAccount">
                        <g:message code="label.createAccount"/>
                    </s:bigButton>
                </s:section>

                <s:section code="login.firstTimeHere" class="center form-inline">
                    <h2><g:message code="login.alternateproviders"/></h2>
                    <a href="/scoutinghub/openId/aol"><img src="/scoutinghub/images/social/PNG/32px/aol.png"/></a>
                    <a href="/scoutinghub/openId/yahoo"><img src="/scoutinghub/images/social/PNG/32px/yahoo.png"/></a>
                    <a href="/scoutinghub/openId/google"><img src="/scoutinghub/images/social/PNG/32px/google.png"/></a>
                    <a href="/scoutinghub/openId/facebook"><img src="/scoutinghub/images/social/PNG/32px/facebook.png"/></a>
                </s:section>
            </s:column>






        </s:rowFluid>

        <s:mobile>
            <s:section code="login.alternateproviders">
                <s:linker controller="openId" action="aol"
                          img="/scoutinghub/images/social/PNG/32px/aol.png">AOL</s:linker>
                <s:linker controller="openId" action="yahoo"
                          img="/scoutinghub/images/social/PNG/32px/yahoo.png">Yahoo!</s:linker>
                <s:linker controller="openId" action="google"
                          img="/scoutinghub/images/social/PNG/32px/google.png">Google</s:linker>
                <s:linker controller="openId" action="facebook"
                          img="/scoutinghub/images/social/PNG/32px/facebook.png">Facebook</s:linker>
            </s:section>
        </s:mobile>

    </s:content>
</form>
</body>
</html>
