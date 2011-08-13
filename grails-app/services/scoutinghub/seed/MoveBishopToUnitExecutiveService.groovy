package scoutinghub.seed

import scoutinghub.SeedScript
import javax.sql.DataSource
import groovy.sql.Sql

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 8/12/11
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
class MoveBishopToUnitExecutiveService implements SeedScript {

    DataSource dataSource

    int getOrder() {
        return 10
    }

    void execute() {
        def sql = new Sql(dataSource)
        sql.execute("UPDATE program_certification set position_type='Bishop' where position_type='Unit Executive'");
        sql.execute("UPDATE leader_group set leader_position='Bishop' where leader_position='Unit Executive'");

        sql.execute("UPDATE program_certification set position_type='UnitExecutive' where position_type='Bishop'");
        sql.execute("UPDATE leader_group set leader_position='UnitExecutive' where leader_position='Bishop'");
    }

    String getName() {
        return "renameBishopToUnitExecutive"
    }

}
