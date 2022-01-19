package ch.boogaga.crystals.testdata;

import ch.boogaga.crystals.model.Role;
import ch.boogaga.crystals.model.persist.User;
import ch.boogaga.crystals.model.UserPrincipalImpl;
import ch.boogaga.crystals.to.UserTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collections;

public class TestDataUser {
    public static final User USER_1 = new User(100000, "test", "test@mail.ru",
            "password1", "test@mail.ru",
            LocalDateTime.of(2021, 6, 8, 12, 30),
            LocalDateTime.of(2021, 6, 8, 13, 0), true, 0);
    public static final User USER_2 = new User(100001, "test2", "test2@mail.ru",
            "password2", "test2@mail.ru",
            LocalDateTime.of(2021, 6, 9, 0, 0),
            LocalDateTime.of(2021, 6, 9, 0, 0), true, 10);

    public static final UserTo USER_TO =
            new UserTo(100000, "test", "test@mail.ru", "test@mail.ru", "password1");

    public static final UsernamePasswordAuthenticationToken USER_1_TOKEN =
            new UsernamePasswordAuthenticationToken(USER_1.getLogin(), USER_1.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER.getAuthority())));

    public static final UserPrincipalImpl USER_1_PRINCIPAL = new UserPrincipalImpl(USER_1);
}
