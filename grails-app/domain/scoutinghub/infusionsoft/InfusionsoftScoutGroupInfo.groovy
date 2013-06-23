package scoutinghub.infusionsoft

import scoutinghub.ScoutGroup

/**
 * User: eric
 * Date: 6/19/11
 * Time: 11:40 AM
 */
class InfusionsoftScoutGroupInfo {
    static mapWith = "neo4j"

    ScoutGroup scoutGroup
    int infusionsoftTagId
    Date lastSyncDate
}
