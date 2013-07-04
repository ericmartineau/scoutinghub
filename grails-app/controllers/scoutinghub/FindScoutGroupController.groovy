package scoutinghub

import grails.converters.JSON
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.QueryStringQueryBuilder

import static org.elasticsearch.index.query.QueryBuilders.boolQuery
import static org.elasticsearch.index.query.QueryBuilders.queryString
import static org.elasticsearch.index.query.QueryBuilders.termQuery

class FindScoutGroupController {

    def findUnits(String position) {
        def rtn
        final searchTerm = params.term?.trim()
        if (!searchTerm) {
            rtn = []
            render rtn as JSON
            return
        }
        try {

            //We need to build a compass query.  The query will use the value they've typed in,
            //plus will use the currently selected value of "position" to make sure that the
            //scoutGroup they select is appropriate for the position they've selected
            def topLevelQuery = boolQuery()
            def query = searchTerm?.trim()?.split(" ")?.findAll { it.trim() }?.collect { "$it*" }?.join(" AND ")
            QueryStringQueryBuilder queryStringBuilder = queryString(query)
            ["groupLabel", "groupIdentifier", "groupType", "unitType"].each{queryStringBuilder.field(it)}
            topLevelQuery.must(queryStringBuilder)
            if (position?.trim()) {

                final LeaderPositionType lpType = LeaderPositionType.valueOf(params.position)

                //Find all group types and unit types that match

                BoolQueryBuilder types = boolQuery()

                lpType.scoutGroupTypes.each { type ->
                    types.should(termQuery("groupType", type.name().toLowerCase()))
                }

                lpType.scoutUnitTypes.each { type ->
                    types.should(termQuery("unitType", type.name().toLowerCase()))
                }

                types.minimumShouldMatch("1")
                if(types.hasClauses()) {
                    topLevelQuery.must(types)
                }
            }

            def results = ScoutGroup.search(topLevelQuery)

            //Had some weird issues with stale objects - this should force a refresh
            results.searchResults.each { it.refresh() }

            //Return the results formatted for json
            final List collectedResults = results.searchResults.collect { ScoutGroup grp ->
                return [key: "${grp?.id}", label: "${grp?.groupLabel} (${grp?.unitType ?: grp.groupType})"]
            }
            if (collectedResults?.size() == 0) {
                collectedResults.add([key: "", label: "No results found "])
            }
            render collectedResults as JSON
        } catch (Exception ex) {
            log.error(ex.message, ex)
            render([parseException: true] as JSON)
        }

    }
}
