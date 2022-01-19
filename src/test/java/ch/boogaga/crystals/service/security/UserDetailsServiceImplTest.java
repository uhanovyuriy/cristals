package ch.boogaga.crystals.service.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static ch.boogaga.crystals.testdata.TestDataUser.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:db/schemaTestDb.sql", "classpath:db/populateTestDb.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl service;

    @Test
    void loadUserByUsername() {
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(""));
        final UserDetails actual_1 = service.loadUserByUsername(USER_1.getLogin());
        assertEquals(USER_1_PRINCIPAL, actual_1);
        final UserDetails actual_2 = service.loadUserByUsername(USER_2.getLogin());
        assertNotEquals(USER_1_PRINCIPAL, actual_2);
    }
}