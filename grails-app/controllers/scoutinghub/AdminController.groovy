package scoutinghub

import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.elasticsearch.search.SearchService
import org.grails.plugins.elasticsearch.ElasticSearchService

/**
 * User: ericm
 * Date: 7/21/13
 * Time: 9:23 PM
 */
@Secured("ROLE_ADMIN")
class AdminController {

    GrailsApplication grailsApplication
    ElasticSearchService elasticSearchService

    def reindex(String clazz) {
        if(clazz) {
            Class cls = grailsApplication.getClassForName(clazz)
            elasticSearchService.index(cls)
        } else {
            elasticSearchService.index()
        }

        render("Started")
    }
}
