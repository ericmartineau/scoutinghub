package scoutinghub.infusionsoft

import scoutinghub.Leader

import groovy.net.xmlrpc.XMLRPCServerProxy
import scoutinghub.LeaderPositionType
import scoutinghub.ScoutGroup
import org.springframework.context.MessageSource
import scoutinghub.LeaderGroup
import org.hibernate.SessionFactory

/**
 * A service that allows data from scoutinghub to be synced to Infusionsoft for email communication.
 *
 * User: eric
 * Date: 6/19/11
 * Time: 10:37 AM
 */
class InfusionsoftService {

    /**
     * For resolving
     */
    MessageSource messageSource

    SessionFactory sessionFactory

    /**
     * Syncs all leader records.
     */
    void syncAllLeaders(int groupId) {
        ScoutGroup scoutGroup = ScoutGroup.get(groupId)
        syncAllLeaders(scoutGroup)
    }

    void syncAllLeaders(ScoutGroup scoutGroup) {
        scoutGroup.leaderGroups?.leader?.each {
            syncLeader(it)
        }

        scoutGroup.childGroups?.each {
            syncAllLeaders(it)
        }
    }

    int syncLeader(Leader leader) {
        int rtn = 0

        //Check if we've previously synced this record agains the local sync cache
        final InfusionsoftLeaderInfo leaderSync = InfusionsoftLeaderInfo.findByLeader(leader)

        //We don't store a record for each position in Infusionsoft, so we'll have to calculate a
        //total percent trained - a roll-up for all positions a given leader has
        int totalPctTrained = 0;
        if (leader.groups?.size() > 0) {
            leader.groups?.each {totalPctTrained += it.pctTrained}
            totalPctTrained = totalPctTrained / leader.groups?.size()
        } else {
            totalPctTrained = 100
        }

        //Create the contact struct for the data to be pushed to Infusionsoft
        def contact = [
                FirstName: leader.firstName ?: "",
                LastName: leader.lastName ?: "",
                Email: leader.email ?: "",
                Phone1: leader.phone ?: "",
                _ScoutingHubId: leader.id,
                _PercentTrained: totalPctTrained,
                _PrimaryPosition: "",
                _UnitName: ""
        ]

        //Set the primary position and primary unit name for the leader.  Most people will only have one unit, so this should suffice
        if (leader.groups?.size() > 0) {
            final LeaderGroup firstGroup = leader.groups?.iterator().next()
            contact["_PrimaryPosition"] = messageSource.getMessage(firstGroup.leaderPosition.name() + ".label", null,
                    firstGroup.leaderPosition.name(), Locale.getDefault())
            contact["_UnitName"] = firstGroup.scoutGroup.toString()
        }

        //Set the date the user first logged into ScoutingHub
        if (leader.setupDate) {
            contact["_FirstLoginDate"] = leader.setupDate
        }

        if (!leaderSync) {
            //Create the contact and create the sync record
            rtn = getApiService().ContactService.add(contact)
            leaderSync = new InfusionsoftLeaderInfo(infusionsoftContactId: rtn, leader: leader).save(failOnError: true)
        } else {
            //Just update the information
            getApiService().ContactService.update(leaderSync.infusionsoftContactId, contact)
            rtn = leaderSync.infusionsoftContactId
        }

        //Create a list of all groups the leader is a part of, including all parent groups
        def scoutGroupsForLeader = []
        leader.groups?.each {
            def parent = it.scoutGroup
            while (parent != null) {
                scoutGroupsForLeader << parent.id
                parent = parent.parent
            }
        }

        //This will contain data for both unit and position tags
        def existsMap = [:]

        InfusionsoftScoutGroupInfo.list().each {InfusionsoftScoutGroupInfo scoutGroupInfo ->
            existsMap[scoutGroupInfo.infusionsoftTagId] = scoutGroupsForLeader.contains(scoutGroupInfo.scoutGroup.id)
        }

        InfusionsoftPositionInfo.list().each {InfusionsoftPositionInfo positionInfo ->
            existsMap[positionInfo.infusionsoftTagId] = leader.groups.find {it.leaderPosition == positionInfo.positionType}
        }

        //Get all tags for the contact record in Infusionsoft
        def grpList = getApiService().DataService.findByField("ContactGroupAssign", 200, 0, "ContactId", leaderSync.infusionsoftContactId,
                ["GroupId"])

        //Look for tags that need to be removed from Infusionsoft
        grpList.each {groupAssign ->
            if (!existsMap[groupAssign.GroupId]) {
                //Remove it
                getApiService().ContactService.removeFromGroup(leaderSync.infusionsoftContactId, groupAssign.GroupId)
            }
            existsMap.remove(groupAssign.GroupId)
        }

        //Look for tags that need to be added
        existsMap.each {entry ->
            if (entry.value) {
                getApiService().ContactService.addToGroup(leaderSync.infusionsoftContactId, entry.key)
            }
        }

        return rtn
    }

