package scoutinghub

class SeedService {


    void seedCertifications() {
        def addCertification = {String name, List<String> codes, CertificationType certificationType, Collection<LeaderPositionType> position, int duration = 1780, boolean tourPermitRequired = false ->
            Certification certification = new Certification(
                    name: name,
                    certificationType: certificationType,
                    description: "",
                    durationInDays: duration,
                    tourPermitRequired: tourPermitRequired);


            codes.each {
                certification.addToCertificationCodes([code:it])
            }

            Date startDate = new Date().parse('yyyy/MM/dd', '1973/01/01')
            Date endDate = new Date().parse('yyyy/MM/dd', '2030/01/01')

            certification.save(failOnError: true)

            position.each {
                ProgramCertification programCertification = new ProgramCertification(unitType: null,
                        certification: certification,
                        positionType: it, required: true,
                        startDate: startDate,
                        endDate: endDate);
                programCertification.save(failOnError: true);
            }
        }

        //Add the fast starts for each leaderPosition
        addCertification("Cubmaster Fast Start", ["C72", "CF3"], CertificationType.FastStart, [LeaderPositionType.Cubmaster, LeaderPositionType.AssistantCubmaster], 1780)
        addCertification("Tiger Cub Leader Fast Start", ["C73", "CF6"], CertificationType.FastStart, [LeaderPositionType.TigerLeader], 1780)
        addCertification("Webelos Den Leader Fast Start", ["C71", "CF2"], CertificationType.FastStart, [LeaderPositionType.WebelosLeader, LeaderPositionType.AssistantWebelosLeader], 1780)
        addCertification("Den Leader Fast Start", ["C70", "CF1"], CertificationType.FastStart, [LeaderPositionType.DenLeader, LeaderPositionType.AssistantDenLeader], 1780)
        addCertification("Pack Committee Fast Start", ["WCF4", "CF4"], CertificationType.FastStart, [LeaderPositionType.CommitteeMember], 1780)
        addCertification("Boy Scouting or Varsity Scout Leader Fast Start", ["SFS", "WSFS"], CertificationType.FastStart,
                [LeaderPositionType.Scoutmaster, LeaderPositionType.AssistantScoutMaster, LeaderPositionType.VarsityCoach, LeaderPositionType.AssistantVarsityCoach], 1780)
        addCertification("Venturing Crew Leader Fast Start", ["PFS", "WPFS"], CertificationType.FastStart, [LeaderPositionType.CrewAdvisor, LeaderPositionType.AssistantCrewAdvisor], 1780)
        addCertification("ScoutParents Unit Coordinator Fast Start", ["CFS", "WCFS"], CertificationType.FastStart, [LeaderPositionType.ScoutParentsUnitCoordinator], 1780)

        //Add leader specific
        addCertification("Cubmaster Specific Training", ["C40"], CertificationType.LeaderSpecific, [LeaderPositionType.Cubmaster, LeaderPositionType.AssistantCubmaster], 1780, true)
        addCertification("Tiger Leader Specific Training", ["C41"], CertificationType.LeaderSpecific, [LeaderPositionType.TigerLeader], 1780, true)
        addCertification("Den Leader Specific Training", ["C42"], CertificationType.LeaderSpecific, [LeaderPositionType.DenLeader, LeaderPositionType.AssistantDenLeader], 1780, true)
        addCertification("Committee Specific Training", ["C60"], CertificationType.LeaderSpecific, [LeaderPositionType.CommitteeChair,LeaderPositionType.CommitteeMember], 1780, true)
        addCertification("Webelos Leader Specific Training", ["C61"], CertificationType.LeaderSpecific, [LeaderPositionType.WebelosLeader, LeaderPositionType.AssistantWebelosLeader], 1780, true)
        addCertification("Pack Trainer Specific Training", ["C62"], CertificationType.LeaderSpecific, [LeaderPositionType.PackTrainer], 1780, true)
        addCertification("Chartered Org Rep Specific Training", ["D72"], CertificationType.LeaderSpecific, [LeaderPositionType.CharterRep], 1780, true)

        addCertification("Scoutmaster Specific Training", ["S24"], CertificationType.LeaderSpecific, [LeaderPositionType.Scoutmaster, LeaderPositionType.AssistantScoutMaster], 1780, true)
        addCertification("Crew Leader Specific Training", ["P21"], CertificationType.LeaderSpecific, [LeaderPositionType.CrewAdvisor, LeaderPositionType.AssistantCrewAdvisor], 1780, true)
        addCertification("Varsity Leader Specific Training", ["V21"], CertificationType.LeaderSpecific, [LeaderPositionType.VarsityCoach, LeaderPositionType.AssistantVarsityCoach], 1780, true)

        //This is Scouting
        addCertification("This is Scouting", ["A01", "WA01"], CertificationType.ThisIsScouting, LeaderPositionType.values().findAll {it.scoutUnitTypes?.length > 0})
        addCertification("Youth Protection Training", ["Y01"], CertificationType.YouthProtection, LeaderPositionType.values().findAll {return it != LeaderPositionType.CrewAdvisor && it != LeaderPositionType.AssistantCrewAdvisor}, 730, true)
        addCertification("Venturing Youth Protection Training", ["Y02"], CertificationType.YouthProtection, [LeaderPositionType.CrewAdvisor, LeaderPositionType.AssistantCrewAdvisor], 730, true)

        //Outdoor leader skills
        addCertification("Outdoor Leader Skills", ["S11"], null, [LeaderPositionType.Scoutmaster, LeaderPositionType.AssistantScoutMaster, LeaderPositionType.VarsityCoach,
                LeaderPositionType.AssistantVarsityCoach], 1780, true)

//        Certification baloo = new Certification(externalId: "baloo",
//                name: "BALOO - Basic Adult Leader Outdoor Orientation",
//                description: "for Cub Scout leaders is a one-day training event that introduces participants to the skills needed to plan and conduct Pack outdoor activities, particularly pack camping.",
//                durationInDays: 1780,
//                tourPermitRequired: true);
//        baloo.save(failOnError: true)
//
//        Certification woodbadge = new Certification(externalId: "woodBadge",
//                name: "Wood Badge",
//                description: "Advanced training in leadership and team development, bringing together all programs - Cub Scouting, Boy Scouting, and Venturing.",
//                durationInDays: 1780,
//                tourPermitRequired: false);
//        woodbadge.save(failOnError: true)
//
//        Certification weather = new Certification(externalId: "weather",
//                name: "Hazardous Weather",
//                description: "planning and decision making regarding weather for a safe outing. Can be taken online at BSA Hazardous Weather",
//                durationInDays: 1780,
//                tourPermitRequired: true);
//        weather.save(failOnError: true)
//
//        Certification safeSwim = new Certification(externalId: "safeswim",
//                name: "Safe Swim Defense",
//                description: "Introduction to BSA water safety policies. Can be taken online at BSA Aquatics",
//                durationInDays: 730,
//                tourPermitRequired: true);
//        safeSwim.save(failOnError: true)
//
//        Certification safetyAfloat = new Certification(externalId: "safetyafloat",
//                name: "Safety Afloat",
//                description: "Introduction to BSA boating policies.  Water craft events required Safety Afloat trained leaders. Can be taken online at BSA Aquatics.",
//                durationInDays: 730,
//                tourPermitRequired: true);
//        safetyAfloat.save(failOnError: true)
//
//        Certification climbOn = new Certification(externalId: "climbon",
//                name: "Climb On Safely",
//                description: "This training lasts about 45 minutes and provides all the information you need to meet the minimum requirements for a climbing activity. This does not consist of any training or certifications in climbing, but provides the essential components of a safe outing.",
//                durationInDays: 730,
//                tourPermitRequired: true);
//        climbOn.save(failOnError: true)
//
//        Certification cpr = new Certification(externalId: "cpr",
//                name: "CPR Certification",
//                description: "Normally not offered through a BSA unit, but at least two currently CPR certified adults are required on many high adventure outings.",
//                durationInDays: 730,
//                tourPermitRequired: false);
//        cpr.save(failOnError: true)

    }
}
