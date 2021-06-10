package ch.boogaga.crystals.service.security;

import ch.boogaga.crystals.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static ch.boogaga.crystals.testdata.TestDataUser.USER_1;
import static ch.boogaga.crystals.testdata.TestDataUser.USER_1_TOKEN;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:db/schemaHsqlTestDb.sql", "classpath:db/populateHsqlTestDb.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
class WebSocketAuthenticatorServiceTest {

    @Autowired
    private WebSocketAuthenticatorService service;

    @Test
    void getAuthenticatedOrFail() {
        assertThrows(AuthenticationCredentialsNotFoundException.class,
                () -> service.getAuthenticatedOrFail("", USER_1.getPassword()));
        assertThrows(AuthenticationCredentialsNotFoundException.class,
                () -> service.getAuthenticatedOrFail(null, USER_1.getPassword()));
        assertThrows(AuthenticationCredentialsNotFoundException.class,
                () -> service.getAuthenticatedOrFail(USER_1.getLogin(), ""));
        assertThrows(AuthenticationCredentialsNotFoundException.class,
                () -> service.getAuthenticatedOrFail(USER_1.getLogin(), null));
        assertThrows(NotFoundException.class,
                () -> service.getAuthenticatedOrFail(USER_1.getName(), USER_1.getPassword()));
        UsernamePasswordAuthenticationToken actual =
                service.getAuthenticatedOrFail(USER_1.getLogin(), USER_1.getPassword());
        assertEquals(USER_1_TOKEN.getPrincipal(), actual.getPrincipal());
        assertIterableEquals(USER_1_TOKEN.getAuthorities(), actual.getAuthorities());
    }
}