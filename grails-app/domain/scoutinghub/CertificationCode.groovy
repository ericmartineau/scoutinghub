package scoutinghub

class CertificationCode {

    Certification certification
    String code

    static belongsTo = [Certification]

    static constraints = {
        code(blank:false, unique:true)
    }

    static mapping = {
        sort("code")
    }

    @Override
    String toString() {
        return code
    }


}
