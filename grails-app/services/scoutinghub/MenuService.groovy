package scoutinghub

import scoutinghub.menu.MenuItem
import javax.annotation.PostConstruct
import scoutinghub.menu.MainMenuItem
import scoutinghub.menu.SubMenuItem

class MenuService {

    Map<String, MainMenuItem> menuItems = [:]

    Collection<MainMenuItem> getMenu() {
        return menuItems.values()
    }

    void addMenuItem(MainMenuItem menuItem) {
        menuItems[menuItem.labelCode] = menuItem
    }

    MainMenuItem getMenuItem(String menuItemCode) {
        return menuItems[menuItemCode];
    }

    @PostConstruct
    public void buildDefaultMenu() {
        def leaderMenu = new MainMenuItem("leader", "menu.leader.index", "ROLE_LEADER")
//            .addMenuItem(new SubMenuItem("leader", "profile", "menu.leader.profile", "ROLE_LEADER"))
//            .addMenuItem(new SubMenuItem("leader", "training", "menu.leader.training", "ROLE_LEADER"))
        addMenuItem(leaderMenu)

        def trainingMenu = new MainMenuItem("training", "menu.training.index", "ROLE_ADMIN")
//                .addMenuItem(new SubMenuItem("training", "individual", "menu.training.individual", "ROLE_LEADER"))
                .addMenuItem(new SubMenuItem("certificationClass", "index", "menu.trainingEvent.index", "ROLE_ADMIN"))
                .addMenuItem(new SubMenuItem("training", "trainingReport", "menu.training.report", "ROLE_LEADER"))
//                .addMenuItem(new SubMenuItem("programCertification", "index", "menu.trainingDefinition.index", "ROLE_ADMIN"))
                .addMenuItem(new SubMenuItem("certification", "index", "menu.trainingDefinition.index", "ROLE_ADMIN"))
//                .addMenuItem(new SubMenuItem("leaderCertification", "index", "menu.trainingLeader.index", "ROLE_ADMIN"))
                .addMenuItem(new SubMenuItem("training", "importTraining", "menu.training.import", "ROLE_ADMIN"))

        addMenuItem(trainingMenu)

//        def unitAdminMenu = new MainMenuItem("unitAdmin", "menu.unitAdmin.index", "ROLE_UNITADMIN")
//            .addMenuItem(new SubMenuItem("unitAdmin", "leaders", "menu.unitAdmin.leaders", "ROLE_UNITADMIN"))
//        addMenuItem(unitAdminMenu)
//
//        def adminMenu = new MainMenuItem("admin", "menu.admin.setup", "ROLE_ADMIN")
//            .addMenuItem(new SubMenuItem("permissions", "index", "menu.permissions.index", "ROLE_ADMIN"))
//        addMenuItem(adminMenu)

    }

}