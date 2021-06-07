package ch.boogaga.crystals.service;

import ch.boogaga.crystals.model.Role;
import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.repository.UserCrudRepository;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class WebSocketAuthenticatorService {
    private final UserCrudRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public WebSocketAuthenticatorService(UserCrudRepository userRepository,
                                         AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(String username, String password) throws AuthenticationException {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }
        final User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("User not found");
        }
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER.getAuthority()))
        );
//        authenticationManager.authenticate(token);
        if (!username.equals(user.getLogin())) {
            throw new BadCredentialsException("Bad credentials user.");
        }
        token.eraseCredentials();
        return token;
    }
}
