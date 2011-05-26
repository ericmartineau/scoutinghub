dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = "scout"
    password = "sc0ut3r"
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
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost/scoutinghub"
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
            pooled = false
            jndiName = "java:comp/env/scoutinghub"
            dbCreate = "update"
        }
    }
}
