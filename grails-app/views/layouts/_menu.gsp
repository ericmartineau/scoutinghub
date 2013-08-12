<ul class="nav primary">
    <li class="brand">ScoutingHub</li>
    <g:each in="${menuItems}" var="menuItem">
        <g:if test="${menuItem.subItems?.size()}">
            <li class="dropdown">
                <a class="dropdown-toggle"
                   data-toggle="dropdown"
                   href="#">
                    <g:message code="${menuItem.labelCode}" />
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu secondary">
                    <g:each in="${menuItem.subItems}" var="subItem">
                        <g:menuItem menuItem="${subItem}" />
                    </g:each>
                </ul>
            </li>
        </g:if>
        <g:else>
            <g:menuItem menuItem="${menuItem}" />
        </g:else>
        %{--<g:mainMenuItem menuItem="${menuItem}">--}%
            %{--<g:menuItem controller="${params.controller}" action="${params.action}" menuItem="${menuItem}"/>--}%
        %{--</g:mainMenuItem>--}%

    </g:each>
    <li style="width: 200px">
        <form class="navbar-search pull-right">
            <g:textField placeholder="Search" name="leaderQuery" id="leaderQuery"
                     class="search-query input-large"/>
        </form>
    </li>

</ul>

%{--<div class="clear"></div>--}%
%{--<g:subMenu controller="${params.controller}" action="${params.action}"/>--}%
%{--<div class="clear"></div>--}%
