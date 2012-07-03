package scoutinghub.geo

import scoutinghub.Address
import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.ContentType.URLENC
import org.apache.commons.io.IOUtils

/**
 * User: ericm
 * Date: 7/2/12
 * Time: 10:14 PM
 */
class GeoCoderDotUsEncodingService implements GeoCodingService {

    SimpleCoordinates getCoordinatesForAddress(Address address) {

        def rtn = null;

        if (address.address && address.city) {
            String addressBlock = "${address.address ?: ""}, ${address.city ?: ""}, ${address.state}, ${address.zip}"
            def http = new HTTPBuilder("http://rpc.geocoder.us")

            def postBody = [address: addressBlock, parse_address: 1]
            http.post(path: "/service/namedcsv", body: postBody, requestContentType: URLENC) { resp, data ->
                if (resp.statusLine.statusCode == 200) {
                    def lines = IOUtils.readLines(data)
                    println "Geocoding response: " + lines.size()
                    if (lines.size() > 1) {
                        String resultList = lines[1]
                        def props = [:]

                        String[] pairs = resultList.split(",")
                        for (String pair : pairs) {
                            String[] keyValue = pair.split("=")
                            if (keyValue.length > 1) {
                                props[keyValue[0]] = keyValue[1]
                                println keyValue[0] + "=" + keyValue[1]
                            }
                        }
                        if (props.lat && props.long) {
                            rtn = new SimpleCoordinates(
                                    lat: Double.parseDouble(props.lat),
                                    lon: Double.parseDouble(props.long)
                            )
                        }
                    }
                } else {
                    println "Unknown status code: ${resp.statusLine}"
                }

            }


        }
        return rtn
    }


}
