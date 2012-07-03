package scoutinghub.geo

import scoutinghub.Address

/**
 * Created with IntelliJ IDEA.
 * User: ericm
 * Date: 7/2/12
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
interface GeoCodingService {
    SimpleCoordinates getCoordinatesForAddress(Address address)
}
