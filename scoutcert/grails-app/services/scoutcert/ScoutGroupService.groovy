package scoutcert

class ScoutGroupService {

    static transactional = false

    void reindex() {
        IndexState state = new IndexState()
        def topNodes = ScoutGroup.findAllByParentIsNull();
        topNodes.each {ScoutGroup group ->
            handleGroup(group, state)
        }

    }

    void handleGroup(ScoutGroup group, IndexState indexState) {
        group.leftNode = indexState.currState++

        group.childGroups.each {ScoutGroup child ->
            handleGroup(child, indexState)
        }

        group.rightNode = indexState.currState++
        group.save()
    }

    def getFilters() {
        def rtn = [:]
        rtn.unitType = [:]
        ScoutUnitType.values().each {ScoutUnitType type->
            rtn.unitType[type.name()] = {
                eq('unitType', type)
            }

        }

        rtn.positionType = [:]
        LeaderPositionType.values().each {LeaderPositionType type->
            rtn.positionType[type.name()] = {
                leaderGroups {
                    eq('position', type)
                }
            }
        }

        rtn.directContact = [:]
        rtn.directContact.yes = {}
        rtn.directContact.no = {}


        return rtn
    }

    class IndexState {
        int currState
    }

}
