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

                jQuery("#phone").val(json.phone);
                jQuery("#address1").val(json.address1);
                jQuery("#city").val(json.city);
                jQuery("#state").val(json.state);
                jQuery("#postalCode").val(json.postalCode);
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
            var phone = jQuery("#phone").val();
            var address1 = jQuery("#address1").val();
            var scoutid = jQuery("#scoutid").val();

            var postData = {id:id, firstName:firstName, phone:phone, address1:address1, lastName:lastName, email:email, scoutid:scoutid};
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
            keypressDelay('findMatchPhone', jQuery("#phone"), findMatch, 300);
            keypressDelay('findMatchAddress', jQuery("#address1"), findMatch, 300);

            keypressDelay('findMatchScoutid', jQuery("#scoutid"), findMatch, 300);


        });
    </script>
</head>

<body>

<s:content>

    <g:form action="save">
        <g:hiddenField name="id" value="${addCommand?.foundLeader?.id}"/>
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

            <s:propertyList class="edit-form">
                <s:div class="alternate-color">
                    <s:textField name="firstName" tabindex="1" code="leader.firstName.label" value="${addCommand?.firstName}"/>
                    <s:textField name="address1" tabindex="5" code="leader.address1.label" value="${addCommand?.address1}"/>
                </s:div>
                <s:div class="alternate-color">
                    <s:textField name="lastName" tabindex="2" code="leader.lastName.label" value="${addCommand?.lastName}"/>
                    <s:textField name="city" tabindex="6" code="leader.city.label" value="${addCommand?.city}"/>
                </s:div>

                <s:div class="alternate-color">
                    <s:textField name="email" tabindex="3" code="leader.email.label" value="${addCommand?.email}"/>
                    <s:textField name="state" tabindex="7" code="leader.state.label" value="${addCommand?.state}"/>
                </s:div>

                <s:div class="alternate-color">
                    <s:textField name="phone" tabindex="4"  code="leader.phone.label"
                                 value="${f.formatPhone(phone: addCommand?.phone)}"/>
                    <s:textField name="postalCode" tabindex="8" code="leader.postalCode.label" value="${addCommand?.postalCode}"/>
                </s:div>

                <s:div class="alternate-color">
                    <s:textField name="scoutid" tabindex="9"  code="leader.scoutid.label" value="${addCommand?.scoutid}"/>

                </s:div>



            </s:propertyList>

            <s:div class="foundIssues">

            </s:div>

        </s:section>

        <s:section>
            <s:sectionHeader code="flow.locateAccount.unitInfo" icon="units-icon"/>

            <s:propertyList class="edit-form">
                <s:div class="alternate-color">
                    <s:selecter class="selecter selecter-short"
                                id="unitPosition" name="unitPosition" value="${addCommand?.unitPosition?.name()}"
                                code="${message(code:'label.unitPosition')}"
                                placeholder="${message(code:'label.unitPosition')}">
                        <s:unitSelectorOptions/>
                    </s:selecter>

                    <s:textField name="unitNumber" otherAttrs="[idfield:'unitNumberId', positionfield:'unitPosition']"
                                 class="unitSelector" value="${addCommand?.unit}"
                                 code="${message(code:'label.unit')}"
                                 placeholder="${message(code:'label.unit')}"/>
                </s:div>

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
