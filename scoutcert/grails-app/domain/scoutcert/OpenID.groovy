package scoutcert



class OpenID implements Serializable {

	String url
    Leader leader

	static belongsTo = [leader: Leader]

	static constraints = {
		url unique: true
	}

    static mapping = {
        leader(fetchMode: "eager")
    }
}
