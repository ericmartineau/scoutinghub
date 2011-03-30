package scoutcert

class HtmlContainersTagLib {

    def header = {attrs, body->
//        out << "<h1 class=\"loginTitle\"><span>${body()}</span></h1>"
        out << "<h1 class=\"ui-corner-all ui-widget-header ui-state-active\">"
        out << body()
        out << "</h1>"

    }

     def msgbox = {attrs, body->
         out << "<div style=\"text-align:left\" class=\"ui-corner-all ${attrs.type}\">"

         if(attrs.code) {
             out << "<div class=\"msg1\">"
             out << message(code: attrs.code)
             out << "</div>"
         } else {
             out << body()
         }
         if(attrs.code2) {
             out << "<div class=\"msg2\">"
             out << message(code: attrs.code2)
             out << "</div>"
         }
         out << '</div>'
     }

    def tableHeader = {attrs, body->
        out << "<td class='ui-state-hover tableHeader'>"
        if(attrs.code) {
            out << message(code: attrs.code)
        }
        out << body()
        out << "</td>"
    }


}
