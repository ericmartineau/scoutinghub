package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.Role
import scoutinghub.LeaderRole
import scoutinghub.Leader
import grails.plugins.springsecurity.SpringSecurityService
import scoutinghub.ScoutGroup
import scoutinghub.LeaderPositionType

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 7/31/11
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
class SeedAdminUserService implements SeedScript {
    SpringSecurityService springSecurityService

    int getOrder() {
        return 3
    }

    void execute() {
        if (!Leader.findByUsername("admin")) {

            Leader admin = new Leader(enabled: true)
            admin.username = "admin"
            admin.password = springSecurityService.encodePassword("890iop")
            admin.passwordExpired = false
            admin.accountLocked = false
            admin.accountExpired = false
            admin.firstName = "Admin"
            admin.lastName = "Leader"
            admin.email = "admin@email.com"
            admin.save(failOnError: true)
            LeaderRole.create(admin, Role.findByAuthority("ROLE_ADMIN"), true)

            //Add to grand canyon council
            ScoutGroup council = ScoutGroup.findByGroupIdentifier("grandcanyon")
            council.addToLeaderGroups([leader: admin, leaderPosition: LeaderPositionType.Executive, admin: true])
            council.save(failOnError: true)
        }


    }

    String getName() {
        return "seedAdminUser"
    }


}
