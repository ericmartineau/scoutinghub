package scoutinghub.infusionsoft

import scoutinghub.ScoutGroup

class InfusionsoftFollowUpInfo {

    static transients = ['scoutGroup']

    Integer scoutGroupId
    int invitationFollowUpSequenceId
    int registrationFollowUpSequenceId

    ScoutGroup getScoutGroup() {
        return ScoutGroup.get(scoutGroupId)
    }

    void setScoutGroup(ScoutGroup scoutGroup) {
        this.scoutGroupId = scoutGroup?.id
    }

}
