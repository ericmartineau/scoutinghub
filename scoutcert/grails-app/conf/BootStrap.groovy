import scoutcert.Role
import scoutcert.Leader
import scoutcert.LeaderRole
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import scoutcert.ScoutUnit
import scoutcert.ScoutUnitType

class BootStrap {

    SpringSecurityService springSecurityService

    def init = { servletContext ->
        def defaultRoleNames = ["ROLE_LEADER", "ROLE_UNITADMIN", "ROLE_ADMIN"]
        def existingRoleNames = Role.list()?.collect {it.authority}

        defaultRoleNames.each {
            if (!existingRoleNames.contains(it)) {
                Role role = new Role(authority: it)
                role.save(failOnError: true)
            }
        }


        if (Leader.list()?.size() == 0) {
            ScoutUnit council = new ScoutUnit(
                    unitType: ScoutUnitType.Council,
                    unitIdentifier: "grandcanyon",
                    unitLabel: "Grand Canyon Council"
            ).save(failOnError: true)

            ScoutUnit district1 = new ScoutUnit(
                    unitType: ScoutUnitType.District,
                    unitIdentifier: "baltic",
                    unitLabel: "Baltic District",
                    parent: council
            ).save(failOnError: true)

            council.addToChildUnits(district1)
            council.save(failOnError: true)

            ScoutUnit unit = new ScoutUnit(
                    unitType: ScoutUnitType.Unit,
                    unitIdentifier: "451",
                    unitLabel: "Unit 451",
                    parent: district1
            ).save(failOnError: true)
            district1.addToChildUnits(unit)
            district1.save(failOnError: true)

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

            Leader unitAdmin = new Leader(enabled: true)
            unitAdmin.username = "unitadmin"
            unitAdmin.password = springSecurityService.encodePassword("890iop")
            unitAdmin.firstName = "Unit Admin"
            unitAdmin.lastName = "Leader"
            unitAdmin.enabled = true
            unitAdmin.addToMyScoutingIds(myScoutingIdentifier: "123456")
            unitAdmin.email = "unitAdmin@email.com"

            unitAdmin.save(failOnError: true)
            LeaderRole.create(unitAdmin, Role.findByAuthority("ROLE_UNITADMIN"), true)

            Leader leader = new Leader(enabled: true)
            leader.username = "leader"
            leader.firstName = "Regular"
            leader.lastName = "Leader"
            leader.password = springSecurityService.encodePassword("890iop")
            leader.passwordExpired = false
            leader.accountLocked = false
            leader.accountExpired = false
            leader.email = "leader@email.com"
            leader.save(failOnError: true)
            LeaderRole.create(leader, Role.findByAuthority("ROLE_LEADER"), true)

            Leader newLeader = new Leader(enabled: false)
            newLeader.username = "newLeader"
            newLeader.password = springSecurityService.encodePassword("890iop")
            newLeader.passwordExpired = false
            newLeader.accountLocked = false
            newLeader.accountExpired = false
            newLeader.firstName = "New"
            newLeader.lastName = "Leader"
            newLeader.email = "newleader@email.com"
            newLeader.addToMyScoutingIds(myScoutingIdentifier: "54321")
            newLeader.save(failOnError: true)
            LeaderRole.create(newLeader, Role.findByAuthority("ROLE_LEADER"), true)

            Leader newLeader2 = new Leader(enabled: false)
            newLeader2.username = "newLeader2"
            newLeader2.password = springSecurityService.encodePassword("890iop")
            newLeader2.passwordExpired = false
            newLeader2.accountLocked = false
            newLeader2.accountExpired = false
            newLeader2.firstName = "Mrs. New"
            newLeader2.lastName = "Leader"
            newLeader2.email = "newleader@email.com"
            newLeader2.addToMyScoutingIds(myScoutingIdentifier: "4321")
            newLeader2.save(failOnError: true)
            LeaderRole.create(newLeader2, Role.findByAuthority("ROLE_LEADER"), true)

            unit.addToLeaders(admin)
            unit.addToLeaders(leader)
            unit.addToLeaders(newLeader)
            unit.addToLeaders(newLeader2)
            unit.save(failOnError:true)
        }


        SpringSecurityUtils.clientRegisterFilter 'facebookAuthenticationFilter',
                SecurityFilterPosition.OPENID_FILTER.order - 10

    }

    def destroy = {
    }
}