    def syncTags() {

        //Look for and sync the Infusionsoft tag categories (one for Position tags and one for Unit tags)
        final InfusionsoftSyncInfo syncInfo = InfusionsoftSyncInfo.list()[0]
        if (!syncInfo) {
            syncInfo = new InfusionsoftSyncInfo()
            def positionTagCategory = [
                    CategoryName: "Scouting Position"
            ]
            final addedCategory = getApiService().DataService.add("ContactGroupCategory", positionTagCategory)
            syncInfo.positionTagCategoryId = addedCategory

            def scoutGroupTagCategory = [
                    CategoryName: "Scouting Unit"
            ]
            final addedCategory2 = getApiService().DataService.add("ContactGroupCategory", scoutGroupTagCategory)
            syncInfo.scoutGroupTagCategoryId = addedCategory2
            syncInfo.save()
        }

        //Sync up positions with tags
        def alreadySyncedPositions = InfusionsoftPositionInfo.list()?.collect {it.positionType}
        List<LeaderPositionType> unSyncedPositions = LeaderPositionType.values().findAll {!alreadySyncedPositions.contains(it)}
        unSyncedPositions.each {
            def tag = [
                    GroupCategoryId: syncInfo.positionTagCategoryId,
                    GroupName: messageSource.getMessage(it.name() + ".label", null, it.name(), Locale.getDefault())
            ]
            int groupId = getApiService().DataService.add("ContactGroup", tag)
            InfusionsoftPositionInfo infusionsoftPositionInfo = new InfusionsoftPositionInfo()
            infusionsoftPositionInfo.infusionsoftTagId = groupId
            infusionsoftPositionInfo.positionType = it
            infusionsoftPositionInfo.save()
        }

        //Sync up all scout groups (units)
        def alreadySyncedGroups = InfusionsoftScoutGroupInfo.list()?.collect {it.scoutGroup.id} ?: []
        def unSyncedScoutGroups
        if (alreadySyncedGroups.size() > 0) {
            unSyncedScoutGroups = ScoutGroup.findAllByIdNotInList(alreadySyncedGroups)
        } else {
            unSyncedScoutGroups = ScoutGroup.list()
        }

        unSyncedScoutGroups.each {
            def tag = [
                    GroupCategoryId: syncInfo.scoutGroupTagCategoryId,
                    GroupName: it.toString()
            ]
            int groupId = getApiService().DataService.add("ContactGroup", tag)
            println "Synced ${it} to Infusionsoft: ${groupId}"
            InfusionsoftScoutGroupInfo infusionsoftScoutGroupInfo = new InfusionsoftScoutGroupInfo()
            infusionsoftScoutGroupInfo.infusionsoftTagId = groupId
            infusionsoftScoutGroupInfo.scoutGroup = it
            infusionsoftScoutGroupInfo.lastSyncDate = new Date()
            infusionsoftScoutGroupInfo.save(failOnError: true)
        }
    }


    boolean isConfigured() {
        def list = InfusionsoftSettings.list()
        return list != null && list.size() > 0
    }

    def getApiService() {
        final InfusionsoftSettings infusionsoftSettings = InfusionsoftSettings.list()[0]
        if (!infusionsoftSettings) {
            throw new NullPointerException("No Infusionsoft Settings configured for application")
        }
        final XMLRPCServerProxy serverProxy = new XMLRPCServerProxy(infusionsoftSettings.infusionsoftUrl)
        return new InfusionsoftApiExecutor(serverProxy: serverProxy, apiKey: infusionsoftSettings.infusionsoftKey)
    }


}
