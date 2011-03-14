import scoutcert.Role
import scoutcert.Leader
import scoutcert.LeaderRole
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition

class BootStrap {

    SpringSecurityService springSecurityService

    def init = { servletContext ->
        def defaultRoleNames = ["ROLE_LEADER", "ROLE_UNITADMIN", "ROLE_ADMIN"]
        def existingRoleNames = Role.list()?.collect {it.authority}

        defaultRoleNames.each {
            if (!existingRoleNames.contains(it)) {
                Role role = new Role(authority: it)
                role.save(failOnError:true)
            }
        }

        if (Leader.list()?.size() == 0) {
            Leader admin = new Leader(enabled: true)
            admin.username = "admin"
            admin.password = springSecurityService.encodePassword("890iop")
            admin.passwordExpired = false
            admin.accountLocked = false
            admin.accountExpired = false
            admin.email = "admin@email.com"
            admin.save(failOnError:true)
            LeaderRole.create(admin, Role.findByAuthority("ROLE_ADMIN"), true)

            Leader unitAdmin = new Leader(enabled: true)
            unitAdmin.username = "unitadmin"
            unitAdmin.password = springSecurityService.encodePassword("890iop")
            unitAdmin.enabled = true
            unitAdmin.addToMyScoutingIds(myScoutingIdentifier:"123456")
            unitAdmin.email = "unitAdmin@email.com"

            unitAdmin.save(failOnError:true)
            LeaderRole.create(unitAdmin, Role.findByAuthority("ROLE_UNITADMIN"), true)

            Leader leader = new Leader(enabled: true)
            leader.username = "leader"
            leader.password = springSecurityService.encodePassword("890iop")
            leader.passwordExpired = false
            leader.accountLocked = false
            leader.accountExpired = false
            leader.email = "leader@email.com"
            leader.save(failOnError:true)
            LeaderRole.create(leader, Role.findByAuthority("ROLE_LEADER"), true)

        }


		SpringSecurityUtils.clientRegisterFilter 'facebookAuthenticationFilter',
				SecurityFilterPosition.OPENID_FILTER.order - 10

    }

    def destroy = {
    }
}
