package scoutinghub

import grails.plugins.springsecurity.SpringSecurityService
import groovyx.net.http.HTTPBuilder
import org.apache.commons.io.IOUtils
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import scoutinghub.infusionsoft.InfusionsoftLeaderInfo

import static groovyx.net.http.ContentType.URLENC

class LeaderService {

    static transactional = true

    SpringSecurityService springSecurityService;

    TrainingService trainingService

    LeaderGeoPosition getGeoCodeForLeader(Leader leader) {
        Address address = new Address(
                address: leader.address1 + " " + leader.address2,
                city: leader.city,
                state: leader.state,
                zip: leader.postalCode
        )

        return lookupGeoCode(address)
    }

    LeaderGeoPosition lookupGeoCode(Address address) {
        def rtn = null;

        if (address.address && address.city) {
            String addressBlock = "${address.address ?: ""}, ${address.city ?: ""}, ${address.state}, ${address.zip}"
            def http = new HTTPBuilder("http://rpc.geocoder.us")

            def postBody = [address: addressBlock, parse_address: 1]
            http.post(path: "/service/namedcsv", body: postBody, requestContentType: URLENC) { resp, data ->
                def lines = IOUtils.readLines(data)
                 println "Geocoding response: " + lines.size()
                if(lines.size() > 1) {
                    String resultList = lines[1]
                    def props = [:]

                    String[] pairs = resultList.split(",")
                    for (String pair : pairs) {
                        String[] keyValue = pair.split("=")
                        if(keyValue.length > 1) {
                            props[keyValue[0]] = keyValue[1]
                            println keyValue[0] + "=" + keyValue[1]
                        }
                    }
                    if(props.lat && props.long) {
                        rtn = new LeaderGeoPosition(
                                latitude: Double.parseDouble(props.lat),
                                longitude: Double.parseDouble(props.long)
                        )
                    }
                }
            }



        }
        return rtn
    }

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

    void unmergeLeaders(Leader leader, MergedLeader mergedLeader) {

        Leader unmerged = new Leader();
        def mergedLeaderDomain = new DefaultGrailsDomainClass(MergedLeader.class)
        def leaderDomain = new DefaultGrailsDomainClass(Leader.class)

        Set fieldNames = new HashSet();
        mergedLeaderDomain.persistantProperties.each {
            GrailsDomainClassProperty mergedProp->

            leaderDomain.persistantProperties.each {
                GrailsDomainClassProperty leaderProp ->
                if (leaderProp.name?.equals(mergedProp.name)) {
                    fieldNames.add(leaderProp.name)
                }
            }
        }

        fieldNames.each {
            unmerged."${it}" = mergedLeader."${it}"
            if(mergedLeader.oldValues) {
                leader."${it}" = mergedLeader.oldValues."${it}"
            }
        }
        leader.save(failOnError:true, flush:true)
        unmerged.save(failOnError:true)
        mergedLeader.mergedRecords.each {
            MergedRecord mergedRecord->
            def got = mergedRecord.mergeRecordClass.get(mergedRecord.recordId)
            if(got) {
                got."${mergedRecord.mergeRecordField}" = unmerged
                got.save(failOnError:true)
            }
        }

        mergedLeader.mergedScoutingId?.each {MergedScoutingId mergedScoutingId->
            MyScoutingId.findAllByMyScoutingIdentifier(mergedScoutingId.scoutingId).each {leader.removeFromMyScoutingIds(it); it.delete(flush:true)}
            MyScoutingId myScoutingId = new MyScoutingId()
            myScoutingId.myScoutingIdentifier = mergedScoutingId.scoutingId
            myScoutingId.leader = unmerged
            unmerged.addToMyScoutingIds(myScoutingId)
        }

        mergedLeader?.mergedLeaderCertifications?.each {MergedLeaderCertification mergedLeaderCertification->
            LeaderCertification leaderCertification = new LeaderCertification()
            leaderCertification.certification = mergedLeaderCertification.certification
            leaderCertification.dateEarned =  mergedLeaderCertification.dateEarned
            leaderCertification.leader = unmerged
            leaderCertification.enteredBy = springSecurityService.currentUser
            leaderCertification.dateEntered = new Date()
            leaderCertification.enteredType = LeaderCertificationEnteredType.ManuallyEntered

            leader.certifications?.findAll {it.certification.id == mergedLeaderCertification.certification.id &&
                it.enteredType == LeaderCertificationEnteredType.Merged}?.each {leader.removeFromCertifications(it); it.delete()}

            unmerged.addToCertifications(leaderCertification)
        }

        mergedLeader?.mergedLeaderGroups?.each {MergedLeaderGroup mergedLeaderGroup->
            LeaderGroup leaderGroup = new LeaderGroup()
            leaderGroup.admin = mergedLeaderGroup.admin
            leaderGroup.leader = unmerged
            leaderGroup.scoutGroup = mergedLeaderGroup.scoutGroup
            leaderGroup.leaderPosition = mergedLeaderGroup.leaderPosition
            unmerged.addToGroups(leaderGroup)

            leader.groups?.findAll {
                it.leaderPosition == mergedLeaderGroup.leaderPosition &&
                        it.scoutGroup.id == mergedLeaderGroup.scoutGroup.id
            }?.each{leader.removeFromGroups(it); it.delete()}
        }

        mergedLeader.mergedLeaderRoles?.each {
            LeaderRole leaderRole = new LeaderRole()
            leaderRole.leader = unmerged
            leaderRole.role = it.role
            leaderRole.save(failOnError:true)
        }

        leader.save(failOnError:true)
        unmerged.save(failOnError:true)

        trainingService.recalculatePctTrained(leader);
        trainingService.recalculatePctTrained(unmerged);

        mergedLeader?.mergedLeaderGroups*.delete();
        mergedLeader?.mergedLeaderRoles*.delete();
        mergedLeader?.mergedLeaderCertifications*.delete();
        mergedLeader?.mergedRecords*.delete();
        mergedLeader.delete()
    }

