package scoutcert

import grails.converters.JSON
import org.compass.core.engine.SearchEngineQueryParseException
import grails.plugins.springsecurity.Secured

class ScoutGroupController {

    ScoutGroupService scoutGroupService

    def show = {
        redirect(controller:"training", action:"trainingReport", id:params.id)
    }

    @Secured(['ROLE_ADMIN'])
    def makeAdmin = {
        LeaderGroup leaderGroup = LeaderGroup.get(params.id)
        leaderGroup.admin = true
        leaderGroup.save(failOfError:true)

        redirect(controller:"leader", view:"view", id:leaderGroup.leader.id)
    }

    def reindex = {
        scoutGroupService.reindex()
        render("Done")

    }

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

    def selectUnit = {
        if (!params.unitId) {
            return [parents: ScoutGroup.findAllByParentIsNull()]
        } else {
            ScoutGroup parent = ScoutGroup.get(Integer.parseInt(params.unitId))
            return [parents: ScoutGroup.findAllByParent(parent)]

        }
    }

    def selectUnitTree = {
        def rtn = []
        if (Integer.parseInt(params?.id ?: "0") == 0) {
            def parentest = ScoutGroup.findAllByParentIsNull()
            parentest.each { ScoutGroup group ->
                def childData = [:]
                childData << [data: "${group.groupLabel} - ${group.unitType ?: group?.groupType}"]
                childData << [attr: [id: "${group.id}"]]
                if(group?.childGroups) {
                    childData.children = []
                    childData.state = "open"

                    group?.childGroups?.each {ScoutGroup child->
                        def subData = [:]
                        subData << [data: "${child.groupLabel} - ${child.unitType ?: child?.groupType}"]
                        subData << [attr: [id: "${child.id}"]]

                        if(group?.childGroups) {
                            subData << [state:"closed"]
                        }
                        childData.children << subData
                    }
                }
                rtn << childData
            }

        } else {
            ScoutGroup parent = ScoutGroup.get(Integer.parseInt(params.id))

            ScoutGroup.findAllByParent(parent)?.each { ScoutGroup group ->
                def childData = [:]
                childData << [data: "${group.groupLabel} - ${group.unitType ?: group?.groupType}"]
                childData << [attr: [id: "${group.id}"]]
                if(group?.childGroups) {
                    childData << [state: "closed"]
                }
                rtn << childData
            }

        }
        render rtn as JSON

    }
}
