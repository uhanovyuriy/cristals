package ch.boogaga.crystals.service.security;

import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.model.UserPrincipalImpl;
import ch.boogaga.crystals.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User user = userService.findByLogin(login);
        return new UserPrincipalImpl(user);
    }
}
