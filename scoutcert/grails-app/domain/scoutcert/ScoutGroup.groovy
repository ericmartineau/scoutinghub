package scoutcert

class ScoutGroup implements Serializable {

    static searchable = true
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
        groupIdentifier(unique:true)
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

    @Override
    String toString() {
        return groupLabel ?: groupIdentifier
    }

    def beforeInsert = {
        createDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }
}
