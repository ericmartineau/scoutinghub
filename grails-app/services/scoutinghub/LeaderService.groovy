package scoutinghub

import grails.plugins.springsecurity.SpringSecurityService



class LeaderService {

    static transactional = true

    SpringSecurityService springSecurityService;

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

    void mergeLeaders(Leader primary, Leader secondary){
        //merge scouting ids
        mergeScoutIds(primary, secondary);

        //merge leader groups
        mergeLeaderGroups(primary, secondary);
        //merge leader certifications
        mergeLeaderCertifications(primary, secondary);


        //persist primary
        primary.save(failOnError: true);
        //kill secondary
        secondary.delete(failOnError: true);
        primary.refresh();
    }

    void mergeScoutIds(Leader primary, Leader secondary){
           secondary.myScoutingIds.each(){
            def idString = it.myScoutingIdentifier;
            def myScoutingIdInstance = new MyScoutingId()
            myScoutingIdInstance.leader = primary
            myScoutingIdInstance.myScoutingIdentifier=idString;

            it.myScoutingIdentifier="del"+idString;
            secondary.save(flush: true,failOnError: true);

            secondary.refresh();
            primary.addToMyScoutingIds(myScoutingIdInstance)
            primary.save(flush: true,failOnError: true);
        }
    }

    void mergeLeaderCertifications(Leader primary, Leader secondary){
        LeaderCertification primaryLeaderCert;
        Set<LeaderCertification> leaderCertificationsToBeAdded = new HashSet<LeaderCertification>();


        // look for collisions, keep only the most recent cert relationships
        secondary.certifications.each() {
            if (primary.hasCertification(it.certification)) {
                primaryLeaderCert = primary.findCertification(it.certification);

                if (primaryLeaderCert.dateEarned.before(it.dateEarned)) {
                    primaryLeaderCert.dateEarned = it.dateEarned;
                    primaryLeaderCert.enteredBy = it.enteredBy;
                    if (it.enteredBy.equals(secondary)) {
                        primaryLeaderCert.enteredBy = primary
                    } else {
                        primaryLeaderCert.enteredBy = it.enteredBy
                    }
                    primaryLeaderCert.enteredType = it.enteredType;
                    primaryLeaderCert.save(failOnError: true);
                }
            } else {
                leaderCertificationsToBeAdded.add(it);
            }
        }

        leaderCertificationsToBeAdded.each() {
            LeaderCertification leaderCertification = new LeaderCertification()
            leaderCertification.leader = primary
            leaderCertification.certification = it.certification
            leaderCertification.dateEarned = it.dateEarned
            leaderCertification.dateEntered = it.dateEntered
            leaderCertification.enteredType = it.enteredType
            if(it.enteredBy.equals(secondary)) {
                leaderCertification.enteredBy = primary
            } else {
                leaderCertification.enteredBy = it.enteredBy
            }
            leaderCertification.save(failOnError: true)

            primary.addToCertifications(leaderCertification);
            primary.save(flush: true,failOnError: true);
            secondary.certifications.remove(it);
        }

        primary.save(flush: true,failOnError: true);
    }

    void mergeLeaderGroups(Leader primary, Leader secondary) {
        // look for collisions and handle them.
        LeaderGroup primaryScoutGroup;
        Set<LeaderGroup> groupsToAdd = new HashSet<LeaderGroup>();

        secondary.groups.each() {

            if (primary.hasScoutGroup(it.scoutGroup)) {
                primaryScoutGroup = primary.findScoutGroup(it.scoutGroup);
                if (primaryScoutGroup.leaderPosition == it.leaderPosition) {
                    if (it.admin != primaryScoutGroup.admin &&
                            !primaryScoutGroup.admin) {
                        primaryScoutGroup.admin = it.admin || secondaryScoutGroup.admin;
                        primaryScoutGroup.save(flush: true, failOnError: true);
                    }
                    it.delete(flush: true, failOnError: true);
                } else {
                    groupsToAdd.add(it);
                }
            } else {
                groupsToAdd.add(it);
            }

        }

        groupsToAdd.each() {
            ScoutGroup existingUnit = it.scoutGroup;
            existingUnit.removeFromLeaderGroups(it);
            secondary.removeFromGroups(it);
            existingUnit.addToLeaderGroups([leader: primary, leaderPosition: it.leaderPosition]);
            existingUnit.save(flush: true, failOnError: true);
        }

        primary.save(failOnError: true);
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
