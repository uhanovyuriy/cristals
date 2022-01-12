package ch.boogaga.crystals.service.security;

import ch.boogaga.crystals.model.Role;
import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.service.UserService;
import ch.boogaga.crystals.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class WebSocketAuthenticatorService {
    private static final Logger Log = LoggerFactory.getLogger(WebSocketAuthenticatorService.class.getName());
    private final UserService userService;

    public WebSocketAuthenticatorService(UserService userService) {
        this.userService = userService;
    }

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(String username, String password) throws AuthenticationException {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }

        final User user = userService.findByLogin(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        if (!username.equals(user.getLogin()) || !password.equals(user.getPassword())) {
            Log.error("Bad credentials user.");
            throw new BadCredentialsException("Bad credentials user.");
        }

        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER.getAuthority()))
        );
        token.eraseCredentials();
        return token;
    }
}
