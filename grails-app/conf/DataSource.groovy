import scoutinghub.CustomMyISAMDialect

dataSource {
    pooled = true
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
//            url = "jdbc:hsqldb:mem:devDb"
//            dbCreate = "create-drop" // one of 'create', 'create-drop','update'

        //If you want to use mysql for your development environment (so your database doesn't get
            // blown away every time), uncomment these lines.  You'll also need to change the url below
            // in the development block of code - don't check in!:
            driverClassName = "com.mysql.jdbc.Driver"
            username = "eric"
            password = "eric5425"
            url = "jdbc:mysql://localhost/scoutinghub"
            dbCreate = "update" // one of 'create', 'create-drop','update'

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
