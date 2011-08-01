package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.Role

/**
 * User: ericm
 * Date: 7/31/11
 * Time: 8:48 PM
 */
class SeedRolesService implements SeedScript {
    int getOrder() {
        return 0  //First script
    }

    void execute() {
        def defaultRoleNames = ["ROLE_LEADER", "ROLE_ADMIN"]
        def existingRoleNames = Role.list()?.collect {it.authority}

        defaultRoleNames.each {
            if (!existingRoleNames.contains(it)) {
                Role role = new Role(authority: it)
                role.save(failOnError: true)
            }
        }
    }

    String getName() {
        return "seedRoles"
    }


}
