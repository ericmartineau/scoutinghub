import scoutcert.Role
import scoutcert.Leader
import scoutcert.LeaderRole
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import scoutcert.ScoutGroup
import scoutcert.ScoutGroupType
import scoutcert.Certification
import scoutcert.ScoutGroup
import scoutcert.ScoutGroupType
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
            ScoutGroup council = new ScoutGroup(
                    groupType: ScoutGroupType.Council,
                    groupIdentifier: "grandcanyon",
                    groupLabel: "Grand Canyon Council"
            ).save(failOnError: true)

            ScoutGroup district1 = new ScoutGroup(
                    groupType: ScoutGroupType.District,
                    groupIdentifier: "baltic",
                    groupLabel: "Baltic District",
                    parent: council
            ).save(failOnError: true)

            council.addToChildGroups(district1)
            council.save(failOnError: true)

            ScoutGroup unit = new ScoutGroup(
                    groupType: ScoutGroupType.Unit,
                    groupIdentifier: "451",
                    groupLabel: "Unit 451",
                    parent: district1,
                    unitType: ScoutUnitType.Troop
            ).save(failOnError: true)
            district1.addToChildGroups(unit)
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

            unit.addToLeaderGroups([leader: admin, admin:true])
            unit.addToLeaderGroups([leader: leader, admin:true])
            unit.addToLeaderGroups([leader: newLeader, admin:true])
            unit.addToLeaderGroups([leader: newLeader2, admin:true])
            unit.save(failOnError:true)

            Certification fastStartTraining = new Certification(externalId: "faststart",
                                                  name: "Fast Start Training",
                                                  description: "The first step for any new adult volunteer in a Pack or Troop, no matter what the position may be. The Fast Start training is short and should be taken as soon as possible after you have accepted a leadership position.",
                                                  durationInDays: 1780,
                                                  tourPermitRequired: false);
            fastStartTraining.save(failOnError:true)

            Certification ypt = new Certification(externalId: "ypt",
                                                  name: "Youth Protection Training",
                                                  description: "YPT ensures boys involved in scouting are kept safe. At least one adult on every Scout outing must be Youth Protection Trained.",
                                                  durationInDays: 730,
                                                  tourPermitRequired: true);
            ypt.save(failOnError:true)

            Certification indoor = new Certification(externalId: "indoor",
                                                  name: "Leader Specific Training",
                                                  description: "Once a volunteer has a solid overview of the scouting program, training for a specific Scouting position continues through Leader Specific training. This training provides the specialized knowledge a new leader needs to assume a leadership role.",
                                                  durationInDays: 1780,
                                                  tourPermitRequired: true);
            indoor.save(failOnError:true)

            Certification outdoor = new Certification(externalId: "outdoor",
                                                  name: "Outdoor Leader Skills",
                                                  description: "Outdoor skills are critical to the success of the Scouting program. Training in outdoor skills is required before taking scouts on outings.",
                                                  durationInDays: 1780,
                                                  tourPermitRequired: true);
            outdoor.save(failOnError:true)

            Certification woodBadge = new Certification(externalId: "woodBadge",
                                                  name: "Wood Badge",
                                                  description: "Advanced training in leadership and team development, bringing together all programs - Cub Scouting, Boy Scouting, and Venturing.",
                                                  durationInDays: 1780,
                                                  tourPermitRequired: false);
            woodBadge.save(failOnError:true)

            Certification weather = new Certification(externalId: "weather",
                                                  name: "Hazardous Weather",
                                                  description: "planning and decision making regarding weather for a safe outing. Can be taken online at BSA Hazardous Weather",
                                                  durationInDays: 1780,
                                                  tourPermitRequired: true);
            weather.save(failOnError:true)

            Certification safeSwim = new Certification(externalId: "safeswim",
                                                  name: "Safe Swim Defense",
                                                  description: "Introduction to BSA water safety policies. Can be taken online at BSA Aquatics",
                                                  durationInDays: 730,
                                                  tourPermitRequired: true);
            safeSwim.save(failOnError:true)

            Certification safetyAfloat = new Certification(externalId: "safetyafloat",
                                                  name: "Safety Afloat",
                                                  description: "Introduction to BSA boating policies.  Water craft events required Safety Afloat trained leaders. Can be taken online at BSA Aquatics.",
                                                  durationInDays: 730,
                                                  tourPermitRequired: true);
            safetyAfloat.save(failOnError:true)

            Certification climbOn = new Certification(externalId: "climbon",
                                                  name: "Climb On Safely",
                                                  description: "This training lasts about 45 minutes and provides all the information you need to meet the minimum requirements for a climbing activity. This does not consist of any training or certifications in climbing, but provides the essential components of a safe outing.",
                                                  durationInDays: 730,
                                                  tourPermitRequired: true);
            climbOn.save(failOnError:true)

            Certification cpr = new Certification(externalId: "cpr",
                                                  name: "CPR Certification",
                                                  description: "Normally not offered through a BSA unit, but at least two currently CPR certified adults are required on many high adventure outings.",
                                                  durationInDays: 730,
                                                  tourPermitRequired: false);
            cpr.save(failOnError:true)

            Certification thisIsScouting = new Certification(externalId: "thisisscouting",
                                                  name: "This is Scouting",
                                                  description: "Not sure what the description is or how long it takes",
                                                  durationInDays: 730,
                                                  tourPermitRequired: false);
            thisIsScouting.save(failOnError:true)

        }


        SpringSecurityUtils.clientRegisterFilter 'facebookAuthenticationFilter',
                SecurityFilterPosition.OPENID_FILTER.order - 10

    }

    def destroy = {
    }
}
