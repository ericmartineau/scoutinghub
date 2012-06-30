package scoutinghub

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
        addMenuItem(leaderMenu)

        def trainingMenu = new MainMenuItem("training", "menu.training.index", {Leader leader-> leader?.canAdminAtLeastOneUnit()})
//                .addMenuItem(new SubMenuItem("training", "individual", "menu.training.individual", "ROLE_LEADER"))
                .addMenuItem(new SubMenuItem("certificationClass", "index", "menu.trainingEvent.index", "ROLE_ADMIN"))
                .addMenuItem(new SubMenuItem("training", "trainingReport", "menu.training.report", "ROLE_LEADER"))
                .addMenuItem(new SubMenuItem("programCertification", "index", "menu.trainingProgramDefinition.index", "ROLE_ADMIN"))
                .addMenuItem(new SubMenuItem("certification", "index", "menu.trainingDefinition.index", "ROLE_ADMIN"))
//                .addMenuItem(new SubMenuItem("leaderCertification", "index", "menu.trainingLeader.index", "ROLE_ADMIN"))
                .addMenuItem(new SubMenuItem("training", "importTraining", "menu.training.import", "ROLE_ADMIN"))
                .addMenuItem(new SubMenuItem("training", "simpleImportTraining", "menu.training.simpleImport", "ROLE_ADMIN"))

        addMenuItem(trainingMenu)

        def unitsMenu = new MainMenuItem("scoutGroup", "menu.scoutGroup.index", {Leader leader-> leader?.canAdminAtLeastOneUnit()})
            .addMenuItem(new SubMenuItem("leader", "create", "menu.leader.create", "ROLE_LEADER"))
            .addMenuItem(new SubMenuItem("unitAdmin", "importUnits", "menu.units.import", "ROLE_ADMIN"))
        addMenuItem(unitsMenu)
        def mbMenu = new MainMenuItem("meritBadgeCounselor", "menu.meritBadgeCounselor.index", "ROLE_LEADER")
                .addMenuItem(new SubMenuItem("meritBadgeCounselor", "list", "menu.meritBadgeCounselor.list", "ROLE_MERITBADGE_ADMIN"))
                .addMenuItem(new SubMenuItem("meritBadgeCounselor", "find", "menu.meritBadgeCounselor.find"))

        addMenuItem(mbMenu)
    }

}
