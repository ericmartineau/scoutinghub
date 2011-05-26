package scoutinghub


class TrainingExpirationJob {

    TrainingService trainingService

    def timeout = 0001000 //Once per day

    def execute() {
//        trainingService.processExpiredTrainings();
    }
}
