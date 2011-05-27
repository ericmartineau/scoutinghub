package scoutinghub

class RecordSavingService {

    static transactional = false

    def searchableService

    public void op(def obj, Closure cl) {

        def id = obj?.id
        def rtn
        searchableService.stopMirroring()
        try {
            rtn = obj.merge();
            rtn = cl(rtn);
        } finally {
            searchableService.startMirroring()
        }

        if(rtn) {
            if(id == null || id==0)
            rtn.index();
        } else {
            rtn.reindex();
        }
    }

}