    void mergeLeaders(Leader primary, Leader secondary) {

        boolean isLoggedIn = secondary.id == springSecurityService.currentUser?.id || primary.id == springSecurityService.currentUser?.id

        MergedLeader originalValues = new MergedLeader(primary.properties)
        MergedLeader mergedLeader = new MergedLeader(secondary.properties)
        mergedLeader.oldValues = originalValues
        mergedLeader.originalId = secondary.id
        mergedLeader.mergedTo = primary

        //merge scouting ids
        mergeScoutIds(primary, secondary, mergedLeader);

        //merge leader groups
        mergeLeaderGroups(primary, secondary, mergedLeader);

        //merge leader certifications
        mergeLeaderCertifications(primary, secondary, mergedLeader);

        //Merge inactive groups
        mergeInactiveLeaderGroups(primary, secondary, mergedLeader);

        //Merge leader roles
        mergeLeaderRoles(primary, secondary, mergedLeader);

        //Merge social logins
        mergeSocialLogins(primary, secondary, mergedLeader);

        //Merge class registrations
        mergeTrainingClassRegistrations(primary, secondary, mergedLeader);

        //Merge infusionsoft merge information
        mergeInfusionsoftMergeInformation(primary, secondary, mergedLeader);

        //Merge name, email, etc
        mergeLeaderInformation(primary, secondary);

        //Save merged leader information
        mergedLeader.save(failOnError:true)

        //kill secondary
        secondary.delete(failOnError: true, flush: true);

        //persist primary
        primary.save(flush: true);

        trainingService.recalculatePctTrained(primary)

        if(isLoggedIn) {
            springSecurityService.reauthenticate(primary.username)
        }

    }

    void mergeSocialLogins(Leader primary, Leader secondary, MergedLeader mergedLeader) {
        secondary.openIds?.each {OpenID secondaryOpenID ->

            addMergeRecord(secondaryOpenID, mergedLeader)

            OpenID primaryOpenID = new OpenID();
            primaryOpenID.url = secondaryOpenID.url
            primaryOpenID.createDate = secondaryOpenID.createDate
            primaryOpenID.updateDate = secondaryOpenID.updateDate
            primaryOpenID.leader = primary

            secondary.removeFromOpenIds(secondaryOpenID)
            secondaryOpenID.delete(flush: true)

            primary.addToOpenIds(primaryOpenID)
            primary.save(failOnError: true)


        }
    }

    void mergeTrainingClassRegistrations(Leader primary, Leader secondary, MergedLeader mergedLeader) {
        secondary.certificationClasses?.each {CertificationClass certificationClass ->
            addMergeRecord(certificationClass, mergedLeader)

            if (!primary.certificationClasses?.find {it.id == certificationClass.id}) {
                primary.addToCertificationClasses(certificationClass)
            }
            secondary.removeFromCertificationClasses(certificationClass)
            secondary.save(failOnError: true)
        }
    }

    void mergeInfusionsoftMergeInformation(Leader primary, Leader secondary, MergedLeader mergedLeader) {
        InfusionsoftLeaderInfo.findAllByLeader(secondary)?.each {
            addMergeRecord(it, mergedLeader)

            if (!InfusionsoftLeaderInfo.findByLeader(primary)) {
                InfusionsoftLeaderInfo copied = new InfusionsoftLeaderInfo()
                copied.infusionsoftContactId = it.infusionsoftContactId
                copied.leader = primary
                copied.save(failOnError: true)
            }
            it.delete(failOnError: true)
        }
    }

