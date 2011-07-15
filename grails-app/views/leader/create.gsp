<html>
<head>
    <meta name='layout' content='${layoutName}'/>
    <title><g:message code="leader.create.title"/></title>
    <script type="text/javascript">

        function useLeader(leaderId) {
            jQuery.getJSON("/scoutinghub/leader/getLeaderDetails/" + leaderId, {}, function(json) {
                jQuery("#firstName").val(json.firstName);
                jQuery("#lastName").val(json.lastName);
                jQuery("#email").val(json.email);
                jQuery("#scoutid").val(json.scoutid);
                jQuery("#id").val(json.id);
                jQuery(".foundIssues").html("");
            });
        }

        function findMatch() {
            var id = jQuery("#id").val();
            var firstName = jQuery("#firstName").val();
            var lastName = jQuery("#lastName").val();
            var email = jQuery("#email").val();
            var scoutid = jQuery("#scoutid").val();

            var postData = {id:id, firstName:firstName, lastName:lastName, email:email, scoutid:scoutid};
            if (!id) {

                jQuery.ajax({
                    url: "/scoutinghub/leader/findLeaderMatch",
                    data: postData,
                    success: function(data) {
                        if (data) {
                            jQuery(".foundIssues").html(data);
                        }
                    }
                });
            } else {
                jQuery.getJSON("/scoutinghub/leader/recheckLeaderMatch", postData, function(json) {
                    if (!json.check) {
                        jQuery("#id").val("");
                        findMatch();
                    }
                });

            }
        }

        jQuery(document).ready(function() {
            keypressDelay('findMatchFirstName', jQuery("#firstName"), findMatch, 300);
            keypressDelay('findMatchLastName', jQuery("#lastName"), findMatch, 300);
            keypressDelay('findMatchEmail', jQuery("#email"), findMatch, 300);
            keypressDelay('findMatchScoutid', jQuery("#scoutid"), findMatch, 300);


        });
    </script>
</head>

<body>

<s:content>

    <g:form action="save">

        <s:section>
            <s:sectionHeader icon="profile-icon" code="flow.locateAccount.createNewAccount"/>


            <g:if test="${addCommand?.hasErrors()}">
                <s:msg type="error">
                    <div class="msg1">
                        <g:message code="label.error"/>
                    </div>

                    <g:renderErrors bean="${addCommand}"/>

                </s:msg>

            </g:if>

            <s:propertyList class="vertical-form">
                <g:hiddenField name="foundLeader.id" id="id" value="${addCommand?.foundLeader?.id}"/>
                <s:textField name="email" value="${addCommand?.email}" code="${message(code:'leader.email.label')}"
                             placeholder="${message(code:'leader.email.label')}"/>
                <s:textField name="firstName" value="${addCommand?.firstName}"
                             code="${message(code:'leader.firstName.label')}"
                             placeholder="${message(code:'leader.firstName.label')}"/>
                <s:textField name="lastName" value="${addCommand?.lastName}"
                             code="${message(code:'leader.lastName.label')}"
                             placeholder="${message(code:'leader.lastName.label')}"/>
                <s:textField name="scoutid" value="${addCommand?.scoutid}" code="${message(code:'label.scoutid')}"
                             placeholder="${message(code:'label.scoutid')}"/>

            </s:propertyList>
            <s:div class="foundIssues">

            </s:div>

        </s:section>

        <s:section>
            <s:sectionHeader code="flow.locateAccount.unitInfo" icon="units-icon"/>
        %{--<s:text>--}%
        %{--<g:message code="flow.locateAccount.unitCreateLater"/>--}%
        %{--</s:text>--}%



            <s:propertyList class="vertical-form">

                <s:selecter class="selecter"
                            id="unitPosition" name="unitPosition" value="${addCommand?.unitPosition?.name()}"
                            code="${message(code:'label.unitPosition')}"
                            placeholder="${message(code:'label.unitPosition')}">
                    <s:unitSelectorOptions/>
                </s:selecter>

                <s:textField name="unitNumber" otherAttrs="[idfield:'unitNumberId', positionfield:'unitPosition']"
                             class="unitSelector unit-selector-style" value="${addCommand?.unit}"
                             code="${message(code:'label.unit')}"
                             placeholder="${message(code:'label.unit')}"/>

            </s:propertyList>


        %{--<s:unitSelector name="unitNumber" class="unitSelector" value="${addCommand?.unitNumber}" code="${message(code:'label.unitNumber')}"/>--}%
            <g:hiddenField id="unitNumberId" name="unit.id" value="${addCommand?.unit?.id}"/>

        </s:section>

        <s:section class='centered'>
            <s:submit name="createNewAccount" value="${message(code: 'leader.create.submit')}"/>
        </s:section>

    </g:form>

</s:content>

</body>
</html>
