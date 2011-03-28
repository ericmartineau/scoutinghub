package scoutcert

class MyScoutingId implements Serializable{

    static searchable = true

    Leader leader
    String myScoutingIdentifier

    static constraints = {
        myScoutingIdentifier(unique:true)
    }
}
