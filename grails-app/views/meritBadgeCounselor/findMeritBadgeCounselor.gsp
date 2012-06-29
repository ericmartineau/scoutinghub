<%@ page import="scoutinghub.meritbadge.MeritBadge; scoutinghub.meritbadge.MeritBadgeCounselor" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'meritBadgeCounselor.label', default: 'MeritBadgeCounselor')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>

    <script type="text/javascript">

        jQuery(document).ready(function () {
            jQuery("#searchBadge").selectBox().change(searchMeritBadgeCounselors);
        });

        function searchMeritBadgeCounselors() {
            var badgeId = jQuery("#searchBadge").val();
            var unitId = jQuery("#searchUnit").val();
            if (badgeId && unitId) {

                jQuery.ajax({
                    url:"/scoutinghub/meritBadgeCounselor/performCounselorSearch",
                    data:{badge:badgeId, unit:unitId},
                    cache:false,
                    dataType:"json",
                    success:function (json) {
                        alert("Hello!!")
                    }
                });

            }
        }
    </script>
</head>

<body>
<g:form method="post">

    <s:content>
        <g:hasErrors bean="${flash.leaderError}">
            <s:msg type="error">
                <g:renderErrors bean="${flash.leaderError}"/>
            </s:msg>
        </g:hasErrors>

        <s:section class="floatSection">
            <s:sectionHeader icon="profile-icon" code="meritBadgeCounselor.search"/>

            <s:propertyList class="edit-profile">
                <s:div class="alternate-color">
                    <s:textField data-onchange="searchMeritBadgeCounselors" size="38" name="scoutGroupName" otherAttrs="[idField: 'searchUnit']" class="unitSelector"
                                 code="${message(code: 'label.unitNumber')}"
                                 placeholder="${message(code: 'label.unitNumber')}"/>

                    <g:hiddenField id="searchUnit" name="searchUnit"/>

                    <s:selecter from="${MeritBadge.listOrderByName()}" optionKey="id" code="meritBadgeCounselor.searchBadge" id="searchBadge" name="searchBadge"/>
                </s:div>

            </s:propertyList>
        </s:section>

    </s:content>
</g:form>

</body>
</html>
