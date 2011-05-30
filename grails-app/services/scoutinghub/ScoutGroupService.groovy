package scoutinghub

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


    def getLeaderFilters() {
        def rtn = [:]
        rtn.unitType = [:]
        ScoutUnitType.values().each {ScoutUnitType type ->
            rtn.unitType[type.name()] = {
                scoutGroup {
                    eq('unitType', type)
                }

            }

        }

        rtn.directContact = [:]
        rtn.directContact.yes = {
            inList('leaderPosition', LeaderPositionType.values().findAll {it.directContact == true})
        }
        rtn.directContact.no = {
            inList('leaderPosition', LeaderPositionType.values().findAll {it.directContact == false})
        }

        rtn.keyLeaders = [:]
        rtn.keyLeaders.isAKeyLeader = {
            inList('leaderPosition', LeaderPositionType.values().findAll {it.keyLeaderPosition == true})
        }

        rtn.positionType = [:]
        LeaderPositionType.values().each {LeaderPositionType type ->
            rtn.positionType[type.name()] = {
                eq('leaderPosition', type)
            }
        }

        return rtn
    }



    def getFilters() {
        def rtn = [:]
        rtn.unitType = [:]
        ScoutUnitType.values().each {ScoutUnitType type ->
            rtn.unitType[type.name()] = {
                eq('unitType', type)
            }
        }

        rtn.directContact = [:]
        rtn.directContact.yes = {
            leaderGroups {
                inList('leaderPosition', LeaderPositionType.values().findAll {it.directContact == true})
            }
        }
        rtn.directContact.no = {
            leaderGroups {
                inList('leaderPosition', LeaderPositionType.values().findAll {it.directContact == false})
            }
        }

        rtn.keyLeaders = [:]
        rtn.keyLeaders.isAKeyLeader = {
            leaderGroups {
                inList('leaderPosition', LeaderPositionType.values().findAll {it.keyLeaderPosition == true})
            }
        }

        rtn.positionType = [:]
        LeaderPositionType.values().each {LeaderPositionType type ->
            rtn.positionType[type.name()] = {
                leaderGroups {
                    eq('leaderPosition', type)
                }
            }
        }
        return rtn
    }

    class IndexState {
        int currState
    }

}
