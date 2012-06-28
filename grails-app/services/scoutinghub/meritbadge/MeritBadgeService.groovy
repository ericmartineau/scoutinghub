package scoutinghub.meritbadge

import scoutinghub.Leader
import scoutinghub.LeaderGroup
import scoutinghub.LeaderPositionType

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 3/10/12
 * Time: 6:56 PM
 * To change this template use File | Settings | File Templates.
 */
class MeritBadgeService {
    static def transactional = true
    
    synchronized List<MeritBadgeCounselor> getOrCreateMeritBadgeCounselor(Leader leader) {
        def rtn = []
        def meritBadgeCounselorRecords = leader.groups?.find {it.leaderPosition == LeaderPositionType.MeritBadgeCounselor}
        if(meritBadgeCounselorRecords) {
            MeritBadgeCounselor counselor = MeritBadgeCounselor.findByLeader(leader)
            if (!counselor) {
                counselor = new MeritBadgeCounselor()
                counselor.leader = leader
                counselor.save(failOnError: true)
            }
            rtn << counselor
        } else {
            MeritBadgeCounselor.findByLeader(leader)?.each{it.delete(failOnError: true)}
        }


        return rtn
    }
}
