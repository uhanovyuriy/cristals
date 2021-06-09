package ch.boogaga.crystals.testdata;

import ch.boogaga.crystals.model.Role;
import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.model.UserPrincipalImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collections;

public class TestDataUser {
    public static final User USER_1 = new User(1, "test", "test@mail.ru",
            "$2a$10$F7bZXdBwZC25MZjNGP97rOvwRs59XzQfprE164kFfI3Y5rrg6Ssuy", "test@mail.ru",
            LocalDateTime.of(2021, 6, 8, 12, 30),
            LocalDateTime.of(2021, 6, 8, 13, 0), true, 100,
            50, 10, 499, 500, 10, 5);
    public static final User USER_2 = new User(2, "test2", "test2@mail.ru",
            "password", "test2@mail.ru",
            LocalDateTime.of(2021, 6, 9, 0, 0),
            LocalDateTime.of(2021, 6, 9, 0, 0), true, 10,
            5, 2, 280, 300, 5, 2);
    public static final UsernamePasswordAuthenticationToken USER_1_TOKEN =
            new UsernamePasswordAuthenticationToken(USER_1.getLogin(), USER_1.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER.getAuthority())));
    public static final UserPrincipalImpl USER_1_PRINCIPAL = new UserPrincipalImpl(USER_1);
}
