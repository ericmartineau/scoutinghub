package scoutcert

class ScoutTagLib {
    static namespace = "f"

    def leaderTraining = {attrs ->
        LeaderCertificationInfo certificationInfo = attrs.certificationInfo
        out << render(template: "/tags/leaderTraining", model: [certificationInfo: certificationInfo]);
    }

    def bigTextField = {attrs, body ->
        def type = attrs.type ?: "text"
        out << "<table class='fldContainer'><tr><td align='left'>"
//        out << "<span class='fldContainerSpacer'></span>"
//        out << "<span class='fldContainer'>"
        out << "<label class='fldLabel' for='${attrs.name}'>${message(code: attrs.code)}</label><br />"
        out << "<input type='${type}' class='fldInput loginForm ui-corner-all' name='${attrs.name}' id='${attrs.name}'/>"
        out << "</td></tr></table>"
//        out << "<span class='fldContainerSpacer'></span>"
//        out << "</div>"

    }
}
