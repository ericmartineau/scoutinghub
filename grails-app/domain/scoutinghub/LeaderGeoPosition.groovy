package scoutinghub

class LeaderGeoPosition {

    Double latitude
    Double longitude
    Date timestamp
    Leader leader

    static searchable = {
        root false
        except = ["leader"]
    }

    static constraints = {
        latitude(nullable: true)
        longitude(nullable: true)
        timestamp(nullable: true)
    }

    static belongsTo = [Leader]

    def beforeInsert() {
        timestamp = new Date()
    }

    def beforeUpdate() {
        timestamp = new Date()
    }
}
