<div class='leader-unit'>

    <div class="leader-unit-position"><h4>${groupName}</h4></div>

    <div class="leader-unit-unitname">
        <h5><a class="manage-this-unit" href="#unit${leaderGroup.id}" data-toggle="modal">${unitName}</a></h5>
    </div>

</div>

<g:modal id="unit${leaderGroup.id}" code="${leaderGroup.leader}">
    <g:modalBody>
        <s:property code="${groupName}">
            ${unitName}
        </s:property>
        <s:property code="leader.profile.permissions">
            <p:canAdministerGroup leaderGroup="${leaderGroup}">
                <g:message code="leaderGroup.admin" args="[leaderGroup.scoutGroup]"/><br/>
            </p:canAdministerGroup>
            <p:cantAdministerGroup leaderGroup="${leaderGroup}">
                <g:message code="leaderGroup.notAdmin" args="[leaderGroup.scoutGroup]"/><br/>
            </p:cantAdministerGroup>

        </s:property>

    </g:modalBody>

    <g:modalFooter>
        <p:canAdministerGroup leaderGroup="${leaderGroup}">
            <g:if test="${leaderGroup.admin}">
                <g:link class="btn" controller="leaderGroup" action="revokeAdmin" id="${leaderGroup.id}">
                    <g:message code="leaderGroup.revokeAdmin"/>
                </g:link>
            </g:if>
        </p:canAdministerGroup>
        <p:cantAdministerGroup leaderGroup="${leaderGroup}">
            <g:link class="btn" controller="leaderGroup" action="revokeAdmin" id="${leaderGroup.id}">
                <g:message code="leaderGroup.makeAdmin"/>
            </g:link>
        </p:cantAdministerGroup>

        <g:link class="btn btn-danger" controller="leaderGroup" action="performRemove"
                id="${leaderGroup.id}"><g:message code="leaderGroup.remove" /></g:link>
    </g:modalFooter>
</g:modal>






%{--out << g.link(class:"manage-this-unit", controller: "scoutGroup", action:"show", id:leaderGroup?.scoutGroup?.id) {--}%
%{--out << unitName--}%
%{--}--}%
%{--} else {--}%
%{--out << unitName--}%
%{--}--}%
%{--out << "</h5></div>"--}%
%{--if (leaderGroup) {--}%
%{--out << "<div>"--}%
%{--out << link(controller: 'leaderGroup',--}%
%{--action: 'confirmRemove',--}%
%{--id: leaderGroup.id,--}%
%{--title: message(code: 'trainingReport.removeFromUnit'),--}%
%{--'class': 'lightbox',--}%
%{--lbwidth: '550') {--}%

%{--out << "<i class='red icon-remove'></i>&nbsp;"--}%
%{--out << message(code: 'trainingReport.removeFromUnit')--}%
%{--}--}%
%{--out << "</div>"--}%
%{--}--}%

%{--out << "</div>"--}%