package scoutinghub

class CertificationCode {

    Certification certification
    String code

    static belongsTo = [Certification]

    static constraints = {
        code(blank:false, unique:true)
    }
}
