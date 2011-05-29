package scoutinghub

class CertificationClass {

    Certification certification
    Date classDate
    String time
    Address location

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
