package scoutinghub



class OpenID implements Serializable {



    String url
    Leader leader

    Date createDate;
    Date updateDate;

	static belongsTo = [Leader]

	static constraints = {
		url unique: true
        createDate nullable: true
        updateDate nullable: true
	}

    static mapping = {
        leader(fetchMode: "eager")
    }

    def beforeInsert = {
        createDate = new Date()
    }

    def beforeUpdate = {
        updateDate = new Date()
    }
}
