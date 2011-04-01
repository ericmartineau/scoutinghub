import scoutcert.Role
import scoutcert.Leader
import scoutcert.LeaderRole
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import scoutcert.ScoutGroup
import scoutcert.ScoutGroupType
import scoutcert.Certification
import scoutcert.ProgramCertification
import scoutcert.ScoutGroup
import scoutcert.ScoutGroupType
import scoutcert.ScoutUnitType
import scoutcert.LeaderPositionType

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

            ScoutGroup unit2 = new ScoutGroup(
                    groupType: ScoutGroupType.Unit,
                    groupIdentifier: "452",
                    groupLabel: "Unit 452",
                    parent: district1,
                    unitType: ScoutUnitType.Troop
            ).save(failOnError: true)
            district1.addToChildGroups(unit2)

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

            unit.addToLeaderGroups([leader: admin, position: LeaderPositionType.Scoutmaster,admin:true])
            unit.addToLeaderGroups([leader: leader, position: LeaderPositionType.AssistantScoutMaster, admin:true])
            unit.addToLeaderGroups([leader: newLeader, position: LeaderPositionType.CommitteeChair, admin:true])
            unit.addToLeaderGroups([leader: newLeader2, position: LeaderPositionType.CharterRep, admin:true])
            unit.save(failOnError:true)

            unit2.addToLeaderGroups([leader: admin, position: LeaderPositionType.CommitteeChair,admin:true])
            unit2.save(failOnError:true)

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

            Certification baloo = new Certification(externalId: "baloo",
                                                  name: "BALOO - Basic Adult Leader Outdoor Orientation",
                                                  description: "for Cub Scout leaders is a one-day training event that introduces participants to the skills needed to plan and conduct Pack outdoor activities, particularly pack camping.",
                                                  durationInDays: 1780,
                                                  tourPermitRequired: true);
            baloo.save(failOnError:true)

            Certification woodbadge = new Certification(externalId: "woodBadge",
                                                  name: "Wood Badge",
                                                  description: "Advanced training in leadership and team development, bringing together all programs - Cub Scouting, Boy Scouting, and Venturing.",
                                                  durationInDays: 1780,
                                                  tourPermitRequired: false);
            woodbadge.save(failOnError:true)

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

            // Fast Start required for all
            ProgramCertification packFastStart = new ProgramCertification(unitType: ScoutUnitType.Pack,
                                                  certification:fastStartTraining,
                                                  positionType: null, required:true);
            packFastStart.save(failOnError:true);

            ProgramCertification troopFastStart = new ProgramCertification(unitType: ScoutUnitType.Troop,
                                                  certification:fastStartTraining,
                                                  positionType: null, required:true);
            troopFastStart.save(failOnError:true);

            ProgramCertification teamFastStart = new ProgramCertification(unitType: ScoutUnitType.Team,
                                                  certification:fastStartTraining,
                                                  positionType: null, required:true);
            teamFastStart.save(failOnError:true);

            ProgramCertification crewFastStart = new ProgramCertification(unitType: ScoutUnitType.Crew,
                                                  certification:fastStartTraining,
                                                  required:true);
            crewFastStart.save(failOnError:true);

            // YPT required for all
            ProgramCertification packYPT = new ProgramCertification(unitType: ScoutUnitType.Pack,
                                                  certification:ypt,
                                                  required:true);
            packYPT.save(failOnError:true);

            ProgramCertification troopYPT = new ProgramCertification(unitType: ScoutUnitType.Troop,
                                                  certification:ypt,
                                                  required:true);
            troopYPT.save(failOnError:true);

            ProgramCertification teamYPT = new ProgramCertification(unitType: ScoutUnitType.Team,
                                                  certification:ypt,
                                                  required:true);
            teamYPT.save(failOnError:true);

            ProgramCertification crewYPT = new ProgramCertification(unitType: ScoutUnitType.Crew,
                                                  certification:ypt,
                                                  required:true);
            crewYPT.save(failOnError:true);

            // Leader Specific required for all
            ProgramCertification packLeaderSpecific = new ProgramCertification(unitType: ScoutUnitType.Pack,
                                                  certification:indoor,
                                                  required:true);
            packLeaderSpecific.save(failOnError:true);


            ProgramCertification troopLeaderSpecific = new ProgramCertification(unitType: ScoutUnitType.Troop,
                                                  certification:indoor,
                                                  required:true);
            troopLeaderSpecific.save(failOnError:true);

            ProgramCertification teamLeaderSpecific = new ProgramCertification(unitType: ScoutUnitType.Team,
                                                  certification:indoor,
                                                  required:true);
            teamLeaderSpecific.save(failOnError:true);

            ProgramCertification crewLeaderSpecific = new ProgramCertification(unitType: ScoutUnitType.Crew,
                                                  certification:indoor,
                                                  required:true);
            crewLeaderSpecific.save(failOnError:true);

            // Baloo.  Tour permit required for pack, but not required for pack
            ProgramCertification packBaloo = new ProgramCertification(unitType: ScoutUnitType.Pack,
                                                  certification:baloo,
                                                  required:false);
            packBaloo.save(failOnError:true)


            //Outdoor.  Required
            ProgramCertification troopOutdoor = new ProgramCertification(unitType: ScoutUnitType.Troop,
                                                  certification:outdoor,
                                                  required:true);
            troopOutdoor.save(failOnError:true)

            ProgramCertification teamOutdoor = new ProgramCertification(unitType: ScoutUnitType.Team,
                                                  certification:outdoor,
                                                  required:true);
            teamOutdoor.save(failOnError:true)

            ProgramCertification crewOutdoor = new ProgramCertification(unitType: ScoutUnitType.Crew,
                                                  certification:outdoor,
                                                  required:true);
            crewOutdoor.save(failOnError:true)

            // This Is Scouting required for all
            ProgramCertification packThisIsScouting = new ProgramCertification(unitType: ScoutUnitType.Pack,
                                                  certification:thisIsScouting,
                                                  required:true);
            packThisIsScouting.save(failOnError:true);

            ProgramCertification troopThisIsScouting = new ProgramCertification(unitType: ScoutUnitType.Troop,
                                                  certification:thisIsScouting,
                                                  required:true);
            troopThisIsScouting.save(failOnError:true);

            ProgramCertification teamThisIsScouting = new ProgramCertification(unitType: ScoutUnitType.Team,
                                                  certification:thisIsScouting,
                                                  required:true);
            teamThisIsScouting.save(failOnError:true);

            ProgramCertification crewThisIsScouting = new ProgramCertification(unitType: ScoutUnitType.Crew,
                                                  certification:thisIsScouting,
                                                  required:true);
            crewThisIsScouting.save(failOnError:true);

            // Hazardous Weather, tour permit required only
            ProgramCertification packWeather = new ProgramCertification(unitType: ScoutUnitType.Pack,
                                                  certification:weather,
                                                  required:false);
            packWeather.save(failOnError:true);

            ProgramCertification troopWeather = new ProgramCertification(unitType: ScoutUnitType.Troop,
                                                  certification:weather,
                                                  required:false);
            troopWeather.save(failOnError:true);

            ProgramCertification teamWeather = new ProgramCertification(unitType: ScoutUnitType.Team,
                                                  certification:weather,
                                                  required:false);
            teamWeather.save(failOnError:true);

            ProgramCertification crewWeather = new ProgramCertification(unitType: ScoutUnitType.Crew,
                                                  certification:weather,
                                                  required:false);
            crewWeather.save(failOnError:true);

            //Woodbadge.  Required for none
            ProgramCertification packWoodbadge = new ProgramCertification(unitType: ScoutUnitType.Pack,
                                                  certification:woodbadge,
                                                  required:false);
            packWoodbadge.save(failOnError:true)
            ProgramCertification troopWoodbadge = new ProgramCertification(unitType: ScoutUnitType.Troop,
                                                  certification:woodbadge,
                                                  required:false);
            troopWoodbadge.save(failOnError:true)

            ProgramCertification teamWoodbadge = new ProgramCertification(unitType: ScoutUnitType.Team,
                                                  certification:woodbadge,
                                                  required:false);
            teamWoodbadge.save(failOnError:true)

            ProgramCertification crewWoodbadge = new ProgramCertification(unitType: ScoutUnitType.Crew,
                                                  certification:woodbadge,
                                                  positionType: null,required:false);
            crewWoodbadge.save(failOnError:true)

        }


        SpringSecurityUtils.clientRegisterFilter 'facebookAuthenticationFilter',
                SecurityFilterPosition.OPENID_FILTER.order - 10

    }

    def destroy = {
    }
}
