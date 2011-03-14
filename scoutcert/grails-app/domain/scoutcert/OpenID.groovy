package scoutcert



class OpenID {

	String url

	static belongsTo = [user: Leader]

	static constraints = {
		url unique: true
	}
}
