package scoutinghub

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 7/31/11
 * Time: 8:40 PM
 * To change this template use File | Settings | File Templates.
 */
class SeedScriptExecution {
    boolean completed
    String scriptName
    Date executionDate
    String message

    static constraints = {
        message(nullable:true)
    }
}
