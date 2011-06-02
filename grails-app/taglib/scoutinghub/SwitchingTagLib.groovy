package scoutinghub

import webkit.IphoneTagLib
import scoutinghub.menu.MenuItem
import scoutinghub.menu.MainMenuItem
import scoutinghub.menu.SubMenuItem
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.security.core.GrantedAuthority
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

/**
 * This class will provide switching between mobile and regular tags
 */
class SwitchingTagLib {
    MenuService menuService

    SpringSecurityService securityService

    static namespace = "s"

    def propertyList = {attrs, body ->
        if (session.isMobile) {
            out << body()
        } else {
            out << "<ul class='property-list ${attrs.class ?: ""}'>${body()}</ul>"
        }

    }

    def address = {attrs, body ->
        Address addr = attrs.address
        if (session.isMobile) {
//            out << "<li class='smallfield'><span class='name'>${message(code: attrs.code)}</span><span class='value'>${body()}</span></li>"
        } else {
            out << "<div class='fldData'>${addr.locationName}</div>"
            out << "<div class='addressProperty'>${addr.address}</div>";
            out << "<div class='addressProperty'>${addr.city} ${addr.state} ${addr.zip}</div>";
            out << "</span>"
        }
    }

    def mapLink = {attrs, body ->
        if (session.isMobile) {
            //todo: Implement this!
        } else {
            Address addr = attrs.address
            String addrString = "${addr.address} ${addr.city}, ${addr.state} ${addr.zip}"
            out << "<a href=\"javascript:showMap('${addrString}')\">${addrString}</a>"
        }
    }

    def leaderUnit = {attrs, body ->
        LeaderGroup leaderGroup = attrs.leaderGroup;
        if (session.isMobile) {
            out << property(attrs, body)
        } else {
            out << "<li class='leader-unit ${attrs.class ?: ''}'>"
            out << "<div class='leader-unit-position'>${message(code: attrs.code)} "
            if (leaderGroup) {
                out << "<div style='float:right'>"
                out << link(controller: 'leaderGroup',
                        action: 'confirmRemove',
                        id: leaderGroup.id,
                        title: message(code: 'trainingReport.removeFromUnit'),
                        'class': 'noshow lightbox remove-button',
                        lbwidth: '550') {

                    out << "<div class='td top'><img align='top' width='16' src='/scoutinghub/images/knobs/PNG/Knob Remove Red.png' /></div>"
                    out << "<div class='td top'>&nbsp;"
                    out << message(code: 'trainingReport.removeFromUnit')
                    out << "</div>"


                }
                out << "</div>"
            }
            out << "</div>"
            out << "<div class='leader-unit-unitname'>${body()}</div>"
            out << "</li>"
        }
    }

    def property = {attrs, body ->
        if (session.isMobile) {
            out << "<li class='smallfield'><span class='name'>${message(code: attrs.code, args: attrs.args)}</span><span class='value'>${body()}</span></li>"
        } else {
            out << "<li class='prop ${attrs.class ?: ''}'>"
            if (attrs.code) {
                out << "<div class='propLabel'>"
                out << message(code: attrs.code, args: attrs.args)
                out << "</div>"
            }

            out << "<div class='propData'>${body()}</div>"
            out << "</li>"
        }
    }

    def pageItem = {attrs, body ->
        if (session.isMobile) {
            out << "<li class='${attrs.type}'><span class='name'>${message(code: attrs.name)}</span>${body()}</li>"
        } else {
            out << "<div class='${attrs.type}'><span class='name'>${message(code: attrs.name)}</span>${body()}</div>"
        }
    }

    def item = {attrs, body ->
        if (session.isMobile) {
            out << "<li class='textbox'>${body()}</li>"
        } else {
            out << "<div class='headerText'>${body()}</div>"
        }
    }

