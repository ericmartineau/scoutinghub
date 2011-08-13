package scoutinghub

import grails.test.*
import grails.plugins.springsecurity.SpringSecurityService
import scoutinghub.infusionsoft.InfusionsoftLeaderInfo
import org.hibernate.SessionFactory

class LeaderServiceTests extends GroovyTestCase {

    SpringSecurityService springSecurityService

    LeaderService leaderService

    SessionFactory sessionFactory

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testLeaderMerge() {

        Leader user = Leader.findByUsername("admin")

        Leader primary = new Leader()
        primary.firstName = "Bob"
        primary.lastName = "Jones"
        primary.email = "bobby@jones.com"
        primary.phone = "480-227-3299"
        primary.username = "bobby"
        primary.password = springSecurityService.encodePassword("890iop")

        Address address = new Address()
        address.address = "123 W. East"
        address.city = "Gilbert"
        address.state = "AZ"
        address.zip = "85295"
        address.locationName = "My House"
        address.save(failOnError:true)

        CertificationClass certificationClass = new CertificationClass()
        certificationClass.classDate = new Date()
        Certification yptTraining = CertificationCode.findByCode("Y01").certification
        certificationClass.certification = yptTraining
        certificationClass.time = "10AM"

        certificationClass.location = address;
        certificationClass.save(failOnError:true)

        primary.addToCertificationClasses(certificationClass)

        primary.addToCertifications([certification: yptTraining, dateEntered: new Date(), dateEarned: new Date(), enteredBy: user, enteredType: LeaderCertificationEnteredType.ManuallyEntered])
        primary.addToGroups([admin: true, leaderPosition: LeaderPositionType.Cubmaster, scoutGroup: ScoutGroup.findByGroupIdentifier("381")])
        primary.addToMyScoutingIds([myScoutingIdentifier: "123456"])
        primary.addToMyScoutingIds([myScoutingIdentifier: "123456789"])
        primary.addToOpenIds([url: "google123"])

        primary.save(failOnError:true)

        InfusionsoftLeaderInfo primaryInfo = new InfusionsoftLeaderInfo()
        primaryInfo.infusionsoftContactId = 123
        primaryInfo.leader = primary
        primaryInfo.save()

        Leader secondary = new Leader()
        secondary.firstName = "Bob2"
        secondary.lastName = "Jones2"
        secondary.email = "bobby2@jones.com"
        secondary.phone = "480-332-8002"
        secondary.username = "bobby2"
        secondary.password = springSecurityService.encodePassword("890iop")

        Certification thisIsScouting = Certification.findByName("This is Scouting")

        CertificationClass certificationClass2 = new CertificationClass()
        certificationClass2.classDate = new Date()
        certificationClass2.certification = thisIsScouting
        certificationClass2.time = "10AM"
        certificationClass2.location = address;
        certificationClass2.save(failOnError:true)

        secondary.addToCertificationClasses(certificationClass2)


        secondary.addToCertifications([certification: thisIsScouting, dateEntered: new Date(), dateEarned: new Date(), enteredBy: user, enteredType: LeaderCertificationEnteredType.ManuallyEntered])
        secondary.addToGroups([admin: true, leaderPosition: LeaderPositionType.Scoutmaster, scoutGroup: ScoutGroup.findByGroupIdentifier("6381")])
        secondary.addToMyScoutingIds([myScoutingIdentifier: "456789"])
        secondary.addToOpenIds([url: "google1234"])

        secondary.save(failOnError:true)

        InfusionsoftLeaderInfo secondaryInfo = new InfusionsoftLeaderInfo()
        secondaryInfo.infusionsoftContactId = 123
        secondaryInfo.leader = secondary
        secondaryInfo.save(failOnError:true)

        sessionFactory.getCurrentSession()?.flush()

        leaderService.mergeLeaders(primary, secondary)

        primary.refresh()


    }
}
