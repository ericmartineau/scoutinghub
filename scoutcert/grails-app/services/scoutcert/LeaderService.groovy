package scoutcert

import grails.plugins.springsecurity.SpringSecurityService



class LeaderService {

    static transactional = false

    SpringSecurityService springSecurityService

    Leader createLeader(def params) {
        Leader leader = new Leader(
                firstName: params.firstName,
                lastName: params.lastName,
                email: params.email,
                username: params.username,
                password: springSecurityService.encodePassword(params.password),
                enabled: true
        )
        if (params.scoutid) {
            leader.addToMyScoutingIds(myScoutingIdentifier: params.scoutid)
        }

        leader.save(failOnError: true)
        LeaderRole.create(leader, Role.findByAuthority("ROLE_LEADER"), true)
        return leader
    }

    Leader findExactLeaderMatch(String scoutid, String email, String firstName, String lastName, ScoutGroup scoutGroup) {
        if (scoutGroup == null) {
            return null
        }
        Leader leader = null
        if (!leader && scoutid != "") {
            def c = Leader.createCriteria();
            leader = c.get {
                myScoutingIds {
                    eq('myScoutingIdentifier', scoutid)
                }
            };
        }

        //Try a strict lookup
        if (!leader) {
            def c = Leader.createCriteria();
            leader = c.get {
                eq('firstName', firstName)
                eq('lastName', lastName)
                eq('email', email)
            }
        }

        //Try first/last/unit
        if (!leader) {
            def c = Leader.createCriteria();
            leader = c.get {
                eq('firstName', firstName)
                eq('lastName', lastName)

                groups {
                    eq('scoutGroup', scoutGroup)
//                    scoutGroup {
//                        eq('groupIdentifier', unitNumber)
//                    }

                }
            }
        }
        return leader
    }

    Set<Leader> findLeaders(String scoutid, String email, String firstName, String lastName, ScoutGroup scoutGroup) {
        Set<Leader> rtn = new HashSet<Leader>();

        if (scoutGroup != null) {

            Leader match = findExactLeaderMatch(scoutid, email, firstName, lastName, scoutGroup)
            if (match) {
                rtn.add(match);
            }

            //Try email

            if (email) {
                Collection<Leader> leaders = Leader.findAllByEmail(email)
                if (leaders?.size() > 0) {
                    rtn.addAll(leaders)
                }
            }


        }



        return rtn

    }

}
