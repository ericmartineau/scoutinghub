package scoutinghub

class Role implements Serializable {

	String authority

    Date createDate;
    Date updateDate;

	static mapping = {
		cache(true)
	}

	static constraints = {
		authority blank: false, unique: true
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

