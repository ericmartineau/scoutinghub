package scoutinghub

import grails.converters.JSON
import org.compass.core.engine.SearchEngineQueryParseException

class FindScoutGroupController {

    def findUnits = {

        if (!params.term?.trim()) {
            rtn = []
            render rtn as JSON
            return
        }
        try {
            def results = ScoutGroup.search(params.term.trim() + "*", defaultOperator:"or", properties:
                ["groupLabel", "parent_groupLabel", "groupIdentifier", "parent_groupIdentifier", "groupType", "unitType"])

            //Had some weird issues with stale objects - this should force a refresh
            results.results.each{it.refresh()}

            //Return the results formatted for json
            render results.results.collect {ScoutGroup grp ->
                return [key: "${grp?.id}", label: "${grp?.groupLabel} (${grp?.unitType ?: grp.groupType})"]

            } as JSON
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }

    }
}
