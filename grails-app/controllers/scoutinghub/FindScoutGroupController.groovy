package scoutinghub

import grails.converters.JSON

class FindScoutGroupController {

    def findUnits = {

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
            def results = ScoutGroup.search{
                must {
                    ["groupLabel", "groupIdentifier", "groupType", "unitType"].each{
                        queryString(searchTerm + "*", [useAndDefaultOperator: true, defaultSearchProperty: it])
                    }
                }
                if(params.position?.trim()) {

                    final LeaderPositionType lpType = LeaderPositionType.valueOf(params.position)

                    //Find all group types and unit types that match
                    must {
                        lpType.scoutGroupTypes.each {
                            term('groupType', it.name().toLowerCase())
                        }
                        lpType.scoutUnitTypes.each {
                            term('unitType', it.name().toLowerCase())
                        }
                    }
                }

            }


            //Had some weird issues with stale objects - this should force a refresh
            results.results.each{it.refresh()}


            //Return the results formatted for json
            final List collectedResults = results.results.collect {ScoutGroup grp ->
                return [key: "${grp?.id}", label: "${grp?.groupLabel} (${grp?.unitType ?: grp.groupType})"]
            }
            if(collectedResults?.size() == 0) {
                collectedResults.add([key: "", label: "No results found "])
            }
            render collectedResults as JSON
        } catch (Exception ex) {
            return [parseException: true]
        }

    }
}
