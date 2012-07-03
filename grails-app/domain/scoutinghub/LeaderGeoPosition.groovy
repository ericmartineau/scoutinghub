package scoutinghub

class LeaderGeoPosition {

    Double lat
    Double lon
    Date timestamp
    Leader leader

    static searchable = {
        root false
        except = ["leader"]
    }

    static constraints = {
        lat(nullable: true)
        lon(nullable: true)
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