    def content = {attrs, body ->
        if (session.isMobile) {
            out << iwebkit.content(attrs, body)
        } else {
            out << "<div class='content ${attrs.class ?: ""}'>"
            out << body()
//            out << "<div style='clear:both;'></div>"
            out << "</div>"
        }
    }

    def text = {attrs, body ->
        if (session.isMobile) {
            out << "<li class='textbox'>"
            out << body()
            out << "</li>"
        } else {
            out << "<div class='text'>${body()}</div>"

        }
    }

    def smallHeader = {attrs, body ->
        if (session.isMobile) {
            out << "<span class='graytitle'>${body()}</span>"
        } else {
            out << "<h2>${body()}</h2>"
        }
    }

    def sectionHeader = {attrs, body ->
        def icon = attrs.icon
        if (session.isMobile) {
            g.set(var: "sectionHeader", value: message(code: attrs.code), scope: "request");
        } else {
            out << g.header(attrs, body)
        }
    }

    def form = {attrs, body ->
        out << body()
    }

    def leaderTraining = {attrs ->
        if (session.isMobile) {
            LeaderCertificationInfo certificationInfo = attrs.certificationInfo

            out << f.completeTrainingLink(certificationInfo: certificationInfo) {
                out << certificationInfo?.certification?.name
            }

        } else {
            out << f.leaderTraining(certificationInfo: attrs.certificationInfo)
        }
    }

    def submit = {attrs, body ->
        def name = attrs.name
        def value = attrs.value
        if (session.isMobile) {
            out << "<li class='button'>"
            out << submitButton(attrs)
            out << "</li>"
        } else {
            attrs.class += " ui-button"
            out << submitButton(attrs)
        }

    }

    def checkbox = {attrs, body ->
        if (session.isMobile) {

            out << """<li class='checkbox ${attrs.class ?: ''}'>
                    <span class='name'>${message(code: attrs.code)}</span>
                    <input type='checkbox' name='${attrs.name}' value='yes' />
                    </li>"""

        } else {
            out << "<div class='${attrs.class}'>"
            out << "<span class='chk-input'>"
            out << checkBox(attrs)
            out << "</span>"
            out << "<span class='chk-label'>"
            out << message(code: attrs.code)
            out << "</span>"
            out << "</div>"
        }
    }

    def msg = {attrs, body ->
        def code = attrs.code
        def code2 = attrs.code2

        if (session.isMobile) {
            out << item {
                out << "<div class='${attrs.type}'>"
                if (attrs.code) {
                    out << message(code: attrs.code)
                }
                out << body()
                out << "</div>"
            }
        } else {
//            attrs.code2 = attrs.code
            //            attrs.code = null
            out << msgbox(attrs, body)
        }
    }

    def menu = {attrs, body ->
        if (session.isMobile) {
            menuService.menu?.each {
                MainMenuItem menuItem ->

                //First, build a list of subItems that would
                //be rendered
                List<SubMenuItem> toRender = [];
                menuItem.subItems?.each {SubMenuItem subItem ->
                    if (subItem.requiredRoles?.size() > 0) {
                        if (SpringSecurityUtils.ifAllGranted(subItem.requiredRoles?.join())) {
                            toRender << subItem
                        }
                    } else {
                        toRender << subItem
                    }
                }


                if (toRender?.size() > 0) {


                    out << section(code: menuItem.labelCode) {
                        toRender?.each {SubMenuItem subItem ->

                            out << linker(attrs) {
                                out << link(controller: subItem.controller, action: subItem.action) {
                                    out << message(code: subItem.labelCode)
                                }
                            }
                        }
                    }
                }
            }


        } else {
            //out << menuItem(attrs, body)
        }

    }

