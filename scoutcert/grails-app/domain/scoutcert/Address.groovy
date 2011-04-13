package scoutcert

class Address {

    String locationName
    String address
    String city
    String state
    String zip

    @Override
    String toString() {
        return locationName
    }


    static constraints = {
    }
}
