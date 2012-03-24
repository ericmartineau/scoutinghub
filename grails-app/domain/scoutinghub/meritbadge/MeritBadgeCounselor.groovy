package scoutinghub.meritbadge

import scoutinghub.LeaderGroup

class MeritBadgeCounselor {

    /**
     * Attached to the LeaderGroup record so a counselor can certify different merit badges for different organizations.  For example, they
     * may certify for 5 merit badges for the district, but may certify 1 merit badge for the council
     */
    LeaderGroup leaderGroup

    /**
     * badges:  Which badges the particular counselor can certify for
     * baseUnits:  Which units the counselor services
     */
    static hasMany = [badges:MeritBadge]

    static constraints = {
    }
}
