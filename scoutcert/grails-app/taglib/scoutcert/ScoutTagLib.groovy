package scoutcert

class ScoutTagLib {
    static namespace = "f"

    def leaderTraining = {attrs ->
        LeaderCertificationInfo certificationInfo = attrs.certificationInfo
        out << render(template: "/tags/leaderTraining", model: [certificationInfo: certificationInfo]);
    }

    def bigTextField = {attrs, body ->
        def type = attrs.type ?: "text"
        out << "<table cellpadding='0' cellspacing='0' class='fldContainer ${attrs.class}'><tr><td align='left'>"
//        out << "<span class='fldContainerSpacer'></span>"
//        out << "<span class='fldContainer'>"
        out << "<label class='fldLabel' for='${attrs.name}'>${message(code: attrs.code)}</label><br />"

        out << "<input "
        attrs.otherAttrs?.each {
            out << "${it.key}='${it.value}' "
        }
        out << " type='${type}' value='${attrs.value ?: ""}' class='fldInput loginForm ui-corner-all ${attrs.class ?: ""}' name='${attrs.name}' id='${attrs.name}'/>"
        out << "</td></tr>"
        if(body) {
            out << "<tr><td>${body()}</td></tr>"
        }
        out << "</table>"

//        out << "<span class='fldContainerSpacer'></span>"
//        out << "</div>"

    }

    def formControl = {attrs, body->
        def type = attrs.type ?: "text"
        out << "<table cellpadding='0' cellspacing='0' class='fldContainer ${attrs.class}'><tr><td align='left'>"
//        out << "<span class='fldContainerSpacer'></span>"
//        out << "<span class='fldContainer'>"
        out << "<label class='fldLabel' for='${attrs.name}'>${message(code: attrs.code)}</label><br />"
        out << body()
        out << "</td></tr></table>"
//
    }

    def dynamicUnitSelector = {attrs->
        out << "<div class='unitSelectTree'></div>"

    }
}