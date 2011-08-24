package scoutinghub

class ScoutTagLib {
    static namespace = "f"

    ScoutGroupService scoutGroupService
    TrainingService trainingService

    def leaderTraining = {attrs ->
        LeaderCertificationInfo certificationInfo = attrs.certificationInfo
        out << render(template: "/tags/leaderTraining", model: [certificationInfo: certificationInfo]);
    }

    def findLeaderMatch = {attrs->
        out << render(template: "/leader/findLeaderMatch", model: [leader:attrs.leaders])
    }

    def formatPhone = {attrs->
        String phone = attrs.phone
        if(phone?.length() == 7) {
            out << phone.substring(0, 3) + "-" + phone.substring(4)
        } else if(phone?.length() == 10) {
            out << "(${phone.substring(0, 3)}) ${phone.substring(3, 6)}-${phone.substring(6)}"
        } else if(phone?.length() == 11) {
            out << "${phone.substring(0, 1)} (${phone.substring(1, 4)}) ${phone.substring(4, 7)}-${phone.substring(7)}"
        } else {
            return null
        }
    }

    def missingTrainingCodes = {attrs->
        LeaderGroup leaderGroup = attrs.leaderGroup
        List<ProgramCertification> certifications = trainingService.getRequiredCertifications(leaderGroup)
        def missing = []
        certifications.each {ProgramCertification programCertification->
            if (!leaderGroup.leader.certifications?.find {it.certification.id == programCertification.certification.id}) {
                missing << programCertification.certification.certificationCodes?.iterator()?.next()
            }
        }
        if(missing.size() == 0) {
            out << "None"
        } else {
            out << missing.join(", ")
        }

    }

    def scoutGroupReport = {attrs ->

        ScoutGroup group = attrs.group
        int index = attrs.index ?: 1

        out << "<tr><td class='level${index}' colspan='6'><h${index}>${group}</h${index}></td></tr>"

        if (group.leaderGroups?.size() > 0) {
            out << """<tr>
    <th class="level${index + 1}">Name</th>
    <th>Position</th>
    <th>% Trained</th>
    <th>Missing Training</th>
    <th>Has Logged In</th>
</tr>"""
            group.leaderGroups?.each {
                LeaderGroup leaderGroup ->
                out << render(template: "/training/leaderRow", model: [leaderGroup: leaderGroup, index:index + 1])
            }

        }

        group.childGroups.each {
            ScoutGroup scoutGroup ->
            out << scoutGroupReport(group: scoutGroup, index: index + 1)
        }

    }

    def completeTrainingLink = {attrs, body ->
        LeaderCertificationInfo certificationInfo = attrs.certificationInfo;
        out << s.linker(controller: "leaderCertification",
                elementId: "completeTraining${certificationInfo.certification.id}",
                action: "createForm",
                img: "/scoutinghub/images/training-${certificationInfo.certificationStatus?.name()?.toLowerCase()}.png",
                'class': 'lightbox',
                params: [certificationId: certificationInfo.certification.id, leaderId: certificationInfo.leader.id],
                lbwidth: 600,
                title: message(code: 'leader.profile.enterTrainingDetails')) {
            out << body();
        }
    }


    def textField = {attrs, body ->

    }

    def bigTextField = {attrs, body ->
        out << "<table cellpadding='0' cellspacing='0' class='fldContainer'><tr><td align='left'>"
//        out << "<span class='fldContainerSpacer'></span>"
        //        out << "<span class='fldContainer'>"
        out << "<label class='fldLabel' for='${attrs.name}'>${message(code: attrs.code)}</label><br />"

        out << txtField(attrs)

//        out << "</td></tr>"
        if (body) {
            out << "<tr><td>"
            out << body()
            out << "</td></tr>"
        }
        out << "</table>"

//        out << "<span class='fldContainerSpacer'></span>"
        //        out << "</div>"

    }

    def txtField = {attrs ->
        def type = attrs.type ?: "text"

        out << "<input "
        attrs.otherAttrs?.each {
            out << "${it.key}='${it.value}' "
        }
        out << " type='${type}' size='${attrs.size ?: 25}' value='${attrs.value ?: ""}' class='fldInput ui-corner-all ${attrs.class ?: ""}' name='${attrs.name}' id='${attrs.name}'/>"
    }

    def formControl = {attrs, body ->
        def type = attrs.type ?: "text"
        out << "<table cellpadding='0' cellspacing='0' class='fldContainer'><tr><td align='left'>"
        out << "<span class='fldContainerSpacer'></span>"
        out << "<span class='fldContainer'>"
        out << "<label class='fldLabel' for='${attrs.name}'>${message(code: attrs.code)}</label><br />"
        out << body()
        out << "</td></tr></table>"
    }

    def dynamicUnitSelector = {attrs ->
        out << "<div class='unitSelectTree'></div>"
    }


}
