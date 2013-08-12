grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.plugin.repos.distribution.martytime="http://martytime.com/nexus/content/repositories/martytime/"
grails.plugin.repos.discovery.martytime="http://martytime.com/nexus/content/repositories/martytime/"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {

        mavenRepo "https://www.cubtrails.com/nexus/content/repositories/martytime-snapshot"
        mavenRepo "https://www.cubtrails.com/nexus/content/repositories/martytime"

        mavenRepo "https://www.cubtrails.com/nexus/content/repositories/cubtrails/"
//        mavenRepo "http://martytime.com/nexus/content/repositories/martytime/"


        mavenRepo "http://oss.sonatype.org/content/repositories/releases/" //For elasticsearch

        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()

        mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"
//        mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://maven.springframework.org/milestone/"
        mavenRepo "https://repository.jboss.org/nexus/content/repositories/thirdparty-uploads"

        mavenRepo "http://snapshots.repository.codehaus.org"

    }

    plugins {

        compile ":elasticsearch:0.20.6.3"

        compile(":spring-security-core:1.2.7.3-cubtrails-3")

        compile ":hibernate:3.6.10.M4"
        build ":tomcat:7.0.41"

        build ":release:2.0.3"
        compile ":resources:1.1.6"

        compile ":executor:0.2"
        compile ":fixtures:1.0.7"
        compile ":geolocation:0.1"

        compile ":iwebkit:0.5"
        compile ":mail:1.0"

        compile ":oauth:0.10"
        compile ":quartz:0.4.2"
//        compile ":rendering:0.4.3"
        compile ":rest-client-builder:1.0.2"
        compile ":spring-events:1.2"
        compile (":spring-security-openid:1.0.4") {
            excludes "spring-security-core"
        }
        compile ":svn:1.0.0.M1"
        compile ":webflow:2.0.8.1"
        runtime ":jquery:1.10.0"

        //Solves issue with "No thread-bound request"
        runtime ":webxml:1.4.1"


    }

    dependencies {

        compile "org.grails:grails-datastore-core:2.0.0.BUILD-SNAPSHOT"
        compile "org.grails:grails-datastore-web:2.0.0.BUILD-SNAPSHOT"
        compile "org.grails:grails-datastore-gorm:2.0.0.BUILD-SNAPSHOT"
        compile "org.grails:grails-datastore-gorm-plugin-support:2.0.0.BUILD-SNAPSHOT"
        compile "org.grails:grails-datastore-gorm-hibernate:2.0.0.BUILD-SNAPSHOT"

//        compile "org.grails:grails-datastore-gorm-hibernate4:2.0.0.BUILD-SNAPSHOT"
        // These are specifically for mongo
        compile "org.grails:grails-datastore-gorm-mongo:1.3.0.BUILD-SNAPSHOT"
        compile "org.grails:grails-datastore-mongo:1.3.0.BUILD-SNAPSHOT"
        compile "org.grails:grails-datastore-gorm-neo4j:1.0.0.BUILD-SNAPSHOT"

        compile("org.codehaus.groovy.modules.http-builder:http-builder:0.5.2") {
            excludes "groovy"
        }

        compile 'net.sf.opencsv:opencsv:2.3'
        compile 'org.apache.poi:poi:3.7'
        compile('org.apache.poi:poi-ooxml:3.7') {
            transitive=false
        }
        compile 'mysql:mysql-connector-java:5.1.10'
        compile 'javax.activation:activation:1.1'
        compile 'commons-lang:commons-lang:2.2'
        compile 'commons-logging:commons-logging:1.1.1'
        compile 'com.sun.xml.bind:jaxb-impl:2.1.9'
        compile 'org.json:json:20080701'
        compile('org.codehaus.groovy:groovy-xmlrpc:0.7') {
            transitive = false
//            excludes "smack"
        }

//        compile('org.igniterealtime.smack:smack:3.1.0') {
//            transitive = false
//        }
        compile("org.springframework:spring-test:3.2.3.RELEASE") {
            transitive = false
        }

//        compile("org.springframework.security:spring-security-core:3.1.4.RELEASE")
//        compile("org.springframework.security:spring-security-web:3.1.4.RELEASE")
//        compile("org.springframework.security:spring-security-openid:3.1.4.RELEASE")

        compile 'org.apache.geronimo.specs:geronimo-stax-api_1.0_spec:1.0'
        compile 'com.google.code.facebookapi:facebook-java-api:3.0.2'
        compile 'com.google.code.facebookapi:facebook-java-api-schema:3.0.2'
        compile 'com.google.code.facebookapi:facebook-java-api-annotation-processor:3.0.2'
        compile 'org.jvnet.jaxb2_commons:runtime:0.4.1'
        compile ('org.springframework.security:spring-security-facebook:1.0.1') {
            //The spring-security-facebook was pulling in imcompatible spring-security dependencies
            transitive = false
        }







        // runtime 'mysql:mysql-connector-java:5.1.5'
    }
}
