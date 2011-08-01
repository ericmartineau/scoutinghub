package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.ScoutGroupType
import scoutinghub.ScoutGroup
import scoutinghub.ScoutUnitType
import scoutinghub.ScoutGroupService

/**
 * User: ericm
 * Date: 7/31/11
 * Time: 8:49 PM
 */
class SeedSanTanDistrictService implements SeedScript {

    ScoutGroupService scoutGroupService

    int getOrder() {
        return 1
    }

    void execute() {
        if(ScoutGroup.findByGroupIdentifier("grandcanyon")) {
            return
        }

        ScoutGroup council = new ScoutGroup(
                groupType: ScoutGroupType.Council,
                groupIdentifier: "grandcanyon",
                groupLabel: "Grand Canyon Council"
        ).save(failOnError: true)

        ScoutGroup sanTanDistrict = new ScoutGroup(parent: council, groupLabel: "San Tan District", groupType: ScoutGroupType.District,
                groupIdentifier: "SanTanDistrict")

        ScoutGroup scoutGroup = new ScoutGroup(groupLabel: "Chandler Stake", groupIdentifier: "ChandlerStake", groupType: ScoutGroupType.Stake, parent: sanTanDistrict).save(failOnError: true)
        sanTanDistrict.addToChildGroups(scoutGroup)
        assert sanTanDistrict.save(failOnError: true)

        addUnit scoutGroup, "Ray 3rd Ward - Unit 81"
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
        communityUnits.groupType = ScoutGroupType.Group
        communityUnits.groupIdentifier = "communityUnits"
        communityUnits.groupLabel = "Community Units"
        communityUnits.parent = sanTanDistrict;
        communityUnits.save(failOnError: true)

        sanTanDistrict.addToChildGroups(communityUnits)
        sanTanDistrict.save(failOnError: true)


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

        council.save(flush: true)

        scoutGroupService.reindex()

    }



    String getName() {
        return "seedSanTanDistrict"
    }

    void addCommunityUnit(String name, ScoutGroup parent, def childUnits) {
        String orgName = name.substring(0, name.indexOf(" -"));
        String groupIdentifier = orgName.replaceAll("\\s", "").replaceAll("'", "")
        ScoutGroup community = new ScoutGroup(groupLabel: orgName, groupIdentifier: groupIdentifier,
                groupType: ScoutGroupType.CharteringOrg, parent: parent).save(failOnError: true)
        assert community
        parent.addToChildGroups(community)
        assert parent.save(failOnError: true)

        childUnits.each {
            String key = it.key
            key = key.charAt(0).toString().toUpperCase() + key.substring(1)
            ScoutGroup unit = new ScoutGroup(groupLabel: name, groupIdentifier: it.value, groupType: ScoutGroupType.Unit,
                    unitType: ScoutUnitType.valueOf(key), parent: community).save(failOnError: true)
            assert unit
            community.addToChildGroups(unit)
            assert community.save(failOnError: true)
        }

    }



    void addUnit(ScoutGroup parent, String unitName) {
        String orgName = unitName.substring(0, unitName.indexOf(" -"));
        String groupIdentifier = orgName.replaceAll("\\s", "").replaceAll("'", "")
        ScoutGroup wardUnit = new ScoutGroup(
                groupLabel: orgName, groupIdentifier: groupIdentifier,
                groupType: ScoutGroupType.CharteringOrg, parent: parent).save(failOnError: true)
        parent.addToChildGroups(wardUnit)
        assert parent.save(failOnError: true)

        def indexOf = unitName.indexOf("Unit ")
        String unitNumber = unitName.substring(indexOf + 4)?.trim()

        parent = wardUnit
        ScoutGroup scoutGroup = new ScoutGroup(groupLabel: unitName, groupIdentifier: unitNumber, unitType: ScoutUnitType.Troop,
                groupType: ScoutGroupType.Unit, parent: parent).save(failOnError: true)
        parent.addToChildGroups(scoutGroup)
        assert parent.save(failOnError: true)

        ScoutGroup pack = new ScoutGroup(groupLabel: unitName, groupIdentifier: unitNumber, unitType: ScoutUnitType.Pack, groupType: ScoutGroupType.Unit, parent: parent).save(failOnError: true)
        parent.addToChildGroups(pack)
        assert parent.save(failOnError: true)

        scoutGroup = new ScoutGroup(groupLabel: unitName, groupIdentifier: "6" + unitNumber, unitType: ScoutUnitType.Team, groupType: ScoutGroupType.Unit, parent: parent).save(failOnError: true)
        parent.addToChildGroups(scoutGroup)
        assert parent.save(failOnError: true)

        scoutGroup = new ScoutGroup(groupLabel: unitName, groupIdentifier: "9" + unitNumber, unitType: ScoutUnitType.Crew, groupType: ScoutGroupType.Unit, parent: parent).save(failOnError: true)
        parent.addToChildGroups(scoutGroup)
        assert parent.save(failOnError: true)

    }
}
