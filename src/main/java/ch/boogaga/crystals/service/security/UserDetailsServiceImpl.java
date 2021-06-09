package ch.boogaga.crystals.service.security;

import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.model.UserPrincipalImpl;
import ch.boogaga.crystals.repository.UserCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserCrudRepository userRepository;

    public UserDetailsServiceImpl(UserCrudRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with login:" + login);
        }
        return new UserPrincipalImpl(user);
    }
}
