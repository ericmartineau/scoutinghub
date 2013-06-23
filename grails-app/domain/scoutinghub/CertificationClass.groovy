package scoutinghub

/**
 * A certification class is a scheduled delivery of adult leader training.  Leaders can register or unregister for the
 * event, and an event coordinator can track attendance.
 */
class CertificationClass {

    static mapWith = "neo4j"

    /**
     * Which certification is being offered at the class/event
     */
    Certification certification
    Date classDate
    String time
    Address location
    Integer leaderId

    static belongsTo = [Leader]
    static hasMany = [registrants:Leader]

    @Override
    String toString() {
        return "${certification.name} ${classDate.format("MM-dd-yyyy")}"
    }

    static constraints = {
    }

    static mapping = {
        cache(true)
    }

}
