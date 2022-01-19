package ch.boogaga.crystals.service.security;

import ch.boogaga.crystals.configuration.WebSecurityConfig;
import ch.boogaga.crystals.model.Role;
import ch.boogaga.crystals.model.persist.User;
import ch.boogaga.crystals.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class WebSocketAuthenticatorService {
    private static final Logger Log = LoggerFactory.getLogger(WebSocketAuthenticatorService.class.getName());
    private final UserService userService;
    private final WebSecurityConfig webSecurityConfig;

    public WebSocketAuthenticatorService(UserService userService, WebSecurityConfig webSocketConfig) {
        this.userService = userService;
        this.webSecurityConfig = webSocketConfig;
    }

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }

        final User user = userService.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!username.equals(user.getLogin())) {
            Log.error("Bad credentials user.");
            throw new BadCredentialsException("Bad credentials user.");
        }

        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER.getAuthority()))
        );

//        try {
//            webSecurityConfig.authenticationManagerBean().authenticate(token);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        token.eraseCredentials();
        return token;
    }
}
