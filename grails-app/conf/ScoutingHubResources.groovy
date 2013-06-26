modules = {

    bootstrap {
        resource url:"bootstrap/css/bootstrap-responsive.min.css"
        resource url:"bootstrap/js/bootstrap.min.js"
    }

    jqueryPlugins {
        dependsOn "application"
        resource url: "js/jquery.hotkeys.js"
        resource url: "js/jquery.ba-throttle-debounce.min.js"
//        resource url: "js/jquery-ui-1.8.11.custom/development-bundle/ui/jquery-ui-1.8.11.custom.js"
//        resource url: "js/jquery.selectBox-1.0.1/jquery.selectBox.min.js"
        resource url: "js/jquery.selectbox.js"
        resource url: "js/jquery.selectBox-1.0.1/jquery.selectBox.css"

        resource url: "js/jquery.qtip.min.js"
        resource url: "css/jquery.qtip.min.css"

    }

    bsa {
        dependsOn "jqueryPlugins"
        resource url: "js/bsa.js"
        resource url: "js/bsa-unit-autocomplete.js"
    }

    application {
        resource url:"wood/javascripts/application.js"
        resource url:"wood/stylesheets/application.css"
        resource url:"wood/stylesheets/extended.css"
        resource url:"wood/font/opensans.css"
    }

    wood {
//        dependsOn "bootstrap"
        resource url:"http://html5shim.googlecode.com/svn/trunk/html5.js", wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
        resource url:"wood/javascripts/html5shiv.js", wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
        resource url:"wood/javascripts/excanvas.js", wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
        resource url:"wood/javascripts/ie_fix.js", wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
        resource url:"wood/stylesheets/ie_fix.css", wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
    }

    jstree {
        dependsOn "jquery"
        resource url:"js/jquery.jstree.js"
    }

}