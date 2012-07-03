package scoutinghub.geo

import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.HTTPBuilder
import scoutinghub.Address

import static groovyx.net.http.ContentType.URLENC

class YahooPlaceFinderGeoEncodingService implements GeoCodingService {

    SimpleCoordinates getCoordinatesForAddress(Address address) {

        def rtn = null;

        if (address.address && address.city) {
            String addressBlock = "${address.address ?: ""}, ${address.city ?: ""}, ${address.state}, ${address.zip}"
            def http = new HTTPBuilder("http://where.yahooapis.com/")

            def query = [q: addressBlock, appid: "aB1uAn42"]
            def result = http.get(path: "/geocode", query: query, requestContentType: URLENC)
            if(result instanceof NodeChild) {
                int found = Integer.parseInt(result.Found.text())
                if(found > 0) {
                    rtn = new SimpleCoordinates(
                            lat: Double.parseDouble(result.Result[0].latitude.text()),
                            lon: Double.parseDouble(result.Result[0].longitude.text())
                    )

                }
            }
//                    { resp, data ->
//                if (resp.statusLine.statusCode == 200) {
//
//                } else {
//                    println "Unknown status code: ${resp.statusLine}"
//                }
//
//            }

            println result


        }
        return rtn
    }


}
