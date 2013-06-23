import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import scoutinghub.ScoutGroupService
import scoutinghub.seed.SeedExecutionService

import java.util.regex.Matcher
import java.util.regex.Pattern

class BootStrap {

    ScoutGroupService scoutGroupService
    SpringSecurityService springSecurityService
    SeedExecutionService seedExecutionService

    def init = { servletContext ->
        SpringSecurityUtils.clientRegisterFilter 'facebookAuthenticationFilter',
                SecurityFilterPosition.OPENID_FILTER.order - 10

        seedExecutionService.executeSeedScripts()

        registerStringMetaclass()

    }

    void registerStringMetaclass() {

        String.metaClass.article = {
            String val = delegate.toString()?.toLowerCase();
            if (["a", "e", "i", "o", "u"].find {
                val.startsWith(it)
            }) {
                return "an"
            } else {
                return "a"
            }
        }

        String.metaClass.firstLetterUpper = {
            String val = delegate.toString();
            String rtn
            if (val) {
                if (val.length() == 1) {
                    rtn = val.toUpperCase()
                } else {
                    rtn = val.charAt(0).toString().toUpperCase() + val.substring(1)
                }
            }
            return rtn
        }

        List.metaClass.joiner = {String lastSeparator ->
            if (delegate.size() == 1) {
                return String.valueOf(delegate[0])
            } else {
                int idxMinus1 = delegate.size() - 2
                String firstSet = delegate.getAt[0..idxMinus1].join(", ")
                return firstSet + " ${lastSeparator} " + delegate.last()
            }
        }

        String.metaClass.trimTo = {int i ->
            String val = delegate.toString()
            return val.substring(0, Math.min(i, val.length()))
        }
        String.metaClass.humanize = {
            String word = delegate.toString()
            Pattern pattern = Pattern.compile("([A-Z]|[a-z])[a-z]*");

            List<String> tokens = new ArrayList<String>();
            Matcher matcher = pattern.matcher(word);
            String acronym = "";
            while (matcher.find()) {
                String found = matcher.group();
                if (found.matches("^[A-Z]\$")) {
                    acronym += found;
                } else {
                    if (acronym.length() > 0) {
                        //we have an acronym to add before we continue
                        tokens.add(acronym);
                        acronym = "";
                    }
                    tokens.add(found);
                }
            }
            if (acronym.length() > 0) {
                tokens.add(acronym);
            }

            if (tokens.size() > 0) {
                word = ""
                for (String s: tokens) {
                    word += " " + s;
                }

            }

            return word
        }

    }

    def destroy = {
    }


}
