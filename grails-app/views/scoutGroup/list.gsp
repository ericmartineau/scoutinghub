<%@ page import="scoutinghub.ScoutGroup" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'scoutGroup.label', default: 'ScoutGroup')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
    <script type="text/javascript">
        jQuery(document).ready(function() {
            keypressDelay('unitQuery', jQuery("#unitQuery"), unitQuery, 200);
        });

        function unitQuery() {
            var searchTerm = jQuery("#unitQuery").val();
            var orgType = jQuery("#groupType").val();
            jQuery(".searchResults").html("<div class='spinner'>Loading...<br /><img src='/scoutinghub/images/loading.gif' /></div>");
            jQuery(".searchResults").load("/scoutinghub/scoutGroup/unitQuery", {param:searchTerm, orgType:orgType});
        }
    </script>
</head>

<body>

<s:content>
    <s:section>
        <s:sectionHeader code="scoutGroup.list.title" icon="units-icon">
            <s:ctxmenu>
                <g:ctxmenuItem>
                    <g:link class="create" action="create">
                        <g:inlineIcon class="add-icon"/>
                        <g:ctxmenuLabel>
                            <g:message code="default.new.label" args="[message(code:entityName)]"/>
                        </g:ctxmenuLabel>
                    </g:link>
                </g:ctxmenuItem>
            </s:ctxmenu>
        </s:sectionHeader>

        <s:propertyList class="search-for-unit alternate-color">
            <s:textField code="scoutGroup.list.searchForUnit" name="unitQuery" />
            <s:selecter onchange="unitQuery()" class="alternate-color" code="scoutGroup.list.searchForOrgType" noSelection="['': 'Show All']"
                        name="groupType" from="${scoutinghub.ScoutGroupType?.values()}" value="${scoutGroupInstance?.groupType}"/>
        </s:propertyList>

    </s:section>

    <s:section class="searchResults">

    </s:section>
</s:content>


</body>
</html>
