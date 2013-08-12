package scoutinghub

class HtmlContainersTagLib {

    def header = {attrs, body ->
//        out << "<h1 class=\"loginTitle\"><span>${body()}</span></h1>"
        //        out << "<h1 class=\"ui-corner-all ui-widget-header\">"

        String iconCss = attrs.icon ? "header-icon ${attrs.icon}" : "";
        out << "<div class=\"section-header ${iconCss}\">"
        if (attrs.code) {
            out << s.div(class: 'h1 td') {
                out << message(code: attrs.code, default:attrs.code, args:attrs.args)
            }
        }

        out << body()
        out << "</div>"

    }

    def ctxmenu = {attrs, body ->
//        out << "<div class='ctx-menu'><div class='header-menu header-menu-pe ui-corner-all'><span class='header-icon ui-icon ui-icon-circle-triangle-s'></span>";
        //        out << "</div>"
        //        out << "<ul class='ctx-menu-pe ui-widget-header ui-corner-all'>"
        String icon
        out << "<div class=\"context-menu btn-group\">"
        out << body()
        out << "</div>"
//        out << """
//
//                <button class="btn">Action</button>
//                <button class="btn dropdown-toggle" data-toggle="dropdown">
//                    <span class="caret"></span>
//                </button>
//                <ul class="dropdown-menu">
//                    <!-- dropdown menu links -->
//"""
//
//        out << """
//                </ul>
//            </div>
//"""

    }

    def inlineIcon = {attrs ->
        out << "<div class=\"td ctx-icon ${attrs.class}\"></div>"
    }

    def selectWithBody = {attrs, body ->
        request.selectValue = attrs.value
        out << "<select "
        attrs.each {entry ->
            out << "${entry.key}=\"${entry.value?.encodeAsHTML()}\" "
        }
        out << ">"
        out << body()
        out << "</select>"
        request.removeAttribute("selectValue")
    }

    def selectOption = {attrs, body ->
        if (String.valueOf(request.selectValue) == String.valueOf(attrs.value)) {
            attrs.selected = "selected"
        }
        out << "<option"
        attrs.each {
            out << " ${it.key}=\"${it.value?.encodeAsHTML()}\""
        }
        out << ">"
        out << body()
        out << "</option>"
    }

    def ctxmenuItem = {attrs, body ->
//        out << "<li class='ctx-menu-item ${attrs.class ?: ""}'>"
//        out << "<span class='ui-icon menu-icon ${attrs.class ?: ""}'></span>"
        //        out << "<span class='menu-text'>"
        if (attrs.controller) {
            def img = attrs.img
            if(!img && attrs.iconType) {
                img = "${attrs.iconType}-icon"
            }
            def linkAttrs = [img:img, menu: "true", class: "btn btn-small", controller: attrs.controller, style: "white-space:nowrap;", action: attrs.action, id: attrs.id, params: attrs.params]
            if(attrs.onclick) {
                linkAttrs.onclick=attrs.onclick
            }
            out << s.linker(linkAttrs) {
                if (attrs.iconType) {
                    out << inlineIcon(class: "${attrs.iconType}-icon")
                }
                out << ctxmenuLabel {
                    out << message(code: attrs.code, args:attrs.args)
                }
            }
        } else {
            out << body()
        }
//        out << "</span>"
//        out << "</li>"
    }

    def ctxmenuLabel = {attrs, body ->
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

    def modal = {attrs, body->
        out << "<div id=\"${attrs.id}\" class=\"modal hide\">"
        out << "    <div class=\"modal-header\">"
        out << "    <button class=\"close\" data-dismiss=\"modal\">×</button>"
        out << "        <h2>${g.message(attrs)}</h2>"
        out << "    </div>"
        out << body()
        out << "</div>"
    }

    def modalBody = {attrs, body->
        out << "<div class=\"modal-body\">"
        out << body()
        out << "</div>"
    }

    def modalFooter = {attrs, body->
        out << "<div class=\"modal-footer\">"
        out << body()
        out << "</div>"
    }


}
