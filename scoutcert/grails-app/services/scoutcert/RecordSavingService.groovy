package scoutcert

class RecordSavingService {

    static transactional = false

    def searchableService

    public void op(def obj, Closure cl) {

        def rtn
        searchableService.stopMirroring()
        try {

            rtn = obj.merge();
            rtn = cl(rtn);
        } finally {
            searchableService.startMirroring()
        }

        if(rtn) {
            rtn.reindex();
        }
    }

}
