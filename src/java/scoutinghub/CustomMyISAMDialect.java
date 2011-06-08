package scoutinghub;

import org.hibernate.dialect.MySQLMyISAMDialect;

import java.sql.Types;


/**
 * User: eric
 * Date: 6/7/11
 * Time: 11:27 PM
 */
public class CustomMyISAMDialect extends MySQLMyISAMDialect {
    public CustomMyISAMDialect() {
        registerColumnType(Types.BIT, "tinyint");
    }
}
