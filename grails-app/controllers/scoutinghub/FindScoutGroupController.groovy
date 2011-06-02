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
            def results = ScoutGroup.search(params.term.trim() + "*", params)
            render results.results.collect {ScoutGroup grp ->
                return [key: "${grp?.id}", label: "${grp?.groupLabel} (${grp?.unitType ?: grp.groupType})"]

            } as JSON
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }

    }
}
