package scoutinghub

class Address {

    String locationName
    String address
    String city
    String state
    String zip

    Long latitude
    Long longitude

    @Override
    String toString() {
        return locationName
    }


    static constraints = {
        locationName(blank:false)
        address(blank:false)
        city(blank:false)
        state(blank:false)
        zip(blank:false)
    }
}
