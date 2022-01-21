package ch.boogaga.crystals.testdata;

import ch.boogaga.crystals.model.Role;
import ch.boogaga.crystals.model.persist.Subscription;
import ch.boogaga.crystals.model.persist.User;
import ch.boogaga.crystals.model.UserPrincipalImpl;
import ch.boogaga.crystals.to.UserTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collections;

public class TestDataUser {
    public static final int USER_ID_1 = 1;
    public static final int USER_ID_2 = 2;
    public static final int USER_ID_3 = 3;
    public static final User USER_1 = new User(USER_ID_1, "test", "test@mail.ru",
            "password1", "test@mail.ru",
            LocalDateTime.of(2021, 6, 8, 12, 30),
            LocalDateTime.of(2021, 6, 8, 13, 0), true, 0,
            Collections.singletonList(new Subscription(100000, 100001)));
    public static final User USER_2 = new User(USER_ID_2, "test2", "test2@mail.ru",
            "password2", "test2@mail.ru",
            LocalDateTime.of(2021, 6, 9, 0, 0),
            LocalDateTime.of(2021, 6, 9, 0, 0), true, 10,
            Collections.singletonList(new Subscription(100001, 100000)));
    public static final User USER_3 = new User(USER_ID_3, "test3", "test3@mail.ru",
            "password3", "test3@mail.ru",
            LocalDateTime.of(2021, 6, 10, 0, 0),
            LocalDateTime.of(2021, 6, 10, 0, 0), true, 101,
            Collections.singletonList(new Subscription(100002, 100000)));

    public static final UserTo USER_TO =
            new UserTo(USER_ID_1, "test", "test@mail.ru", "test@mail.ru", "password1");

    public static final UsernamePasswordAuthenticationToken USER_1_TOKEN =
            new UsernamePasswordAuthenticationToken(USER_1.getLogin(), USER_1.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER.getAuthority())));

    public static final UserPrincipalImpl USER_1_PRINCIPAL = new UserPrincipalImpl(USER_1);
}
