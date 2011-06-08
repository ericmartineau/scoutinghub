import scoutinghub.CustomMyISAMDialect

dataSource {
    pooled = true

    dialect = scoutinghub.CustomMyISAMDialect


    //If you want to use mysql for your development environment (so your database doesn't get
    // blown away every time), uncomment these lines.  You'll also need to change the url below
    // in the development block of code:
//    driverClassName = "com.mysql.jdbc.Driver"
//    username = "scout"
//    password = "sc0ut3r"

    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""

}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dialect = scoutinghub.CustomMyISAMDialect

//            url = "jdbc:hsqldb:mem:devDb"
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
    driverClassName = "com.mysql.jdbc.Driver"
    username = "eric"
    password = "eric5425"


//              If you want to use mysql, use the following configuration
            url = "jdbc:mysql://localhost/scoutinghub"
//            dbCreate = "update" // one of 'create', 'create-drop','update'

        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            dialect = scoutinghub.CustomMyISAMDialect
            pooled = false
            jndiName = "java:comp/env/scoutinghub"
            dbCreate = "update"
        }
    }
}
