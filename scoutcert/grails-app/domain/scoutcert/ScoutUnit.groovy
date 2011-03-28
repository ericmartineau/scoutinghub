package scoutcert

class ScoutUnit implements Serializable {

    static searchable = true
    String unitIdentifier
    String unitLabel
    ScoutUnit parent;
    ScoutUnitType unitType
    Integer leftNode
    Integer rightNode

    static hasMany = [childUnits:ScoutUnit, leaders:Leader]

    static constraints = {
        unitIdentifier(unique:true)
        unitLabel(nullable:true)
        parent(nullable:true)
        leftNode(nullable:true)
        rightNode(nullable:true)
    }

    static mapping = {
        cache(true)
    }

    String toCrumbString() {
        List<String> names = []
        ScoutUnit unit = this;
        StringBuilder rtn = new StringBuilder()
        while(unit) {
            names << unit?.unitLabel ?: unit.unitIdentifier
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
        return unitLabel ?: unitIdentifier
    }
}
