<div id="navigation">
    <ul>
        <g:each in="${menuItems}" var="menuItem">
            <g:if test="${menuItem.requiredRoles?.size() > 0}">
                <sec:ifAnyGranted roles="${menuItem.requiredRoles?.join()}">
                    <g:menuItem controller="${params.controller}" action="${params.action}" menuItem="${menuItem}" />
                </sec:ifAnyGranted>
            </g:if>
            <g:else>
                <g:menuItem controller="${params.controller}" action="${params.action}" menuItem="${menuItem}" />
            </g:else>
        </g:each>

        <sec:ifLoggedIn>
            <li><a href="/scoutcert/logout/"><g:message code="menu.logout" /></a></li>
        </sec:ifLoggedIn>
    </ul>
</div>
<div class="clear"></div>
<g:subMenu controller="${params.controller}" action="${params.action}"/>
<div class="clear"></div>
