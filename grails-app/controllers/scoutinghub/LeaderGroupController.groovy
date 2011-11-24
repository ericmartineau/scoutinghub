package scoutinghub

import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import grails.plugins.springsecurity.Secured
import org.hibernate.SessionFactory

@Secured(["ROLE_LEADER"])
class LeaderGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def searchableService
    SessionFactory sessionFactory

    TrainingService trainingService
    SpringSecurityService springSecurityService

    def index = {
        redirect(action: "create", params: params)
    }

    @Secured(["IS_AUTHENTICATED_ANONYMOUSLY"])
    def checkPositionAndUnit = {
        List.metaClass.joiner = {String lastSeparator ->
            if (delegate.size() == 1) {
                return String.valueOf(delegate[0])
            } else if (delegate.size() == 2) {
                return delegate.join(" ${lastSeparator} ");
            } else {
                int idxMinus1 = delegate.size() - 2
                String firstSet = delegate[0..idxMinus1].join(", ")
                return firstSet + " ${lastSeparator} " + delegate.last()
            }
        }

        def rtn = [:]
        if (params.position?.trim()) {
            ScoutGroup leaderGroup = ScoutGroup.get(params.id)
            LeaderPositionType type = LeaderPositionType.valueOf(params.position.trim())
            if (!leaderGroup.findApplicablePositionTypes()?.contains(type)) {
                rtn.mismatch = true
                String groupTypeLabel = message(code: leaderGroup.groupOrUnitName() + ".label")
                String positionLabel = message(code: type.name() + ".label")
                String article = positionLabel.article().firstLetterUpper()

                def applicableItemsList = []
                applicableItemsList.addAll(type.scoutGroupTypes.collect {it.name()})
                applicableItemsList.addAll(type.scoutUnitTypes.collect {it.name()})

                final applicableItemList = applicableItemsList.joiner("or")
                rtn.msgText = message(code: 'leaderGroup.mismatch', args: [groupTypeLabel, article + " " + positionLabel,
                        applicableItemList])
            }
        }

        render rtn as JSON
    }

    @Secured(["IS_AUTHENTICATED_ANONYMOUSLY"])
    def getApplicablePositions = {
        ScoutGroup leaderGroup = ScoutGroup.get(params.id)
        def rtnList = [:]
        leaderGroup.findApplicablePositionTypes().each {
            rtnList[it.name()] = it.name().humanize()
        };

        render rtnList as JSON
    }

    def performRemove = {
        //Check permission to remove from group
        LeaderGroup leaderGroup = LeaderGroup.get(params.id)
        def rtn = [:]
        try {
            InactiveLeaderGroup inactiveLeaderGroup = new InactiveLeaderGroup()
            inactiveLeaderGroup.scoutGroup = leaderGroup.scoutGroup
            inactiveLeaderGroup.leader = leaderGroup.leader
            inactiveLeaderGroup.createDate = new Date();
            inactiveLeaderGroup.save(failOnError: true)

            Leader leader = leaderGroup.leader
            leader.removeFromGroups(leaderGroup)
            leader.save(failOnError: true)

            leaderGroup.delete(failOnError: true)
            leader.reindex();


            rtn.success = true;
        } catch (Exception e) {
            log.error e
            rtn.success = false;
            flash.error = "Unable to remove from group"
            flash.error2 = e.message
        }
        render rtn as JSON
    }

    def confirmRemove = {
        LeaderGroup leaderGroup = LeaderGroup.get(params.id)
        return [leaderGroup: leaderGroup]
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [leaderGroupInstanceList: LeaderGroup.list(params), leaderGroupInstanceTotal: LeaderGroup.count()]
    }

    def create = {
        def leaderGroupInstance = new LeaderGroup()
        leaderGroupInstance.properties = params
        return [leaderGroupInstance: leaderGroupInstance]
    }

    def permissions = {
        Leader leader = Leader.get(params.id)
        return [leader: leader]
    }

    def rebuildTraining = {
        ScoutGroup scoutGroup = ScoutGroup.get(params.id)
        List<ScoutGroup> childGroups = ScoutGroup.findAllByLeftNodeGreaterThanAndRightNodeLessThan(scoutGroup.leftNode, scoutGroup.rightNode)
        int number = 0;
        childGroups.each {ScoutGroup processGroup->
            number++;

            processGroup.leaderGroups?.collect {it.leader}?.each{trainingService.recalculatePctTrained(it)}

            if(number % 20 == 0) {
                sessionFactory.currentSession.clear()
            }
        }

        render("Done: ${number}")
    }

    def savePermissions = {
        Leader leader = Leader.get(params.id)
        Leader currentUser = springSecurityService.currentUser
        params.each {

            if (it.key?.contains("_grp")) {

                int groupId = Integer.parseInt(it.key?.substring(it.key.indexOf("grp") + 3));
                boolean isAdmin = params["grp${groupId}"] == "on"
                if (groupId > 0) {

                    //Is the leader in the group?
                    final ScoutGroup scoutGroup = ScoutGroup.get(groupId)
                    if (scoutGroup.canBeAdministeredBy(currentUser)) {


                        LeaderGroup found = leader.groups.find {it.scoutGroup.id == groupId}


                        if (!found && isAdmin) {

                            found = new LeaderGroup(leader: leader, admin: isAdmin, leaderPosition: LeaderPositionType.Administrator, scoutGroup: scoutGroup);
                            leader.addToGroups(found)
                        } else if (found) {
                            found?.admin = isAdmin
                            found?.save(flush: true, failOnError: true)

                        }
                    }

                } else {
                    if (currentUser.hasRole("ROLE_ADMIN")) {
                        //Add role
                        if (!leader.hasRole("ROLE_ADMIN") && isAdmin) {
                            LeaderRole.create(leader, Role.findByAuthority("ROLE_ADMIN"), true)
                        } else if (!isAdmin) {
                            LeaderRole.remove(leader, Role.findByAuthority("ROLE_ADMIN"), true)
                        }

                    }

                }
            }
        }
        leader.save(failOnError: true)
        redirect(controller: "leader", action: "view", id: leader.id)
    }


    def save = {

        //Strange bug with searchable requires this goofy logic using merge
        def leaderGroupInstance = new LeaderGroup(params)
        def rtn = [:]

        LeaderGroup leaderGroupInstance2 = leaderGroupInstance.merge(flush: true)
        if (leaderGroupInstance2 && leaderGroupInstance2.save(flush: true)) {
            leaderGroupInstance2.leader.addToGroups(leaderGroupInstance2)
            leaderGroupInstance2.leader.save(failOnError: true)
            leaderGroupInstance2.leader.reindex()
            rtn.success = true
        } else {
            rtn.success = false;
            flash.errorObj = leaderGroupInstance
        }

        render rtn as JSON

    }

    def show = {
        def leaderGroupInstance = LeaderGroup.get(params.id)
        if (!leaderGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            [leaderGroupInstance: leaderGroupInstance]
        }
    }

    def edit = {
        def leaderGroupInstance = LeaderGroup.get(params.id)
        if (!leaderGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [leaderGroupInstance: leaderGroupInstance]
        }
    }

    def update = {
        def leaderGroupInstance = LeaderGroup.get(params.id)
        if (leaderGroupInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (leaderGroupInstance.version > version) {

                    leaderGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'leaderGroup.label', default: 'LeaderGroup')] as Object[], "Another user has updated this LeaderGroup while you were editing")
                    render(view: "edit", model: [leaderGroupInstance: leaderGroupInstance])
                    return
                }
            }
            leaderGroupInstance.properties = params
            if (!leaderGroupInstance.hasErrors() && leaderGroupInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), leaderGroupInstance.id])}"
                redirect(action: "show", id: leaderGroupInstance.id)
            }
            else {
                render(view: "edit", model: [leaderGroupInstance: leaderGroupInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def leaderGroupInstance = LeaderGroup.get(params.id)
        if (leaderGroupInstance) {
            try {
                leaderGroupInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'leaderGroup.label', default: 'LeaderGroup'), params.id])}"
            redirect(action: "list")
        }
    }
}
