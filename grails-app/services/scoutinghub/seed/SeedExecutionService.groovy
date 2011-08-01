package scoutinghub.seed

import scoutinghub.SeedScriptExecution
import scoutinghub.SeedScript
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 7/31/11
 * Time: 9:27 PM
 * To change this template use File | Settings | File Templates.
 */
class SeedExecutionService {

    @Autowired
    Collection<SeedScript> seedScripts;

    void executeSeedScripts() {
        seedScripts.sort {it.order}.each {
            //Has it already been executed?
            if (SeedScriptExecution.findAllByScriptNameAndCompleted(it.name, true)) {
                log.info "Script ${it.name} was completed"
            } else {
                SeedScriptExecution scriptExecution = new SeedScriptExecution()
                scriptExecution.scriptName = it.name
                scriptExecution.executionDate = new Date()

                String message
                try {
                    it.execute()

                    scriptExecution.completed = true

                } catch (Exception e) {
                    scriptExecution.completed = false
                    scriptExecution.message = e.message

                } finally {
                    scriptExecution.save(failOnError: true)

                }
            }
        }
    }
}
