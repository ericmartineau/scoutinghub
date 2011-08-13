package scoutinghub

import grails.plugins.springsecurity.SpringSecurityService
import scoutinghub.infusionsoft.InfusionsoftLeaderInfo

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

    void mergeLeaders(Leader primary, Leader secondary) {
        //merge scouting ids
        mergeScoutIds(primary, secondary);

        //merge leader groups
        mergeLeaderGroups(primary, secondary);

        //merge leader certifications
        mergeLeaderCertifications(primary, secondary);

        //Merge inactive groups
        mergeInactiveLeaderGroups(primary, secondary);

        //Merge leader roles
        mergeLeaderRoles(primary, secondary);

        //Merge social logins
        mergeSocialLogins(primary, secondary)

        //Merge class registrations
        mergeTrainingClassRegistrations(primary, secondary)

        //Merge infusionsoft merge information
        mergeInfusionsoftMergeInformation(primary, secondary)

        //Merge name, email, etc
        mergeLeaderInformation(primary, secondary);

        //kill secondary
        secondary.delete(failOnError: true, flush: true);

        //persist primary
        primary.save(failOnError: true);

    }

    void mergeSocialLogins(Leader primary, Leader secondary) {
        secondary.openIds?.each {OpenID secondaryOpenID ->

            OpenID primaryOpenID = new OpenID();
            primaryOpenID.url = secondaryOpenID.url
            primaryOpenID.createDate = secondaryOpenID.createDate
            primaryOpenID.updateDate = secondaryOpenID.updateDate
            primaryOpenID.leader = primary

            secondary.removeFromOpenIds(secondaryOpenID)
            secondaryOpenID.delete(flush:true)

            primary.addToOpenIds(primaryOpenID)
            primary.save(failOnError:true)


        }
    }

    void mergeTrainingClassRegistrations(Leader primary, Leader secondary) {
        secondary.certificationClasses?.each {CertificationClass certificationClass->
            if(!primary.certificationClasses?.find{it.id == certificationClass.id}) {
                primary.addToCertificationClasses(certificationClass)
            }
            secondary.removeFromCertificationClasses(certificationClass)
            secondary.save(failOnError:true)
        }
    }

    void mergeInfusionsoftMergeInformation(Leader primary, Leader secondary) {
        InfusionsoftLeaderInfo.findAllByLeader(secondary)?.each {
            if (!InfusionsoftLeaderInfo.findByLeader(primary)) {
                InfusionsoftLeaderInfo copied = new InfusionsoftLeaderInfo()
                copied.infusionsoftContactId = it.infusionsoftContactId
                copied.leader = primary
                copied.save(failOnError:true)
            }
            it.delete(failOnError:true)
        }
    }

    void mergeLeaderRoles(Leader primary, Leader secondary) {
        LeaderRole.findAllByLeader(secondary)?.each {
            LeaderRole role ->

            if (!primary.hasAuthority(role.role)) {
                LeaderRole primaryRole = new LeaderRole();
                primaryRole.leader = primary
                primaryRole.role = role.role
                primaryRole.save(failOnError: true)
            }

            role.delete()
        }
    }

    void mergeLeaderInformation(Leader primary, Leader secondary) {
        primary.username = primary.username ?: secondary.username
        primary.password = primary.password ?: secondary.password
        primary.email = primary.email ?: secondary.email
        primary.phone = primary.phone ?: secondary.phone
        if (!primary.enabled) {
            primary.enabled = secondary.enabled
        }
    }

    void mergeScoutIds(Leader primary, Leader secondary) {
        secondary.myScoutingIds.each() {
            def idString = it.myScoutingIdentifier;
            def myScoutingIdInstance = new MyScoutingId()
            myScoutingIdInstance.leader = primary
            myScoutingIdInstance.myScoutingIdentifier = idString;

            it.myScoutingIdentifier = "del" + idString;
            secondary.save(flush: true, failOnError: true);

            secondary.refresh();
            primary.addToMyScoutingIds(myScoutingIdInstance)
            primary.save(flush: true, failOnError: true);
        }
    }

    void mergeInactiveLeaderGroups(Leader primary, Leader secondary) {
        LeaderCertification primaryLeaderCert;
        InactiveLeaderGroup.findAllByLeader(secondary)?.each {
            InactiveLeaderGroup inactiveLeaderGroup ->

            InactiveLeaderGroup copied = new InactiveLeaderGroup()
            copied.leader = primary
            copied.scoutGroup = inactiveLeaderGroup.scoutGroup
            copied.createDate = inactiveLeaderGroup.createDate
            copied.save(failOnError: true)

            inactiveLeaderGroup.delete()
        }
    }

    void mergeLeaderCertifications(Leader primary, Leader secondary) {
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
            if (it.enteredBy.equals(secondary)) {
                leaderCertification.enteredBy = primary
            } else {
                leaderCertification.enteredBy = it.enteredBy
            }

            primary.addToCertifications(leaderCertification);

            it.delete();
            secondary.removeFromCertifications(it);
        }

        primary.save(flush: true, failOnError: true);
        secondary.save(flush: true, failOnError: true)

        //Move over any certifications that were entered by the secondary (but aren't actually for the secondary)
        LeaderCertification.findAllByEnteredBy(secondary)?.each {
            it.enteredBy = primary
            it.save(failOnError: true)
        }
    }

    void mergeLeaderGroups(Leader primary, Leader secondary) {
        // look for collisions and handle them.
        LeaderGroup primaryScoutGroup;
        Set<LeaderGroup> groupsToAdd = new HashSet<LeaderGroup>();

        secondary.groups.each() {LeaderGroup leaderGroup ->

            if (primary.hasScoutGroup(leaderGroup.scoutGroup)) {
                primaryScoutGroup = primary.findScoutGroup(leaderGroup.scoutGroup);
                if (primaryScoutGroup.leaderPosition == leaderGroup.leaderPosition) {
                    if (leaderGroup.admin != primaryScoutGroup.admin &&
                            !primaryScoutGroup.admin) {
                        primaryScoutGroup.admin = leaderGroup.admin || secondaryScoutGroup.admin;
                        primaryScoutGroup.save(flush: true, failOnError: true);
                    }
                    secondary.removeFromGroups(leaderGroup)
                    leaderGroup.delete(flush: true, failOnError: true);
                } else {
                    groupsToAdd.add(leaderGroup);
                }
            } else {
                groupsToAdd.add(leaderGroup);
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
        secondary.save(failOnError: true);
    }


    Leader findExactLeaderMatch(String scoutid, String email, String firstName, String lastName, ScoutGroup scoutGroup) {

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
        if (!leader && scoutGroup) {
            def c = Leader.createCriteria();
            leader = c.get {
                eq('firstName', firstName)
                eq('lastName', lastName)

                groups {
                    eq('scoutGroup', scoutGroup)
                }
            }
        }
        return leader
    }

    Set<Leader> findLeaders(String scoutid, String email, String firstName, String lastName, ScoutGroup scoutGroup) {
        Set<Leader> rtn = new HashSet<Leader>();

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

        return rtn
    }

}
