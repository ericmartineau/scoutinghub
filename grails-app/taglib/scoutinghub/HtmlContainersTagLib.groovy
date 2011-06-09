package scoutinghub

class HtmlContainersTagLib {

    def header = {attrs, body ->
//        out << "<h1 class=\"loginTitle\"><span>${body()}</span></h1>"
//        out << "<h1 class=\"ui-corner-all ui-widget-header\">"

        String iconCss = attrs.icon ? "header-icon ${attrs.icon}" : "";
        out << "<div class=\"section-header ${iconCss}\">"
        if(attrs.code) {
            out << s.div(class:'h1 td') {
                out << message(code:attrs.code)
            }
        }

        out << body()
        out << "</div>"

    }

    def ctxmenu = {attrs, body ->
//        out << "<div class='ctx-menu'><div class='header-menu header-menu-pe ui-corner-all'><span class='header-icon ui-icon ui-icon-circle-triangle-s'></span>";
//        out << "</div>"
//        out << "<ul class='ctx-menu-pe ui-widget-header ui-corner-all'>"
        out << "<ul class='ctx-menu-pe'>"
        out << body()
        out << "</ul>"
//        out << "</div>"

//        out << "</div>"
    }

    def inlineIcon = {attrs->
        out << "<div class=\"td ctx-icon ${attrs.class}\"></div>"
    }

    def ctxmenuItem = {attrs, body->
        out << "<li class='ctx-menu-item ${attrs.class ?: ""}'>"
//        out << "<span class='ui-icon menu-icon ${attrs.class ?: ""}'></span>"
//        out << "<span class='menu-text'>"
        if(attrs.controller) {
            out << link(controller:attrs.controller, style:"white-space:nowrap;", action:attrs.action, id:attrs.id, params:attrs.params) {
                out << inlineIcon(class:"${attrs.iconType}-icon")
                out << ctxmenuLabel {
                    out << message(code:attrs.code)
                }
            }
        } else{
            out << body()
        }
//        out << "</span>"
        out << "</li>"
    }

    def ctxmenuLabel = {attrs, body->
        out << "<div class='td'>${body()}</div>"
    }

    def msgbox = {attrs, body ->
        out << "<div style=\"text-align:left\" class=\"ui-corner-all ${attrs.class ?: ''} ${attrs.type}\">"

        if (attrs.code) {
            out << "<div class=\"msg1\">"
            out << message(code: attrs.code)
            out << "</div>"
        } else {
            out << body()
        }
        if (attrs.code2) {
            out << "<div class=\"msg2\">"
            out << message(code: attrs.code2)
            out << "</div>"
        }
        out << '</div>'
    }

    def tableHeader = {attrs, body ->
        out << "<td class='ui-state-hover tableHeader'>"
        if (attrs.code) {
            out << message(code: attrs.code)
        }
        out << body()
        out << "</td>"
    }


}
