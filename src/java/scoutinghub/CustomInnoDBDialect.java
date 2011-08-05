package scoutinghub;

import org.hibernate.dialect.MySQLInnoDBDialect;
import org.hibernate.dialect.MySQLMyISAMDialect;

import java.sql.Types;


/**
 * User: eric
 * Date: 6/7/11
 * Time: 11:27 PM
 */
public class CustomInnoDBDialect extends MySQLInnoDBDialect {
    public CustomInnoDBDialect() {
        registerColumnType(Types.BIT, "tinyint");
    }
}
