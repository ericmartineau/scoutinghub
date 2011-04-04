<%@ page import="scoutcert.CertificationStatus" %>
<g:if test="${certificationInfo.certificationStatus == CertificationStatus.Expired}">

    <div class="fldContainer ui-corner-all profileCertification validation warningIcon">
        <div class="profileData">${certificationInfo.certification.name}</div>
        <div class="trainingDetails">
            <g:message code="leader.profile.trainingExpiredOn"/> <g:formatDate date="${certificationInfo.leaderCertification.goodUntilDate()}" format="MM-dd-yyyy"/>
            <a href="javascript:markTrainingComplete(${certificationInfo.leader.id}, ${certificationInfo.leaderCertification.certification.id})"><g:message code="leader.profile.updateTraining"/></a>
            <br/>
        </div>

    </div>
</g:if>

<g:elseif test="${certificationInfo.certificationStatus == CertificationStatus.Missing}">
    <div class="fldContainer ui-corner-all profileCertification validation errorIcon">
        <div class="profileData">${certificationInfo.certification.name}</div>
        <div class="trainingDetails missingTraining">
            <g:message code="leader.profile.missingTraining"/>&nbsp;
            <a href="javascript:markTrainingComplete(${certificationInfo.leader.id},
                ${certificationInfo.certification.id})"><g:message code="leader.profile.alreadyComplete"/></a>
        </div>
    </div>

</g:elseif>
<g:else>
    <g:set var="certification" value="${certificationInfo.leader.findCertification(certificationInfo.certification)}"/>
    <div class="fldContainer ui-corner-all profileCertification validation successIcon">
        <div class="profileData">${certificationInfo.certification.name}</div>
        <div class="trainingDetails">Good until <g:formatDate date="${certificationInfo.leaderCertification.goodUntilDate()}" format="MM-dd-yyyy"/><br/>
            <g:message code="${certificationInfo.leaderCertification.enteredType}.label"/> ${certificationInfo.leaderCertification.enteredBy} <g:formatDate date="${certificationInfo.leaderCertification.dateEntered}" format="MM-yyyy"/>
        </div>

    </div>
</g:else>