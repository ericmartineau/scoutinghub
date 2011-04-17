package scoutcert

import webkit.IphoneTagLib
import scoutcert.menu.MenuItem
import scoutcert.menu.MainMenuItem
import scoutcert.menu.SubMenuItem
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
        out << "<ul>${body()}</ul>"
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

        } else {
            Address addr = attrs.address
            String addrString = "${addr.address} ${addr.city}, ${addr.state} ${addr.zip}"
            out << "<a href=\"javascript:showMap('${addrString}')\">${addrString}</a>"
        }
    }

    def property = {attrs, body ->
        if (session.isMobile) {
            out << "<li class='smallfield'><span class='name'>${message(code: attrs.code)}</span><span class='value'>${body()}</span></li>"
        } else {
            out << "<li class='prop'>"
            if (attrs.code) {
                out << "<div class='propLabel'>"
                out << message(code: attrs.code)
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
            out << body()

        }
    }

    def smallHeader = {attrs, body ->
        if (session.isMobile) {
            out << "<span class='graytitle'>${body()}</span>"
        } else {
            out << "<h2>${body()}</h2>"
        }

    }

    def form = {attrs, body ->
        out << body()
    }

    def leaderTraining = {attrs ->
        if (session.isMobile) {
            LeaderCertificationInfo certificationInfo = attrs.certificationInfo
            def editLink
            if (certificationInfo?.leaderCertification) {
                editLink = link(id: certificationInfo?.leaderCertification?.id, controller: 'leaderCertification', action: 'quickEdit') {
                    certificationInfo?.certification?.name
                }
            } else {
                def params = [
                        'leader.id': certificationInfo.leader.id,
                        'certification.id': certificationInfo.certification.id
                ]
                editLink = link(params: params, controller: 'leaderCertification', action: 'quickCreate') {
                    certificationInfo?.certification?.name
                }
            }

            out << linker(comment: certificationInfo.certificationStatus) { editLink }
        } else {
            out << f.leaderTraining(certificationInfo: attrs.certificationInfo)
        }
    }

    def submit = {attrs, body ->
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

//            out << property(attrs) {

            out << """<li class='checkbox'>
                    <span class='name'>${message(code: attrs.code)}</span>
                    <input type='checkbox' name='${attrs.name}' value='yes' />
                    </li>"""
//                out << iwebkit.checkbox(attrs, body)
//            }

        } else {
            out << "<div class='${attrs.class}'>"
            out << checkBox(attrs)
            out << message(code: attrs.code)
            out << "</div>"
        }
    }

    def msg = {attrs, body ->
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
            out << "<li class='menu'>"
            out << "<span class='name'>${body()}</span>"
            if (attrs.comment) {
                out << "<span class='comment'>${attrs.comment}</span>"
            }
            out << "<span class='arrow'></span>"
            out << "</li>"

        } else {

            out << link(attrs, body)

        }
    }

    def div = {attrs, body ->
        if (session.isMobile) {
            out << body()
        } else {
            out << "<div class='${attrs.class}'>${body()}</div>"
        }

    }


    def bigButton = {attrs, body ->
        if (session.isMobile) {
            out << linker(attrs) {
                out << link(attrs) {attrs.value}
            }
        } else {
            out << "<div class='big-button-container'>"
            attrs.class = "${attrs.class?:""} big-button ui-state-active"
            out << link(attrs) {
                if(attrs.value) {
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

    def bigTextField = {attrs, body ->
        if (session.isMobile) {
            out << "<li class='bigfield'>"
            def type = attrs.type ?: "text"
            out << "<input placeholder='${attrs.placeholder}' name='${attrs.name}' type='${type}' value='${attrs.value ?: ""}' />"
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

    def permission = {attrs->
        Leader leader = attrs.leader
        Role role = attrs.role
        if(session.isMobile) {
            out << pageItem { out << "Log in with a browser"}
        } else {
            out << checkBox(onclick:"togglePermission(this, ${leader?.id}, ${role?.id})", checked:leader.hasAuthority(role))
            out << message(code:"${role.authority}.label")
        }
    }

    def selecter = {attrs ->
        if (session.isMobile) {
            out << "<li class='select'>"
            out << select(attrs)
            out << "<span class='arrow'></span>"
            out << "</li>"
        } else {
//            out << "<div class='selecter-container'>"
            out << f.formControl(attrs) {
                out << select(attrs)
            }
//            out << "</div>"
        }

    }



    def section = {attrs, body ->
        if (session.isMobile) {

            if (attrs.code) {
                out << "<span class='graytitle'>${message(code: attrs.code)}</span>"
            }
            out << iwebkit.section(attrs, body)
        } else {
            out << "<div class='section ${attrs.class ?: "singleColumn"}'>"
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

//            out << "<div style=\"clear:both; height:1px; margin-top:-1px;\" ><!— —></div>"
//            out << "<div style='clear:both;'></div>"
            out << "</div>"
//            out << "<br style='font-size: 1px; line-height: 0; height: 0; clear: both' />"


        }
    }


}
