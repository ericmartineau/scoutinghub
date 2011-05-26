package scoutinghub.menu;

/**
 * User: eric
 * Date: 3/12/11
 * Time: 12:35 PM
 */
public class SubMenuItem extends MenuItem {
    public SubMenuItem(String controller, String action, String labelCode, String... requiredRoles) {
        super(controller, action, labelCode, requiredRoles);
    }

    public SubMenuItem(String controller, String labelCode, String... requiredRoles) {
        super(controller, labelCode, requiredRoles);
    }

    private MainMenuItem mainMenuItem;

    public MainMenuItem getMainMenuItem() {
        return mainMenuItem;
    }

    public void setMainMenuItem(MainMenuItem mainMenuItem) {
        this.mainMenuItem = mainMenuItem;
    }
}
