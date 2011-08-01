package scoutinghub.seed

import scoutinghub.Certification
import scoutinghub.CertificationType
import scoutinghub.LeaderPositionType
import scoutinghub.ProgramCertification

class SeedService {



    void addCertification(String name, List<String> codes, CertificationType certificationType, Collection<LeaderPositionType> position, int duration = 0,
                          boolean tourPermitRequired = false, boolean required = true) {
        Certification certification = new Certification(
                name: name,
                certificationType: certificationType,
                description: "",
                durationInDays: duration,
                tourPermitRequired: tourPermitRequired);


        codes.each {
            certification.addToCertificationCodes([code: it])
        }

        Date startDate = Date.parse('yyyy/MM/dd', '1973/01/01')
        Date endDate = Date.parse('yyyy/MM/dd', '2030/01/01')

        certification.save(failOnError: true)

        position.each {
            ProgramCertification programCertification = new ProgramCertification(unitType: null,
                    certification: certification,
                    positionType: it, required: required,
                    startDate: startDate,
                    endDate: endDate);
            programCertification.save(failOnError: true);
        }
    }

    void seedCertifications() {

        if(Certification.findByName("Cubmaster Fast Start")) {
            return
        }

        //Add the fast starts for each leaderPosition
        addCertification("Cubmaster Fast Start", ["C72", "CF3"], CertificationType.FastStart, [LeaderPositionType.Cubmaster, LeaderPositionType.AssistantCubmaster], 1780)
        addCertification("Tiger Cub Leader Fast Start", ["C73", "CF6"], CertificationType.FastStart, [LeaderPositionType.TigerLeader], 1780)
        addCertification("Webelos Den Leader Fast Start", ["C71", "CF2"], CertificationType.FastStart, [LeaderPositionType.WebelosLeader, LeaderPositionType.AssistantWebelosLeader], 1780)
        addCertification("Den Leader Fast Start", ["C70", "CF1"], CertificationType.FastStart, [LeaderPositionType.DenLeader, LeaderPositionType.AssistantDenLeader], 1780)
        addCertification("Pack Committee Fast Start", ["WCF4", "CF4"], CertificationType.FastStart, [LeaderPositionType.CommitteeMember, LeaderPositionType.CommitteeChair], 1780)
        addCertification("Boy Scouting or Varsity Scout Leader Fast Start", ["SFS", "WSFS"], CertificationType.FastStart,
                [LeaderPositionType.Scoutmaster, LeaderPositionType.AssistantScoutMaster, LeaderPositionType.VarsityCoach, LeaderPositionType.AssistantVarsityCoach], 1780)
        addCertification("Venturing Crew Leader Fast Start", ["PFS", "WPFS"], CertificationType.FastStart, [LeaderPositionType.CrewAdvisor, LeaderPositionType.AssistantCrewAdvisor], 1780)
        addCertification("ScoutParents Unit Coordinator Fast Start", ["CFS", "WCFS"], CertificationType.FastStart, [LeaderPositionType.ScoutParentsUnitCoordinator], 1780)

        //Add leader specific
        addCertification("Cubmaster Specific Training", ["C40"], CertificationType.LeaderSpecific, [LeaderPositionType.Cubmaster, LeaderPositionType.AssistantCubmaster], 1780, true)
        addCertification("Tiger Leader Specific Training", ["C41"], CertificationType.LeaderSpecific, [LeaderPositionType.TigerLeader], 1780, true)
        addCertification("Den Leader Specific Training", ["C42"], CertificationType.LeaderSpecific, [LeaderPositionType.DenLeader, LeaderPositionType.AssistantDenLeader], 1780, true)
        addCertification("Committee Specific Training", ["C60"], CertificationType.LeaderSpecific, [LeaderPositionType.CommitteeChair, LeaderPositionType.CommitteeMember], 1780, true)
        addCertification("Webelos Leader Specific Training", ["C61"], CertificationType.LeaderSpecific, [LeaderPositionType.WebelosLeader, LeaderPositionType.AssistantWebelosLeader], 1780, true)
        addCertification("Pack Trainer Specific Training", ["C62"], CertificationType.LeaderSpecific, [LeaderPositionType.PackTrainer], 1780, true)
        addCertification("Chartered Org Rep Specific Training", ["D72"], CertificationType.LeaderSpecific, [LeaderPositionType.CharterRep], 1780, true)

        addCertification("Scoutmaster Specific Training", ["S24"], CertificationType.LeaderSpecific, [LeaderPositionType.Scoutmaster, LeaderPositionType.AssistantScoutMaster], 1780, true)
        addCertification("Crew Leader Specific Training", ["P21"], CertificationType.LeaderSpecific, [LeaderPositionType.CrewAdvisor, LeaderPositionType.AssistantCrewAdvisor], 1780, true)
        addCertification("Varsity Leader Specific Training", ["V21"], CertificationType.LeaderSpecific, [LeaderPositionType.VarsityCoach, LeaderPositionType.AssistantVarsityCoach], 1780, true)

        //This is Scouting
        addCertification("This is Scouting", ["A01", "WA01"], CertificationType.ThisIsScouting, LeaderPositionType.values().findAll {it.scoutUnitTypes?.size() > 0})
        addCertification("Youth Protection Training", ["Y01"], CertificationType.YouthProtection, LeaderPositionType.values().findAll {return it != LeaderPositionType.CrewAdvisor && it != LeaderPositionType.AssistantCrewAdvisor}, 730, true)
        addCertification("Venturing Youth Protection Training", ["Y02"], CertificationType.YouthProtection, [LeaderPositionType.CrewAdvisor, LeaderPositionType.AssistantCrewAdvisor], 730, true)

        //Outdoor leader skills
        addCertification("Outdoor Leader Skills", ["S11"], null, [LeaderPositionType.Scoutmaster, LeaderPositionType.AssistantScoutMaster, LeaderPositionType.VarsityCoach,
                LeaderPositionType.AssistantVarsityCoach], 1780, true)


    }
}
