package org.springframework.security.facebook;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.memory.InMemoryDaoImpl;

import java.util.ArrayList;

/**
 * User: eric
 * Date: 6/4/11
 * Time: 10:43 PM
 */
public class MappedFacebookAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        FacebookAuthenticationToken facebookAuthentication = (FacebookAuthenticationToken) authentication;

        if (facebookAuthentication.getUid() == null) {
            throw new BadCredentialsException("User not authenticated through facebook");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(facebookAuthentication.getUid()));

        FacebookAuthenticationToken succeedToken = new FacebookAuthenticationToken(
                facebookAuthentication.getUid(), new ArrayList<GrantedAuthority>(userDetails.getAuthorities()));
        succeedToken.setDetails(authentication.getDetails());

        return succeedToken;
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        boolean supports = FacebookAuthenticationToken.class
                .isAssignableFrom(authentication);
        return supports;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


}
