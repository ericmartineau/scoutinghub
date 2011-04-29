package scoutcert

class ScoutGroup implements Serializable {

    static searchable = {
        id name: 'scoutGroupId'
        parent component: [maxDepth: 6]
    }

    String groupIdentifier
    String groupLabel
    ScoutGroup parent;
    ScoutGroupType groupType
    ScoutUnitType unitType

    Integer leftNode
    Integer rightNode
    Date createDate;
    Date updateDate;

    static hasMany = [childGroups:ScoutGroup, leaderGroups:LeaderGroup]

    static constraints = {

        groupLabel(nullable:true)
        parent(nullable:true)
        leftNode(nullable:true)
        rightNode(nullable:true)
        unitType(nullable:true, validator: {val, ScoutGroup grp->
            if(grp.groupType == ScoutGroupType.Unit && !grp.unitType) {
                return ['scoutGroup.unitType.required']
            }
        })
        createDate nullable: true
        updateDate nullable: true

    }

    static mapping = {
        cache(true)
        leaderGroups cascade:'all-delete-orphan'
    }

    String toCrumbString() {
        List<String> names = []
        ScoutGroup unit = this;
        StringBuilder rtn = new StringBuilder()
        while(unit) {
            names << unit?.groupLabel ?: unit.groupIdentifier
            unit = unit.parent
        }

        ListIterator<String> namesIterator = names.listIterator(names.size())
        while(namesIterator.hasPrevious()) {
            rtn.append(namesIterator.previous())
            if(namesIterator.hasPrevious()) {
                rtn.append("&nbsp;>&nbsp;")
            }
        }
        return rtn.toString()
    }

    boolean canBeAdministeredBy(Leader leader) {
        LeaderGroup found = leader.groups?.find {LeaderGroup lg ->
            return lg.scoutGroup.id == this.id && lg.admin
        }
        boolean rtn
        if(found) {
            rtn = true
        } else {
            rtn = parent?.canBeAdministeredBy(leader)
        }
        return rtn
    }

    List<LeaderPositionType> findApplicablePositionTypes() {
        LeaderPositionType.values().findAll {LeaderPositionType type->
            boolean matchesGroup = type.scoutGroupTypes.find {it == groupType} != null
            boolean matchesUnit = type.scoutUnitTypes.find {it == unitType} != null
            return matchesGroup || matchesUnit;

        }
    }

    @Override
    String toString() {
        String rootName = groupLabel ?: groupIdentifier
        if(unitType) {
            rootName += " (${unitType})"
        }
        return rootName
    }

    def beforeInsert = {
        createDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }
}
