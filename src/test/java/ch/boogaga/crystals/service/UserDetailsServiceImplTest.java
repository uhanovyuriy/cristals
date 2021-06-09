package ch.boogaga.crystals.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;

import static ch.boogaga.crystals.testdata.TestDataUser.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"SPRING_DATASOURCE_URL=jdbc:postgresql://192.168.10.151:5432/crystals"})
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl service;

    @Test
    void loadUserByUsername() {
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(""));
        assertEquals(USER_1_PRINCIPAL, service.loadUserByUsername(USER_1.getLogin()));
        assertNotEquals(USER_1_PRINCIPAL, service.loadUserByUsername(USER_2.getLogin()));
    }
}