    void mergeLeaderRoles(Leader primary, Leader secondary, MergedLeader mergedLeader) {
        LeaderRole.findAllByLeader(secondary)?.each {
            LeaderRole role ->
            mergedLeader.addToMergedLeaderRoles(new MergedLeaderRole(role.role))
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

        primary.middleName = primary.middleName ?: secondary.middleName

        if(!primary.address1) {
            primary.address1 = secondary.address1
            primary.address2 = secondary.address2
            primary.city = secondary.city
            primary.state = secondary.state
            primary.postalCode = secondary.postalCode
        }

        primary.username = primary.username ?: secondary.username
        primary.password = primary.password ?: secondary.password
        primary.email = primary.email ?: secondary.email
        primary.phone = primary.phone ?: secondary.phone
        primary.setupDate = primary.setupDate ?: secondary.setupDate
        if (!primary.enabled) {
            primary.enabled = secondary.enabled
        }
    }

    void addMergeRecord(def object, String fieldName, MergedLeader mergedLeader) {
        MergedRecord mergedRecord = new MergedRecord(object.class, fieldName, (Long) object?.id)
        mergedRecord.mergedLeader = mergedLeader
        mergedLeader.addToMergedRecords(mergedRecord)
    }

    void addMergeRecord(def object, MergedLeader mergedLeader) {
        addMergeRecord(object, "leader", mergedLeader);
    }

    void mergeScoutIds(Leader primary, Leader secondary, MergedLeader mergedLeader) {
        secondary.myScoutingIds.each {

            MergedScoutingId scoutingId = new MergedScoutingId(scoutingId: it.myScoutingIdentifier)
            mergedLeader.addToMergedScoutingId(scoutingId);

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

    void mergeInactiveLeaderGroups(Leader primary, Leader secondary, MergedLeader mergedLeader) {
        LeaderCertification primaryLeaderCert;
        InactiveLeaderGroup.findAllByLeader(secondary)?.each {
            InactiveLeaderGroup inactiveLeaderGroup ->

            addMergeRecord(inactiveLeaderGroup, mergedLeader)

            InactiveLeaderGroup copied = new InactiveLeaderGroup()
            copied.leader = primary
            copied.scoutGroup = inactiveLeaderGroup.scoutGroup
            copied.createDate = inactiveLeaderGroup.createDate
            copied.save(failOnError: true)

            inactiveLeaderGroup.delete()
        }
    }

    void mergeLeaderCertifications(Leader primary, Leader secondary, MergedLeader mergedLeader) {
        LeaderCertification primaryLeaderCert;
        Set<LeaderCertification> leaderCertificationsToBeAdded = new HashSet<LeaderCertification>();

        // look for collisions, keep only the most recent cert relationships
        secondary.certifications.each() {
            addMergeRecord(it, mergedLeader)
            mergedLeader.addToMergedLeaderCertifications(new MergedLeaderCertification(it.dateEarned, it.certification))
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
            leaderCertification.enteredType = LeaderCertificationEnteredType.Merged
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

    void mergeLeaderGroups(Leader primary, Leader secondary, MergedLeader mergedLeader) {
        // look for collisions and handle them.
        LeaderGroup primaryScoutGroup;
        Set<LeaderGroup> groupsToAdd = new HashSet<LeaderGroup>();

        secondary.groups.each() {LeaderGroup leaderGroup ->
            mergedLeader.addToMergedLeaderGroups(new MergedLeaderGroup(leaderGroup.scoutGroup, leaderGroup.admin, leaderGroup.leaderPosition))
            addMergeRecord(leaderGroup, mergedLeader)

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

            LeaderGroup newGroup = new LeaderGroup(leader: primary, leaderPosition: it.leaderPosition)
            existingUnit.addToLeaderGroups(newGroup);
            primary.addToGroups(newGroup)
            existingUnit.save(flush: true, failOnError: true);
        }

        primary.save(failOnError: true);
        secondary.save(failOnError: true);
    }


    Leader findExactLeaderMatch(String scoutid, String email, String firstName, String lastName, ScoutGroup scoutGroup = null) {

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
            def matched = c.list {
                eq('firstName', firstName)
                eq('lastName', lastName)
                eq('email', email)
            }
            int maxTrainingRecords = 0
            matched.each {
                Leader foundLeader ->
                if (foundLeader.certifications?.size() > maxTrainingRecords) {
                    leader = foundLeader
                    maxTrainingRecords = foundLeader.certifications?.size()
                }
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

    Set<Leader> findDuplicateLeaders(Leader leader) {
        //Find by email address
        //Find by first & last name
        return Leader.withCriteria {
            if(leader.id) {
                ne('id', leader.id)
            }
            or {
                if(leader.email) {
                    eq('email', leader.email)
                }
                and {
                    if(leader.middleName) {
                        or {
                            and {
                                eq('firstName', leader.middleName)
                                isNull('middleName')
                            }
                            and {
                                eq('firstName', leader.firstName)
                                isNull('middleName')
                            }
                            and {
                                eq('firstName', leader.firstName)
                                eq('middleName', leader.middleName)
                            }
                        }

                    } else {
                       or {
                           eq('firstName', leader.firstName)
                           eq('middleName', leader.firstName)
                       }
                    }

                    eq('lastName', leader.lastName)
                }
                if(leader?.phone) {
                    eq('phone', leader.phone)
                }
                if(leader?.address1) {
                    eq('address1', leader.address1)
                }
            }
        }

    }
}
