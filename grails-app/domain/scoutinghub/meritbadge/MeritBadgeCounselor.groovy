package scoutinghub.meritbadge

import scoutinghub.Leader

class MeritBadgeCounselor {

    /**
     * Attached to the leaderGroup for type "Merit Badge Counselor"
     */
    Leader leader
    
    Date originalCertificationDate
    Date recertificationDate


    /**
     * badges:  Which badges the particular counselor can certify for
     * baseUnits:  Which units the counselor services
     */
    static hasMany = [badges:MeritBadge]

    static belongsTo = [Leader]

    static constraints = {
        originalCertificationDate(nullable: true)
        recertificationDate(nullable: true)
    }

    public Collection<MeritBadge> getSortedMeritBadges() {
        return badges?.sort {it.name}
    }
}
