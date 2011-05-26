package scoutinghub;

import org.codehaus.groovy.grails.plugins.springsecurity.AjaxAwareAuthenticationFailureHandler;
import org.codehaus.groovy.grails.plugins.springsecurity.ReflectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.facebook.FacebookAuthenticationToken;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The library I used didn't require facebook accounts to be mapped to local accounts.  This handler had to be added (I modeled
 * it after the openId one)
 *
 * User: eric
 * Date: 3/13/11
 * Time: 12:58 PM
 */
public class FacebookAuthenticationFailureHandler extends AjaxAwareAuthenticationFailureHandler {

    /**
     * Session key for the Open ID username/uri.
     */
    public static final String LAST_FACEBOOK_USERNAME = "LAST_OPENID_USERNAME";

    /**
     * Session key for the attributes that were returned.
     */
    public static final String LAST_FACEBOOK_ATTRIBUTES = "LAST_OPENID_ATTRIBUTES";

    /**
     * {@inheritDoc}
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {

        boolean createMissingUsers = Boolean.TRUE.equals(
                ReflectionUtils.getConfigProperty("openid.registration.autocreate"));

        if (!createMissingUsers || !isSuccessfulLoginUnknownUser(exception)) {
            super.onAuthenticationFailure(request, response, exception);
            return;
        }

        FacebookAuthenticationToken authentication = (FacebookAuthenticationToken) exception.getAuthentication();
        request.getSession().setAttribute(LAST_FACEBOOK_USERNAME, authentication.getUid());
        request.getSession().setAttribute(LAST_FACEBOOK_ATTRIBUTES, new ArrayList());

        String createAccountUri = (String) ReflectionUtils.getConfigProperty("openid.registration.createAccountUri");
        getRedirectStrategy().sendRedirect(request, response, createAccountUri);
    }

    private boolean isSuccessfulLoginUnknownUser(AuthenticationException exception) {
        if (!(exception instanceof UsernameNotFoundException)) {
            return false;
        }

        Authentication authentication = exception.getAuthentication();
        if (!(authentication instanceof FacebookAuthenticationToken)) {
            return false;
        }

        FacebookAuthenticationToken castToken = (FacebookAuthenticationToken) authentication;
        return castToken.isAuthenticated();
    }

    private List<OpenIDAttribute> extractAttrsWithValues(final OpenIDAuthenticationToken authentication) {
        List<OpenIDAttribute> attributes = new ArrayList<OpenIDAttribute>();
        for (OpenIDAttribute attr : authentication.getAttributes()) {
            if (attr.getValues() == null || attr.getValues().isEmpty()) {
                continue;
            }
            if (attr.getValues().size() == 1 && attr.getValues().get(0) == null) {
                continue;
            }
            attributes.add(attr);
        }
        return attributes;
    }
}
