package scoutcert

class RequestMap {

	String url
	String configAttribute

    Date createDate;
    Date updateDate;

	static mapping = {
		cache true
	}

	static constraints = {
		url blank: false, unique: true
		configAttribute blank: false
        createDate nullable: true
        updateDate nullable: true
	}

    def beforeInsert = {
        createDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }
}
