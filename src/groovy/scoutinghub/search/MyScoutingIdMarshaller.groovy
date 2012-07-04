package scoutinghub.search

/**
 * User: ericm
 * Date: 7/3/12
 * Time: 4:51 PM
 */
class MyScoutingIdMarshaller {
    Object value

    void setValue(Object value) {
        this.value = value
    }

    @Override
    def getAsText() {
        def scoutingIds = []
        value?.each {
            scoutingIds << it.myScoutingIdentifier
        }
        return scoutingIds
    }
}
