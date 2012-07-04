package scoutinghub.search

/**
 * Created with IntelliJ IDEA.
 * User: ericm
 * Date: 7/3/12
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
class LeaderGroupMarshaller {
    Object value

    void setValue(Object value) {
        this.value = value
    }

    @Override
    def getAsText() {
        def positions = [] as Set
        def groups = [] as Set

        value?.each {
            positions << it.leaderPosition
            groups << it.scoutGroup.id
        }

        return [
                positions: positions,
                groups: groups
        ]

    }
}
