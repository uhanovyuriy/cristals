package ch.boogaga.crystals.service;

import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.repository.UserCrudRepository;
import ch.boogaga.crystals.testdata.TestDataUser;
import ch.boogaga.crystals.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:db/schemaHsqlTestDb.sql", "classpath:db/populateHsqlTestDb.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    void findByLogin() {
        assertThrows(NotFoundException.class, () -> service.findByLogin(""));
        User expected = service.findByLogin(TestDataUser.USER_1.getLogin());
        assertEquals(expected, TestDataUser.USER_1);
        assertNotEquals(expected, TestDataUser.USER_2);
    }
}