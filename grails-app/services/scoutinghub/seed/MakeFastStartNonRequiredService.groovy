package scoutinghub.seed

import scoutinghub.SeedScript
import javax.sql.DataSource
import groovy.sql.Sql

/**
 * User: ericm
 * Date: 8/27/11
 * Time: 10:40 PM
 */
class MakeFastStartNonRequiredService implements SeedScript{

    DataSource dataSource

    int getOrder() {
        return 13
    }

    void execute() {
        Sql sql = new Sql(dataSource)
        sql.execute("""

UPDATE program_certification
inner join certification on certification_id=certification.id
set required=0
where certification.certification_type="FastStart";

"""
        )
    }

    String getName() {
        return "makeFastStartNonRequired"
    }

}
