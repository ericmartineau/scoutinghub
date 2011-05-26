package scoutinghub

import scoutinghub.menu.MenuItem
import scoutinghub.menu.MainMenuItem
import scoutinghub.menu.SubMenuItem
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class MenuTagLib {

    MenuService menuService

    SpringSecurityService springSecurityService

    def menu = {attrs ->
        out << render(template: "/layouts/menu", model: [menuItems: menuService.menu])
    }

    def menuItem = {attrs ->

        String currController = attrs.controller;
        String currAction = attrs.action;

        MenuItem menuItem = attrs.menuItem;
        String isCurr = (menuItem.isCurrentMenuItem(currController, currAction)) ? "class='on'" : "";

        out << "<li ${isCurr}>${link(controller: menuItem.controller, action: menuItem.action ?: "index") { message(code: menuItem.labelCode)}}</li>"
    }

    def subMenu = {attrs ->

        //Find currently selected main menu items
        menuService.menu?.each {MainMenuItem mainMenuItem ->
            if (mainMenuItem.isCurrentMenuItem(attrs.controller, attrs.action) && mainMenuItem.subItems?.size() > 0) {
                out << '<div id="top-nav"><ul>'
                mainMenuItem.subItems?.each {
                    SubMenuItem subItem ->
                    if(SpringSecurityUtils.ifAllGranted(subItem.requiredRoles?.join())) {
                        out << menuItem(controller: attrs.controller, action: attrs.controller, menuItem: subItem)
                    }
                }
                out << '</ul></div>'
            }
        }
    }

}
