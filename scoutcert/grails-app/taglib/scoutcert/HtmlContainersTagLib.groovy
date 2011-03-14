package scoutcert

class HtmlContainersTagLib {

    def header = {attrs, body->
        out << "<h1 class=\"loginTitle\"><span>${body()}</span></h1>"
    }

}
