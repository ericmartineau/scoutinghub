package scoutcert

class HtmlContainersTagLib {

    def header = {attrs->
        out << "<h1 class=\"loginTitle\"><span>${attrs.title}</span></h1>"
    }

}
