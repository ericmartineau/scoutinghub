<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:fb="http://www.facebook.com/2008/fbml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.facebook.com/2008/fbml ">
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="menu.login"/></title>

</head>

<body>

<s:content class="twoContent">

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
        <s:bigButton controller="login" action="accountLink" name="createAccount">
            <g:message code="label.createAccount"/>
        </s:bigButton>
    </s:section>

    <s:mobile>
        <s:section code="login.alternateproviders">
            <s:linker controller="openId" action="aol" img="/images/social/PNG/32px/aol.png">AOL</s:linker>
            <s:linker controller="openId" action="yahoo" img="/images/social/PNG/32px/yahoo.png">Yahoo!</s:linker>
            <s:linker controller="openId" action="google" img="/images/social/PNG/32px/google.png">Google</s:linker>
            <s:linker controller="openId" action="facebook" img="/images/social/PNG/32px/facebook.png">Facebook</s:linker>
        </s:section>
    </s:mobile>
</s:content>


<content tag="footer">
    <div style="display:table-cell; text-align:right">

        <table>

            <tr>
                <td><g:message code="login.alternateproviders"/></td>
                <td><a href="/scoutinghub/openId/aol"><img src="/scoutinghub/images/social/PNG/32px/aol.png"/></a></td>
                <td><a href="/scoutinghub/openId/yahoo"><img src="/scoutinghub/images/social/PNG/32px/yahoo.png"/></a></td>
                <td><a href="/scoutinghub/openId/google"><img src="/scoutinghub/images/social/PNG/32px/google.png"/></a></td>
                <td><a href="/scoutinghub/openId/facebook"><img src="/scoutinghub/images/social/PNG/32px/facebook.png"/></a></td>

            </tr>

        </table>

    </div>
</content>

</body>
</html>
