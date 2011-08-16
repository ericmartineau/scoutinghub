package scoutinghub.seed

import scoutinghub.SeedScript
import javax.sql.DataSource
import groovy.sql.Sql

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/15/11
 * Time: 11:15 PM
 */
class ClearAllEmailsService implements SeedScript {
    DataSource dataSource

    int getOrder() {
        return 13
    }

    void execute() {
        new Sql(dataSource).execute("UPDATE leader set email=null")
    }

    String getName() {
        return "clearAllEmailsService"
    }

}
