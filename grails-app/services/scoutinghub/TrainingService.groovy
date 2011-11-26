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

        int count = 0
        Leader.withTransaction {TransactionStatus status ->
            ids.each {
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

        return ids
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
