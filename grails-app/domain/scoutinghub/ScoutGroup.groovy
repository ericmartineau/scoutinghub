package scoutinghub

/**
 * A 'node' in the scouting organization.  ScoutGroups are hierarchical, and are categorized as follows:
 *
 * All units have a ScoutGroupType.  This is a fairly arbitrary assignment, but is allows a more concrete and
 * familiar association with a scouting organization.
 *
 * If groupType == ScoutGroupType.Unit, then an additional field is mapped: unitType.  A ScoutUnitType is one of Pack, Troop, Crew, or Team.
 *
 * Leaders are attached to ScoutGroup instances, which allows us to attach leaders at the council, district, and unit levels.  We can also
 * create somewhat arbitrary organizations (such as Ward, Stake, or Community) that we can attach permissions to.
 *
 */
class ScoutGroup implements Serializable {

    static searchable = {
        id name: 'scoutGroupId'
        parent component: [maxDepth: 6]
    }

    /**
     * The 4-digit group unit identifier.  For non-units, this is usually some form of the group name.
     */
    String groupIdentifier

    /**
     * A readable label for this scouting group
     */
    String groupLabel
    ScoutGroup parent;

    ScoutGroupType groupType

    /**
     * Only required when groupType = Unit
     */
    ScoutUnitType unitType

    /**
     * Depth-first index of node's left value
     */
    Integer leftNode

    /**
     * Depth-first index of node's right value
     */
    Integer rightNode
    Date createDate;
    Date updateDate;

    static hasMany = [childGroups: ScoutGroup, leaderGroups: LeaderGroup]

    static constraints = {

        groupLabel(nullable: true)
        parent(nullable: true)
        leftNode(nullable: true)
        rightNode(nullable: true)
        unitType(nullable: true, validator: {val, ScoutGroup grp ->
            if (grp.groupType == ScoutGroupType.Unit && !grp.unitType) {
                return ['scoutGroup.unitType.required']
            }
        })
        createDate nullable: true
        updateDate nullable: true

    }

    static mapping = {
        cache(true)
        leaderGroups cascade: 'all-delete-orphan'
    }

    String toCrumbString() {
        List<String> names = []
        ScoutGroup unit = this;
        StringBuilder rtn = new StringBuilder()
        while (unit) {
            names << unit?.groupLabel ?: unit.groupIdentifier
            unit = unit.parent
        }

        ListIterator<String> namesIterator = names.listIterator(names.size())
        while (namesIterator.hasPrevious()) {
            rtn.append(namesIterator.previous())
            if (namesIterator.hasPrevious()) {
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
        if (found) {
            rtn = true
        } else {
            rtn = parent?.canBeAdministeredBy(leader)
        }
        return rtn
    }

    List<LeaderPositionType> findApplicablePositionTypes() {
        LeaderPositionType.values().findAll {LeaderPositionType type ->
            boolean matchesGroup = type.scoutGroupTypes.find {it == groupType} != null
            boolean matchesUnit = type.scoutUnitTypes.find {it == unitType} != null
            return matchesGroup || matchesUnit;

        }
    }

    @Override
    String toString() {
        String rootName = groupLabel ?: groupIdentifier
        if (unitType) {
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
