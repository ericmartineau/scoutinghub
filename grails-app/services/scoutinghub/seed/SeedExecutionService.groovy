package scoutinghub.seed

import scoutinghub.SeedScriptExecution
import scoutinghub.SeedScript
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.ExecutorService
import java.util.concurrent.Callable

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

    ExecutorService executorService

    def searchableService



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
                Exception error = null
                try {
                    it.execute()

                    scriptExecution.completed = true

                } catch (Exception e) {
                    log.error "Error running seed script ${it.name}", e
                    scriptExecution.completed = false
                    scriptExecution.message = e.message
                    error = e
                } finally {
                    log.info "Ran ${it.name} successfully"
                    scriptExecution.save(failOnError: true)
                    if(error) {
                        throw error
                    }

                }
            }
        }

        executorService.submit({
            searchableService.index()
        } as Callable)
    }
}
