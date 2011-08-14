package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.CertificationCode
import scoutinghub.Certification

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/13/11
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
class UpdateOnlineTrainingUrlScriptService implements SeedScript {
    int getOrder() {
        return 12
    }

    void execute() {
        def onlineTrainingCodes = ["A01", //This is Scouting
                "C40", //Cubmaster Specific Training
                "C41", //Tiger Leader Specific Training
                "C42", //Den Leader Specific Training
                "C60", //Committee Specific Training
                "C61", //Webelos Leader Specific Training
                "C62", //Pack Trainer Specific Training
                "C70", //Den Leader Fast Start
                "C71", //Webelos Den Leader Fast Start
                "C72", //Cubmaster Fast Start
                "C73", //Tiger Cub Leader Fast Start
                "CF1", //Den Leader Fast Start
                "CF2", //Webelos Den Leader Fast Start
                "CF3", //Cubmaster Fast Start
                "CF4", //Pack Committee Fast Start
                "CF6", //Tiger Cub Leader Fast Start
                "CFS", //ScoutParents Unit Coordinator Fast Start
                "D62", //Chartered Org Rep Fast Start
                "PFS", //Venturing Crew Leader Fast Start
                "S74", //Climb On Safely
                "SA", //Safety Afloat
                "SFS", //Boy Scouting or Varsity Scout Leader Fast Start
                "SPW", //Physical Wellness
                "SSD", //Safe Swim Defense
                "WA01", //This is Scouting
                "WCF4", //Pack Committee Fast Start
                "WCFS", //ScoutParents Unit Coordinator Fast Start
                "WPFS", //Venturing Crew Leader Fast Start
                "WS81", //Weather Hazards
                "WSFS", //Boy Scouting or Varsity Scout Leader Fast Start
                "Y01", //Youth Protection Training
                "Y02"] //Venturing Youth Protection Training
        onlineTrainingCodes.each {
            Certification certification = CertificationCode.findByCode(it).certification
            certification.trainingUrl = "https://myscouting.scouting.org/Pages/eLearning.aspx"
            certification.save(failOnError:true)
        }
    }

    String getName() {
        return "updateOnlineTrainingUrl"
    }

}