    def linker = {attrs, body ->
        if (session.isMobile) {
            out << "<li class='menu linker'>"
            def bodyClosure = {
                if (attrs.img) {
                    String img = attrs.img
                    if(!img?.contains("images")) {
                        img = "/scoutinghub/images/${img}.png"
                    }
                    out << "<img src='${img}' />"
                }
                out << "<span class='name'>${body()}</span>"
                if (attrs.comment) {
                    out << "<span class='comment'>${attrs.comment}</span>"
                }
                out << "<span class='arrow'></span>"
            }

            if (attrs.controller || attrs.action) {
                out << link(attrs, bodyClosure)
            } else {
                out << bodyClosure()
            }

            out << "</li>"
        } else {
            out << link(attrs, body)
        }
    }

    def div = {attrs, body ->
        if (session.isMobile) {
            out << body()
        } else {
            out << "<div style='${attrs.style ?: ''}' class='${attrs.class ?: ''}'>${body()}</div>"
        }

    }


    def bigButton = {attrs, body ->
        if (session.isMobile) {
            out << linker(attrs) {
                out << body()
            }
        } else {
            out << "<div class='big-button-container'>"
            attrs.class = "${attrs.class ?: ""} big-button ui-state-active"
            out << link(attrs) {
                if (attrs.value) {
                    out << attrs.value
                } else {
                    out << body()
                }

            }
            out << "</div>"
        }
    }

    def radioItem = {attrs ->
        if (session.isMobile) {
            out << radio(attrs)
        } else {
            out << radio(attrs)
        }

    }

    def textField = {attrs, body->
        if(session.isMobile) {
            out << bigTextField(attrs, body)
        } else {
            out << property(attrs) {
                out << g.textField(attrs)
            }
        }

    }

    def bigTextField = {attrs, body ->
        def cssClass = attrs.class
        def code = attrs.code
        if (session.isMobile) {
            out << "<li class='bigfield'>"
            def type = attrs.type ?: "text"
            if (!attrs.otherAttrs) attrs.otherAttrs = [:]
            attrs.otherAttrs.placeholder = attrs.placeholder ?: message(code: attrs.code) ?: ""
            attrs.otherAttrs.value = attrs.value ?: ""
            attrs.otherAttrs.name = attrs.name

            out << f.txtField(attrs)
//            out << "<input placeholder='${attrs.placeholder}' name='${attrs.name}' type='${type}' value='${attrs.value ?: ""}' />"
            out << "</li>"
        } else {
            out << f.bigTextField(attrs, body)
        }
    }

    def unitSelector = {attrs ->
        if (session.isMobile) {
            out << bigTextField(attrs)
        } else {
            out << f.dynamicUnitSelector(attrs)
        }
    }

    def permission = {attrs ->
        Leader leader = attrs.leader
        Role role = attrs.role
        if (session.isMobile) {
//            out << pageItem { out << "Log in with a browser"}
        } else {
            out << checkBox(onclick: "togglePermission(this, ${leader?.id}, ${role?.id})", checked: leader.hasAuthority(role))
            out << message(code: "${role.authority}.label")
        }
    }

    def selecter = {attrs ->
        if (session.isMobile) {
            out << "<li class='select'>"
            out << select(attrs)
            out << "<span class='arrow'></span>"
            out << "</li>"
        } else {
            out << f.formControl(attrs) {
                out << select(attrs)
            }
        }

    }

    def section = {attrs, body ->
        if (session.isMobile) {
            def bodyEval = iwebkit.section(attrs, body)
            def header

            if (attrs.code) {
                header = message(code: attrs.code)
            } else if (request.sectionHeader) {
                header = request.sectionHeader
            }
            if (header) {
                out << "<span class='graytitle'>${header}</span>"
            }
            request.sectionHeader = null
            out << bodyEval
        } else {
            out << "<div class='section ${attrs.class ?: ""}'>"
            if (attrs.code) {
                if (attrs.header == "small") {
                    out << smallHeader { out << message(code: attrs.code) }
                } else {
                    out << header {
                        out << message(code: attrs.code)
                    }
                }

            }

            out << body()

            out << "</div>"

        }
    }

    def ctxmenu = {attrs, body ->
        if (session.isMobile) {

        } else {
            out << g.ctxmenu(attrs, body)
        }
    }


}
