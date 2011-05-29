package scoutinghub

class ScoutTagLib {
    static namespace = "f"

    ScoutGroupService scoutGroupService

    def leaderTraining = {attrs ->
        LeaderCertificationInfo certificationInfo = attrs.certificationInfo
        out << render(template: "/tags/leaderTraining", model: [certificationInfo: certificationInfo]);
    }

    def completeTrainingLink = {attrs, body ->
        LeaderCertificationInfo certificationInfo = attrs.certificationInfo;
        out << s.linker(controller: "leaderCertification",
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
        out << "<table cellpadding='0' cellspacing='0' class='fldContainer ${attrs.class}'><tr><td align='left'>"
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
        out << " type='${type}' value='${attrs.value ?: ""}' class='fldInput loginForm ui-corner-all ${attrs.class ?: ""}' name='${attrs.name}' id='${attrs.name}'/>"
    }

    def formControl = {attrs, body ->
        def type = attrs.type ?: "text"
        out << "<table cellpadding='0' cellspacing='0' class='fldContainer ${attrs.class}'><tr><td align='left'>"
        out << "<span class='fldContainerSpacer'></span>"
        out << "<span class='fldContainer'>"
        out << "<label class='fldLabel' for='${attrs.name}'>${message(code: attrs.code)}</label><br />"
        out << body()
        out << "</td></tr></table>"
//
    }

    def dynamicUnitSelector = {attrs ->
        out << "<div class='unitSelectTree'></div>"
    }


}
