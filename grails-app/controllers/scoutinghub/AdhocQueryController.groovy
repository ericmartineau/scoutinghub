package scoutinghub

import grails.plugins.springsecurity.Secured
import au.com.bytecode.opencsv.CSVWriter

@Secured(["ROLE_ADMIN"])
class AdhocQueryController {

    def sessionFactory
    def index = { }
    def show = {}
    def runQuery = {
        System.out.println("Test " + params.sqlQuery);

        def session = sessionFactory.getCurrentSession()
        def result = session.createSQLQuery(params.sqlQuery).list()
        System.out.println("Results " + result);

        List<String[]> friendlyResult = new ArrayList<String>()

        for(row in result) {
            friendlyResult.add(convertObjectArrayToStringArray((Object[])row))
        }

        def sw = new StringWriter()
        def csvWriter = new CSVWriter(sw)
        csvWriter.writeAll(friendlyResult)

        response.setHeader("Content-disposition", "attachment; filename=results.csv")
        response.contentType = "application/vnd.ms-excel"
        def outs = response.outputStream
        outs << sw.toString()
        outs.flush()
        outs.close()

    }

    String[] convertObjectArrayToStringArray(Object[] foo) {
        String[] retVal = new String[foo.length];
        for(int i=0;i<foo.length;i++) {
            retVal[i]=foo[i].toString()
        }
        return retVal;
    }
}
