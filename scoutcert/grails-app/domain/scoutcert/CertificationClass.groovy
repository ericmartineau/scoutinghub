package scoutcert

class CertificationClass {

    Certification certification
    Date classDate
    String time
    Address location

    static hasMany = [registrants:Leader]

    static constraints = {
    }
}
