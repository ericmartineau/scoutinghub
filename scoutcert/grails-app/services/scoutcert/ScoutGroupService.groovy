package scoutcert

class ScoutGroupService {

    static transactional = false

    void reindex() {
        IndexState state = new IndexState()
        def topNodes = ScoutGroup.findAllByParentIsNull();
        topNodes.each {ScoutGroup group->
            handleGroup(group, state)
        }

    }

    void handleGroup(ScoutGroup group, IndexState indexState) {
        group.leftNode = indexState.currState++

        group.childGroups.each {ScoutGroup child->
            handleGroup(child, indexState)
        }

        group.rightNode = indexState.currState++
        group.save()
    }

    class IndexState {
        int currState
    }

}
