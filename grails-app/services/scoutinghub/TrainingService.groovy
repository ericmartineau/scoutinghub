package scoutinghub

import org.hibernate.SessionFactory
import org.springframework.transaction.TransactionStatus

class TrainingService {

    static transactional = true

    SessionFactory sessionFactory

    Collection processExpiredTrainings() {
        //find all certifications that have expirations in the past
        Date date = new Date()
        Set<Long> ids = [] as Set;
        def criteria = LeaderCertification.withCriteria {
            and {
                isNotNull("expirationDate")
                lt('expirationDate', date)
                or {
                    ltProperty("lastCalculationDate", "expirationDate")
                    isNull("lastCalculationDate")
                }

            }

        }.each {LeaderCertification leaderCertification ->
            ids.add(leaderCertification.leader?.id)
        }

        recalculateTrainingForLeaders(ids)

        return ids
    }

    Collection processTrainingsForCertification(Certification certification) {
        //find all certifications that have expirations in the past
        Date date = new Date()
        Set<Long> ids = [] as Set;
        LeaderCertification.withTransaction { TransactionStatus status ->
            def results = LeaderCertification.executeQuery("select distinct lc.leader.id from LeaderCertification as lc where lc.certification.id = ?", [certification.id])
            ids.addAll(results)
        }

        recalculateTrainingForLeaders(ids)

        return ids
    }

    Collection processCertificationsForModifiedTrainings() {
        Date date = new Date()
        Set<Long> ids = [] as Set;
        LeaderCertification.withTransaction { TransactionStatus status ->
            def results = LeaderCertification.executeQuery("select distinct lc.leader.id from LeaderCertification as lc, ProgramCertification as pc where pc.updateDate is not null and pc.certification.id = lc.certification.id and pc.updateDate > lc.lastCalculationDate")
            ids.addAll(results)
        }

        recalculateTrainingForLeaders(ids)

        return ids
    }

    Collection reprocessAllTrainings() {
        Date date = new Date()
        Set<Long> ids = [] as Set;
        LeaderCertification.withTransaction { TransactionStatus status ->
            def results = LeaderCertification.executeQuery("select distinct lc.leader.id from LeaderCertification as lc")
            ids.addAll(results)
        }

        recalculateTrainingForLeaders(ids)

        return ids
    }

    private def recalculateTrainingForLeaders(Set<Long> leaderIds) {

        Date date = new Date()
        int count = 0
        Leader.withTransaction {TransactionStatus status ->
            leaderIds.each {
                Leader leader = Leader.get(it)
                leader.certifications?.each {

                    it.lastCalculationDate = date
                }
                println "Recalcing for ${leader}"
                recalculatePctTrained(leader)
                sessionFactory.currentSession.flush();
                count++;
                if (count % 50 == 0) {
                    status.flush()
                    sessionFactory.currentSession.clear()
                }

            }
        }

        println("Recalculated ${count} leaders")
    }

    void recalculatePctTrained(Leader leader) {

        int numCompletedCertifications;

        def requiredCertifications

        for (LeaderGroup group: leader.groups) {
            numCompletedCertifications = 0
            requiredCertifications = getRequiredCertifications(group);

            requiredCertifications?.each {ProgramCertification progCert ->
                if (leader.certifications.find {it.certification.id == progCert.certification.id && !it.hasExpired()}) {
                    numCompletedCertifications++
                }
            }

            if (requiredCertifications?.size() > 0) {
                group.pctTrained = 100 * numCompletedCertifications / requiredCertifications?.size()
            } else {
                group.pctTrained = 100
            }
        }

        leader.save(failOnError: true)
    }

    List<ProgramCertification> getRequiredCertifications(LeaderGroup leaderGroup) {
        List<ProgramCertification> requiredCertifications = []
        def c = ProgramCertification.createCriteria()
        Date now = new Date()

        requiredCertifications = c.list {
            and {
                or {

                    if (leaderGroup.scoutGroup.unitType) {
                        inList('unitType', leaderGroup.scoutGroup.unitType)
                    }
                    inList('positionType', leaderGroup.leaderPosition)
                }
                eq('required', true)
            }
            eq('required', true)
            lt('startDate', now)
            gt('endDate', now)

            certification {
                sort: 'name'
            }
        }


        return requiredCertifications;
    }

}
