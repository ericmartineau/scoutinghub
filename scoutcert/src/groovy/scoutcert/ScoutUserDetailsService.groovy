package scoutcert

import org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdUserDetailsService
import org.springframework.security.core.userdetails.UserDetails

/**
 * User details service that allows a social userid to be passed and maps to an actual leader record.
 * User: eric
 * Date: 3/12/11
 * Time: 5:43 PM
 */
class ScoutUserDetailsService extends OpenIdUserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username, boolean loadRoles) {

        def c = Leader.createCriteria()
        def foundMapping = c.get {
            openIds {
                eq("url", username)
            }
        }
        if(foundMapping) {
            username = foundMapping.username ?: username;
        }
        //Look up in openID mapping table
        return super.loadUserByUsername(username, loadRoles)
    }

}
