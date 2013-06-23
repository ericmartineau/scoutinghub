package scoutinghub

class CertificationReport {

    static mapWith = "neo4j"

    ScoutGroup scoutGroup
    double pctTrained
    int reportCount
    Date reportDate

    static constraints = {
    }
}
