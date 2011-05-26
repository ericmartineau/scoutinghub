import grails.plugins.springsecurity.SpringSecurityService
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import scoutinghub.*

class BootStrap {

    ScoutGroupService scoutGroupService
    SpringSecurityService springSecurityService

    def init = { servletContext ->
        SpringSecurityUtils.clientRegisterFilter 'facebookAuthenticationFilter',
                        SecurityFilterPosition.OPENID_FILTER.order - 10

        def defaultRoleNames = ["ROLE_LEADER", "ROLE_ADMIN"]
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
            ScoutUnitType crew = ScoutUnitType.Crew
            ScoutUnitType troop = ScoutUnitType.Troop


            Certification packFastStartTraining = new Certification(externalId: "CF3",
                    name: "Cubmaster Fast Start Training",
                    description: "The first step for any new adult volunteer in a Pack or Troop, no matter what the position may be. The Fast Start training is short and should be taken as soon as possible after you have accepted a leadership position.",
                    durationInDays: 1780,
                    tourPermitRequired: false);
            packFastStartTraining.save(failOnError: true)

            Certification troopFastStartTraining = new Certification(externalId: "SFS",
                    name: "Boy Scout Leader Fast Start Training",
                    description: "The first step for any new adult volunteer in a Pack or Troop, no matter what the position may be. The Fast Start training is short and should be taken as soon as possible after you have accepted a leadership position.",
                    durationInDays: 1780,
                    tourPermitRequired: false);
            troopFastStartTraining.save(failOnError: true)

            Certification teamFastStartTraining = new Certification(externalId: "VFS",
                    name: "Varsity Scout Fast Start Training",
                    description: "The first step for any new adult volunteer in a Pack or Troop, no matter what the position may be. The Fast Start training is short and should be taken as soon as possible after you have accepted a leadership position.",
                    durationInDays: 1780,
                    tourPermitRequired: false);
            teamFastStartTraining.save(failOnError: true)

            Certification crewFastStartTraining = new Certification(externalId: "PFS",
                    name: "Venturing Fast Start Training",
                    description: "The first step for any new adult volunteer in a Pack or Troop, no matter what the position may be. The Fast Start training is short and should be taken as soon as possible after you have accepted a leadership position.",
                    durationInDays: 1780,
                    tourPermitRequired: false);
            crewFastStartTraining.save(failOnError: true)


            Certification ypt = new Certification(externalId: "Y01",
                    name: "Youth Protection Training",
                    description: "YPT ensures boys involved in scouting are kept safe. At least one adult on every Scout outing must be Youth Protection Trained.",
                    durationInDays: 730,
                    tourPermitRequired: true);
            ypt.save(failOnError: true)

            Certification vYpt = new Certification(externalId: "Y02",
                    name: "Venturing Youth Protection Training",
                    description: "Venturing is a coed program  Venturing YPT ensures the youth involved in venturing are kept safe. At least one adult on every Venturing outing must be Venture Youth Protection Trained.",
                    durationInDays: 730,
                    tourPermitRequired: true);
            vYpt.save(failOnError: true)

            Certification packLeaderSpecific = new Certification(externalId: "C40",
                    name: "Cubmaster Leader Specific Training",
                    description: "Once a volunteer has a solid overview of the scouting program, training for a specific Scouting position continues through Leader Specific training. This training provides the specialized knowledge a new leader needs to assume a leadership role.",
                    durationInDays: 1780,
                    tourPermitRequired: true);
            packLeaderSpecific.save(failOnError: true)

            Certification troopLeaderSpecific = new Certification(externalId: "S24",
                    name: "Scoutmaster Specific Training",
                    description: "Once a volunteer has a solid overview of the scouting program, training for a specific Scouting position continues through Leader Specific training. This training provides the specialized knowledge a new leader needs to assume a leadership role.",
                    durationInDays: 1780,
                    tourPermitRequired: true);
            troopLeaderSpecific.save(failOnError: true)

            Certification teamLeaderSpecific = new Certification(externalId: "V21",
                    name: "Varsity Coach Leader Specific Training",
                    description: "Once a volunteer has a solid overview of the scouting program, training for a specific Scouting position continues through Leader Specific training. This training provides the specialized knowledge a new leader needs to assume a leadership role.",
                    durationInDays: 1780,
                    tourPermitRequired: true);
            teamLeaderSpecific.save(failOnError: true)

            Certification crewLeaderSpecific = new Certification(externalId: "P21",
                    name: "Venturing Leader Specific Training",
                    description: "Once a volunteer has a solid overview of the scouting program, training for a specific Scouting position continues through Leader Specific training. This training provides the specialized knowledge a new leader needs to assume a leadership role.",
                    durationInDays: 1780,
                    tourPermitRequired: true);
            crewLeaderSpecific.save(failOnError: true)


            Certification outdoor = new Certification(externalId: "S11",
                    name: "Outdoor Leader Skills",
                    description: "Outdoor skills are critical to the success of the Scouting program. Training in outdoor skills is required before taking scouts on outings.",
                    durationInDays: 1780,
                    tourPermitRequired: true);
            outdoor.save(failOnError: true)

            Certification baloo = new Certification(externalId: "baloo",
                    name: "BALOO - Basic Adult Leader Outdoor Orientation",
                    description: "for Cub Scout leaders is a one-day training event that introduces participants to the skills needed to plan and conduct Pack outdoor activities, particularly pack camping.",
                    durationInDays: 1780,
                    tourPermitRequired: true);
            baloo.save(failOnError: true)

            Certification woodbadge = new Certification(externalId: "woodBadge",
                    name: "Wood Badge",
                    description: "Advanced training in leadership and team development, bringing together all programs - Cub Scouting, Boy Scouting, and Venturing.",
                    durationInDays: 1780,
                    tourPermitRequired: false);
            woodbadge.save(failOnError: true)

            Certification weather = new Certification(externalId: "weather",
                    name: "Hazardous Weather",
                    description: "planning and decision making regarding weather for a safe outing. Can be taken online at BSA Hazardous Weather",
                    durationInDays: 1780,
                    tourPermitRequired: true);
            weather.save(failOnError: true)

            Certification safeSwim = new Certification(externalId: "safeswim",
                    name: "Safe Swim Defense",
                    description: "Introduction to BSA water safety policies. Can be taken online at BSA Aquatics",
                    durationInDays: 730,
                    tourPermitRequired: true);
            safeSwim.save(failOnError: true)

            Certification safetyAfloat = new Certification(externalId: "safetyafloat",
                    name: "Safety Afloat",
                    description: "Introduction to BSA boating policies.  Water craft events required Safety Afloat trained leaders. Can be taken online at BSA Aquatics.",
                    durationInDays: 730,
                    tourPermitRequired: true);
            safetyAfloat.save(failOnError: true)

            Certification climbOn = new Certification(externalId: "climbon",
                    name: "Climb On Safely",
                    description: "This training lasts about 45 minutes and provides all the information you need to meet the minimum requirements for a climbing activity. This does not consist of any training or certifications in climbing, but provides the essential components of a safe outing.",
                    durationInDays: 730,
                    tourPermitRequired: true);
            climbOn.save(failOnError: true)

            Certification cpr = new Certification(externalId: "cpr",
                    name: "CPR Certification",
                    description: "Normally not offered through a BSA unit, but at least two currently CPR certified adults are required on many high adventure outings.",
                    durationInDays: 730,
                    tourPermitRequired: false);
            cpr.save(failOnError: true)

            Certification thisIsScouting = new Certification(externalId: "WA01",
                    name: "This is Scouting",
                    description: "Not sure what the description is or how long it takes",
                    durationInDays: 730,
                    tourPermitRequired: false);
            thisIsScouting.save(failOnError: true)

            CertificationClass certClass = new CertificationClass()
            certClass.location = new Address(locationName: "Galveston Ward Building", address: "123 W. East", city: "Gilbert",
                    state: "AZ", zip: "14324").save(failOnError: true)
            certClass.classDate = new Date() + 1
            certClass.time = "8AM-10PM"
//            certClass.coordinator = admin
            certClass.certification = outdoor
            certClass.save(failOnError: true)


            Date startDate = new Date().parse('yyyy/MM/dd', '1973/01/01')
            Date endDate = new Date().parse('yyyy/MM/dd', '2030/01/01')

            // Fast Start required for all
            ScoutUnitType pack = ScoutUnitType.Pack
            ProgramCertification packFastStart = new ProgramCertification(unitType: pack,
                    certification: packFastStartTraining,
                    positionType: null, required: true,
                    startDate: startDate,
                    endDate: endDate);
            packFastStart.save(failOnError: true);

            ProgramCertification troopFastStart = new ProgramCertification(unitType: troop,
                    certification: troopFastStartTraining,
                    positionType: null, required: true,
                    startDate: startDate,
                    endDate: endDate);
            troopFastStart.save(failOnError: true);

            ProgramCertification teamFastStart = new ProgramCertification(unitType: ScoutUnitType.Team,
                    certification: teamFastStartTraining,
                    positionType: null, required: true,
                    startDate: startDate,
                    endDate: endDate);
            teamFastStart.save(failOnError: true);

            ProgramCertification crewFastStart = new ProgramCertification(unitType: ScoutUnitType.Crew,
                    certification: crewFastStartTraining,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            crewFastStart.save(failOnError: true);

            // YPT required for all
            ProgramCertification packYPT = new ProgramCertification(unitType: pack,
                    certification: ypt,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            packYPT.save(failOnError: true);

            ProgramCertification troopYPT = new ProgramCertification(unitType: troop,
                    certification: ypt,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            troopYPT.save(failOnError: true);

            ProgramCertification teamYPT = new ProgramCertification(unitType: ScoutUnitType.Team,
                    certification: ypt,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            teamYPT.save(failOnError: true);

            ProgramCertification crewYPT = new ProgramCertification(unitType: ScoutUnitType.Crew,
                    certification: vYpt,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            crewYPT.save(failOnError: true);

            // Leader Specific required for all
            ProgramCertification packLeaderSpecificP = new ProgramCertification(positionType: LeaderPositionType.Cubmaster,
                    certification: packLeaderSpecific,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            packLeaderSpecificP.save(failOnError: true);


            ProgramCertification troopLeaderSpecificP = new ProgramCertification(positionType: LeaderPositionType.Scoutmaster,
                    certification: troopLeaderSpecific,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            troopLeaderSpecificP.save(failOnError: true);

            ProgramCertification teamLeaderSpecificP = new ProgramCertification(positionType: LeaderPositionType.VarsityCoach,
                    certification: teamLeaderSpecific,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            teamLeaderSpecificP.save(failOnError: true);

            ProgramCertification crewLeaderSpecificP = new ProgramCertification(positionType: LeaderPositionType.CrewAdvisor,
                    certification: crewLeaderSpecific,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            crewLeaderSpecificP.save(failOnError: true);

            // Baloo.  Tour permit required for pack, but not required for pack
            ProgramCertification packBaloo = new ProgramCertification(unitType: pack,
                    certification: baloo,
                    required: false,
                    startDate: startDate,
                    endDate: endDate);
            packBaloo.save(failOnError: true)

            //Outdoor.  Required
            ProgramCertification troopOutdoor = new ProgramCertification(unitType: troop,
                    certification: outdoor,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            troopOutdoor.save(failOnError: true)

            ProgramCertification teamOutdoor = new ProgramCertification(unitType: ScoutUnitType.Team,
                    certification: outdoor,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            teamOutdoor.save(failOnError: true)

            ProgramCertification crewOutdoor = new ProgramCertification(unitType: ScoutUnitType.Crew,
                    certification: outdoor,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            crewOutdoor.save(failOnError: true)

            // This Is Scouting required for all
            ProgramCertification packThisIsScouting = new ProgramCertification(unitType: pack,
                    certification: thisIsScouting,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            packThisIsScouting.save(failOnError: true);

            ProgramCertification troopThisIsScouting = new ProgramCertification(unitType: troop,
                    certification: thisIsScouting,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            troopThisIsScouting.save(failOnError: true);

            ProgramCertification teamThisIsScouting = new ProgramCertification(unitType: ScoutUnitType.Team,
                    certification: thisIsScouting,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            teamThisIsScouting.save(failOnError: true);

            ProgramCertification crewThisIsScouting = new ProgramCertification(unitType: ScoutUnitType.Crew,
                    certification: thisIsScouting,
                    required: true,
                    startDate: startDate,
                    endDate: endDate);
            crewThisIsScouting.save(failOnError: true);

            // Hazardous Weather, tour permit required only
            ProgramCertification packWeather = new ProgramCertification(unitType: pack,
                    certification: weather,
                    required: false,
                    startDate: startDate,
                    endDate: endDate);
            packWeather.save(failOnError: true);

            ProgramCertification troopWeather = new ProgramCertification(unitType: troop,
                    certification: weather,
                    required: false,
                    startDate: startDate,
                    endDate: endDate);
            troopWeather.save(failOnError: true);

            ProgramCertification teamWeather = new ProgramCertification(unitType: ScoutUnitType.Team,
                    certification: weather,
                    required: false,
                    startDate: startDate,
                    endDate: endDate);
            teamWeather.save(failOnError: true);

            ProgramCertification crewWeather = new ProgramCertification(unitType: ScoutUnitType.Crew,
                    certification: weather,
                    required: false,
                    startDate: startDate,
                    endDate: endDate);
            crewWeather.save(failOnError: true);

            //Woodbadge.  Required for none
            ProgramCertification packWoodbadge = new ProgramCertification(unitType: pack,
                    certification: woodbadge,
                    required: false,
                    startDate: startDate,
                    endDate: endDate);
            packWoodbadge.save(failOnError: true)
            ProgramCertification troopWoodbadge = new ProgramCertification(unitType: troop,
                    certification: woodbadge,
                    required: false,
                    startDate: startDate,
                    endDate: endDate);
            troopWoodbadge.save(failOnError: true)

            ProgramCertification teamWoodbadge = new ProgramCertification(unitType: ScoutUnitType.Team,
                    certification: woodbadge,
                    required: false,
                    startDate: startDate,
                    endDate: endDate);
            teamWoodbadge.save(failOnError: true)

            ProgramCertification crewWoodbadge = new ProgramCertification(unitType: ScoutUnitType.Crew,
                    certification: woodbadge,
                    positionType: null,
                    required: false,
                    startDate: startDate,
                    endDate: endDate);
            crewWoodbadge.save(failOnError: true)

            ScoutGroup sanTanDistrict = new ScoutGroup(parent: council, groupLabel: "San Tan District", groupType: ScoutGroupType.District,
                    groupIdentifier: "SanTanDistrict")

            council.addToLeaderGroups([leader: admin, position: LeaderPositionType.Executive, admin: true])
            council.save(failOnError: true)

            ScoutGroup scoutGroup = new ScoutGroup(groupLabel: "Chandler Stake", groupIdentifier: "ChandlerStake", groupType: ScoutGroupType.Stake, parent: sanTanDistrict).save(failOnError: true)
            sanTanDistrict.addToChildGroups(scoutGroup)
            sanTanDistrict.save(failOnError: true)

            addUnit scoutGroup, "Ray 3nd Ward - Unit 81"
            addUnit scoutGroup, "Ray 2nd Ward - Unit 185"
            addUnit scoutGroup, "Ray 1st Ward - Unit 281"
            addUnit scoutGroup, "Cooper Ward  - Unit 489"
            addUnit scoutGroup, "McQueen 3rd Ward - Unit 785"
            addUnit scoutGroup, "McQueen 1st Ward - Unit 888"
            addUnit scoutGroup, "Cooper 2nd Ward - Unit 896"
            addUnit scoutGroup, "McQueen 2nd Ward - Unit 897"
            scoutGroup = new ScoutGroup(groupLabel: "Chandler East Stake", groupIdentifier: "ChandlerEastStake", groupType: ScoutGroupType.Stake, parent: scoutGroup).save(failOnError: true)
            sanTanDistrict.addToChildGroups(scoutGroup)
            sanTanDistrict.save(failOnError: true)
            addUnit scoutGroup, "Greenfield 2nd Ward - Unit 834"
            addUnit scoutGroup, "Grove 2nd Ward - Unit 841"
            addUnit scoutGroup, "Grove 3rd Ward - Unit 876"
            addUnit scoutGroup, "Grove 1st Ward - Unit 882"
            addUnit scoutGroup, "Greenfield 1st Ward - Unit 884"
            addUnit scoutGroup, "Lindsay 3rd Ward - Unit 886"
            addUnit scoutGroup, "Ray 5th Ward - Unit 887"
            addUnit scoutGroup, "Lindsay 2nd Ward - Unit 898"
            addUnit scoutGroup, "Lindsay 1st Ward - Unit 899"
            scoutGroup = new ScoutGroup(groupLabel: "Chandler West Stake", groupIdentifier: "ChandlerWestStake", groupType: ScoutGroupType.Stake, parent: scoutGroup).save(failOnError: true)
            sanTanDistrict.addToChildGroups(scoutGroup)
            sanTanDistrict.save(failOnError: true)
            addUnit scoutGroup, "Del Rio 1st Ward - Unit 188"
            addUnit scoutGroup, "Del Rio 2nd Ward - Unit 425"
            addUnit scoutGroup, "Galveston 1st Ward - Unit 485"
            addUnit scoutGroup, "Galveston 2nd Ward - Unit 585"
            addUnit scoutGroup, "Pleasant 1st Ward - Unit 588"
            addUnit scoutGroup, "Galveston 3rd Ward - Unit 639"
            addUnit scoutGroup, "Pleasant 3rd Ward - Unit 885"
            addUnit scoutGroup, "Pleasant 2nd Ward - Unit 988"
            scoutGroup = new ScoutGroup(groupLabel: "Gilbert Stake", groupIdentifier: "GilbertStake", groupType: ScoutGroupType.Stake, parent: scoutGroup).save(failOnError: true)
            sanTanDistrict.addToChildGroups(scoutGroup)
            sanTanDistrict.save(failOnError: true)
            addUnit scoutGroup, "Val Vista Ward - Unit 83"
            addUnit scoutGroup, "Gilbert 2nd Ward - Unit 183"
            addUnit scoutGroup, "Gilbert 11 Branch - Unit 210"
            addUnit scoutGroup, "Mesquite Ward - Unit 212"
            addUnit scoutGroup, "Frestone Park Ward - Unit 215"
            addUnit scoutGroup, "Saratoga Ward - Unit 671"
            addUnit scoutGroup, "Elliott Ward - Unit 683"
            addUnit scoutGroup, "Windrift Ward - Unit 840"
            addUnit scoutGroup, "Palo Verde Ward - Unit 842"
            addUnit scoutGroup, "Silver Creek Ward - Unit 844"
            addUnit scoutGroup, "Cullumber Ward - Unit 849"
            scoutGroup = new ScoutGroup(groupLabel: "Gilbert Greenfield Stake", groupIdentifier: "GilbertGreenfieldStake", groupType: ScoutGroupType.Stake, parent: scoutGroup).save(failOnError: true)
            sanTanDistrict.addToChildGroups(scoutGroup)
            sanTanDistrict.save(failOnError: true)
            addUnit scoutGroup, "Ray Ward - Unit 189"
            addUnit scoutGroup, "Western Skies Ward - Unit 289"
            addUnit scoutGroup, "Ashland Ranch Ward - Unit 290"
            addUnit scoutGroup, "Crossroads Park Ward - Unit 507"
            addUnit scoutGroup, "Allen Ranch Ward - Unit 689"
            addUnit scoutGroup, "Catalina Ward - Unit 789"
            addUnit scoutGroup, "Settler's Meadow Ward - Unit 889"
            addUnit scoutGroup, "Gilbert Ranch Ward - Unit 985"
            addUnit scoutGroup, "Vintage Ranch Ward - Unit 989"
            scoutGroup = new ScoutGroup(groupLabel: "Gilbert Highland Stake", groupIdentifier: "GilbertHighlandStake", groupType: ScoutGroupType.Stake, parent: scoutGroup).save(failOnError: true)
            sanTanDistrict.addToChildGroups(scoutGroup)
            sanTanDistrict.save(failOnError: true)
            addUnit scoutGroup, "Lakeview Trails Ward - Unit 44"
            addUnit scoutGroup, "Gilbert 3rd Ward - Unit 383"
            addUnit scoutGroup, "Higley Groves Ward - Unit 393"
            addUnit scoutGroup, "Cornerstone Ward - Unit 398"
            addUnit scoutGroup, "Constellation Ward - Unit 399"
            addUnit scoutGroup, "Bridlegate Ward - Unit 465"
            addUnit scoutGroup, "Durango Ward - Unit 480"
            addUnit scoutGroup, "Gilbert 6th Ward - Unit 481"
            addUnit scoutGroup, "Pioneer Ward - Unit 488"
            addUnit scoutGroup, "Page Ward - Unit 498"
            scoutGroup = new ScoutGroup(groupLabel: "Gilbert Higley Stake", groupIdentifier: "GilbertHigleyStake", groupType: ScoutGroupType.Stake, parent: scoutGroup).save(failOnError: true)
            sanTanDistrict.addToChildGroups(scoutGroup)
            sanTanDistrict.save(failOnError: true)
            addUnit scoutGroup, "Higley Ward - Unit 307"
            addUnit scoutGroup, "Fairview Ward - Unit 381"
            addUnit scoutGroup, "Ashley Heights Ward - Unit 780"
            addUnit scoutGroup, "Gateway Garden Ward - Unit 783"
            addUnit scoutGroup, "Summerfield Ward - Unit 807"
            addUnit scoutGroup, "Pecos Park Ward - Unit 819"
            addUnit scoutGroup, "Chaparral Ward - Unit 821"
            addUnit scoutGroup, "Bella Vista Ward - Unit 880"
            addUnit scoutGroup, "Gateway Ward - Unit 893"
            scoutGroup = new ScoutGroup(groupLabel: "Gilbert Stapley Stake", groupIdentifier: "GilbertStapleyStake", groupType: ScoutGroupType.Stake, parent: scoutGroup).save(failOnError: true)
            sanTanDistrict.addToChildGroups(scoutGroup)
            sanTanDistrict.save(failOnError: true)
            addUnit scoutGroup, "Islands Ward - Unit 181"
            addUnit scoutGroup, "Cooper Ward - Unit 528"
            addUnit scoutGroup, "McQueen Ward - Unit 569"
            addUnit scoutGroup, "Gilbert Harris Ward - Unit 583"
            addUnit scoutGroup, "Neely Ward - Unit 586"
            addUnit scoutGroup, "Madera Ward - Unit 589"
            addUnit scoutGroup, "Encinas Ward - Unit 685"
            addUnit scoutGroup, "El Dorado Ward - Unit 803"
            scoutGroup = new ScoutGroup(groupLabel: "Gilber Val Vista Stake", groupIdentifier: "GilbertValVistaStake", groupType: ScoutGroupType.Stake, parent: scoutGroup).save(failOnError: true)
            sanTanDistrict.addToChildGroups(scoutGroup)
            sanTanDistrict.save(failOnError: true)
            addUnit scoutGroup, "Gilbert 12th Ward - Unit 80"
            addUnit scoutGroup, "Lakeside Ward - Unit 218"
            addUnit scoutGroup, "Lindsay Ward - Unit 482"
            addUnit scoutGroup, "Gilbert 4th Ward - Unit 483"
            addUnit scoutGroup, "Gilbert 9th Ward - Unit 581"
            addUnit scoutGroup, "Emerald Bay Ward - Unit 680"
            addUnit scoutGroup, "Voyager Ward - Unit 681"
            addUnit scoutGroup, "Crystal Shores Ward - Unit 806"

            scoutGroup = new ScoutGroup(groupLabel: "San Tan Stake", groupIdentifier: "SanTanStake", groupType: ScoutGroupType.Stake, parent: scoutGroup).save(failOnError: true)
            sanTanDistrict.addToChildGroups(scoutGroup)
            sanTanDistrict.save(failOnError: true)
            addUnit scoutGroup, "San Tan 1st Ward - Unit 811"
            addUnit scoutGroup, "Coronado 1st Ward - Unit 812"
            addUnit scoutGroup, "Maplewood Ward - Unit 830"
            addUnit scoutGroup, "San Tan 2nd Ward - Unit 831"
            addUnit scoutGroup, "Power Ranch 2nd Ward - Unit 843"
            addUnit scoutGroup, "Meadow View Ward - Unit 873"
            addUnit scoutGroup, "Power Ranch Ward - Unit 883"
            addUnit scoutGroup, "Coronado 2nd Ward - Unit 912"

            ScoutGroup communityUnits = new ScoutGroup()
            communityUnits.groupType = ScoutGroupType.CommunityUnits
            communityUnits.groupIdentifier = "communityUnits"
            communityUnits.groupLabel = "Community Units"
            communityUnits.parent = sanTanDistrict;
            communityUnits.save(failOnError:true)
            
            sanTanDistrict.addToChildGroups(communityUnits)
            sanTanDistrict.save(failOnError:true)


            addCommunityUnit("Chandler Lions Club - Unit 58", communityUnits, [troop: "58"])
            addCommunityUnit("Gilbert United Methodist Church - Unit 88", communityUnits,
                    [pack: "88",
                    troop: "88",
                    crew: "2088"])

            addCommunityUnit("Deseret Gateway Baptist Church - Unit 104", communityUnits, [troop: "104"])
            addCommunityUnit("Sant Tan Scouts - Unit 125", communityUnits, [troop: "125", pack: "125"])
            addCommunityUnit("Knights of Columbus - Unit 132", communityUnits, [troop: "132", pack: "132"])
            addCommunityUnit("St Matthews Episcopal Church  - Unit 233", communityUnits, [troop: "233"])
            addCommunityUnit("Epiphany Lutheran Church - Unit 280", communityUnits, [troop: "280"])
            addCommunityUnit("Christs Greenfield Lutheran Church - Unit 282", communityUnits, [troop: "282", pack: "282"])
            addCommunityUnit("Chandler United Methodist - Unit 283", communityUnits, [troop: "283"])
            addCommunityUnit("Chandler Christian Church - Unit 285", communityUnits, [troop: "285", pack: "285"])
            addCommunityUnit("Gilbert Presbyterian - Unit 286", communityUnits, [troop: "286"])
            addCommunityUnit("St Anne's Church - Unit 312", communityUnits, [troop: "312", crew: "2312"])
            addCommunityUnit("New Hope Community Church - Unit 322", communityUnits, [troop: "322", pack: "322"])
            addCommunityUnit("Rancho Solano School - Unit 330", communityUnits, [pack: "330"])
            addCommunityUnit("Gilbert Boys and Girls Club - 382", communityUnits, [pack: "382"])
            addCommunityUnit("St Mary's Church - Unit 522", communityUnits, [pack: "522", troop: "522"])
            addCommunityUnit("San Tan Pack - Unit 524", communityUnits, [pack: "524"])
            addCommunityUnit("Hope Covenant Church - Unit 584", communityUnits, [pack: "584"])
            addCommunityUnit("Sanborn Elem PTO - Unit 587", communityUnits, [pack: "587"])
            addCommunityUnit("East Valley Bible Church - Unit 923", communityUnits, [troop: "923"])
            addCommunityUnit("Chandler Elks  - Unit 984", communityUnits, [pack: "984"])
            addCommunityUnit("Phoenix Scuba - Unit 2228", communityUnits, [crew: "2228"])

            council.save(flush:true)
            scoutGroupService.reindex()
        }


        registerStringMetaclass()

    }

    void registerStringMetaclass() {
        String.metaClass.humanize = {
            String word = delegate.toString()
            Pattern pattern = Pattern.compile("([A-Z]|[a-z])[a-z]*");

            List<String> tokens = new ArrayList<String>();
            Matcher matcher = pattern.matcher(word);
            String acronym = "";
            while (matcher.find()) {
                String found = matcher.group();
                if (found.matches("^[A-Z]\$")) {
                    acronym += found;
                } else {
                    if (acronym.length() > 0) {
                        //we have an acronym to add before we continue
                        tokens.add(acronym);
                        acronym = "";
                    }
                    tokens.add(found);
                }
            }
            if (acronym.length() > 0) {
                tokens.add(acronym);
            }

            if (tokens.size() > 0) {
                word = ""
                for (String s: tokens) {
                    word += " " + s;
                }

            }

            return word
        }

    }

    def destroy = {
    }

    void addCommunityUnit(String name, ScoutGroup parent, def childUnits) {
        String orgName = name.substring(0, name.indexOf(" -"));
        ScoutGroup community = new ScoutGroup(groupLabel: orgName, groupIdentifier: orgName.replaceAll("\\w", ""),
                groupType: ScoutGroupType.CharteringOrg, parent: parent).save(failOnError: true)
        parent.addToChildGroups(community)
        parent.save(failOnError: true)

        childUnits.each{
            String key = it.key
            key = key.charAt(0).toString().toUpperCase() + key.substring(1)
            ScoutGroup unit = new ScoutGroup(groupLabel: name, groupIdentifier: it.value, groupType: ScoutGroupType.Unit,
                unitType: ScoutUnitType.valueOf(key), parent:community).save(failOnError:true)
            community.addToChildGroups(unit)
            community.save(failOnError:true)
        }

    }



    void addUnit(ScoutGroup parent, String unitName) {
        String orgName = unitName.substring(0, unitName.indexOf(" -"));
        ScoutGroup wardUnit = new ScoutGroup(
                groupLabel: orgName, groupIdentifier: orgName.replaceAll("\\w", ""),
                groupType: ScoutGroupType.CharteringOrg, parent: parent).save(failOnError: true)
        parent.addToChildGroups(wardUnit)
        parent.save(failOnError: true)

        def indexOf = unitName.indexOf("Unit ")
        String unitNumber = unitName.substring(indexOf + 4)?.trim()

        parent = wardUnit
        ScoutGroup scoutGroup = new ScoutGroup(groupLabel: unitName, groupIdentifier: unitNumber, unitType: ScoutUnitType.Troop,
                groupType: ScoutGroupType.Unit, parent: parent).save(failOnError: true)
        parent.addToChildGroups(scoutGroup)
        parent.save(failOnError: true)

        ScoutGroup pack = new ScoutGroup(groupLabel: unitName, groupIdentifier: unitNumber, unitType: ScoutUnitType.Pack, groupType: ScoutGroupType.Unit, parent: parent).save(failOnError: true)
        parent.addToChildGroups(pack)
        parent.save(failOnError: true)

        scoutGroup = new ScoutGroup(groupLabel: unitName, groupIdentifier: "6" + unitNumber, unitType: ScoutUnitType.Team, groupType: ScoutGroupType.Unit, parent: parent).save(failOnError: true)
        parent.addToChildGroups(scoutGroup)
        parent.save(failOnError: true)

        scoutGroup = new ScoutGroup(groupLabel: unitName, groupIdentifier: "9" + unitNumber, unitType: ScoutUnitType.Crew, groupType: ScoutGroupType.Unit, parent: parent).save(failOnError: true)
        parent.addToChildGroups(scoutGroup)
        parent.save(failOnError: true)

    }
}
