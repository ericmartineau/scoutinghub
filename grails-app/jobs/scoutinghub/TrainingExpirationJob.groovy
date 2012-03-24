package scoutinghub


class TrainingExpirationJob {

    TrainingService trainingService

    static triggers = {
        simple name:'trainingExpirationCron', startDelay:5000, repeatInterval: 86400000, repeatCount: -1
    }

    def concurrent = false

    def execute() {
        Set<Long> numProcessed = trainingService.processExpiredTrainings();
        println "Processed expiration job in quartz: ${numProcessed?.size() ?: "0"}"
    }
}
