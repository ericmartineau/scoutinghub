package scoutcert

class ScoutUnit implements Serializable {

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

}
