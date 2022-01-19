package ch.boogaga.crystals.service;

import ch.boogaga.crystals.model.persist.User;
import ch.boogaga.crystals.testdata.TestDataUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:db/schemaTestDb.sql", "classpath:db/populateTestDb.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    void findByLoginWithCaching() {
        assertThrows(UsernameNotFoundException.class, () -> service.findByLoginWithCaching(""));
        User expected = service.findByLoginWithCaching(TestDataUser.USER_1.getLogin());
        assertEquals(expected, TestDataUser.USER_1);
        User expected2 = service.findByLoginWithCaching(TestDataUser.USER_1.getLogin());
        assertNotEquals(expected2, TestDataUser.USER_2);
    }

    @Test
    void findByLogin() {
        assertThrows(UsernameNotFoundException.class, () -> service.findByLogin(""));
        User expected = service.findByLogin(TestDataUser.USER_1.getLogin());
        assertEquals(expected, TestDataUser.USER_1);
        User expected2 = service.findByLogin(TestDataUser.USER_1.getLogin());
        assertNotEquals(expected2, TestDataUser.USER_2);
    }
}