package ch.boogaga.crystals.service.security;

import ch.boogaga.crystals.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static ch.boogaga.crystals.testdata.TestDataUser.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl service;

    @Test
    void loadUserByUsername() {
        assertThrows(NotFoundException.class, () -> service.loadUserByUsername(""));
        assertEquals(USER_1_PRINCIPAL, service.loadUserByUsername(USER_1.getLogin()));
        assertNotEquals(USER_1_PRINCIPAL, service.loadUserByUsername(USER_2.getLogin()));
    }
}