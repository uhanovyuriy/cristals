package ch.boogaga.crystals.service.chat;

import ch.boogaga.crystals.model.persist.ChatMessagePrivate;
import ch.boogaga.crystals.model.persist.ChatMessagePublic;
import ch.boogaga.crystals.repository.chat.ChatPrivateRepository;
import ch.boogaga.crystals.repository.chat.ChatPublicRepository;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static ch.boogaga.crystals.ConfigData.*;
import static ch.boogaga.crystals.testdata.TestDataChat.*;
import static ch.boogaga.crystals.testdata.TestDataUser.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = {"classpath:db/schemaTestDataChatDb.sql", "classpath:db/populateTestDataChatDb.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ChatRoomServiceTest {

    @Autowired
    ChatRoomService service;

    @Autowired
    ChatPrivateRepository privateRepository;

    @Autowired
    ChatPublicRepository publicRepository;

    @Autowired
    @Qualifier("hazelcastInstance")
    HazelcastInstance hazelcastInstance;

    @Test
    void initRooms() {
        service.initRooms();
        Map<Integer, ChatMessagePrivate> privateMap = hazelcastInstance.getMap(CACHE_PRIVATE_ROOM_NAME);
        assertEquals(PRIVATE_MESSAGE_1, privateMap.get(PRIVATE_MESSAGE_1_ID));
        assertEquals(PRIVATE_MESSAGE_2, privateMap.get(PRIVATE_MESSAGE_2_ID));
        assertNotEquals(PRIVATE_MESSAGE_1, privateMap.get(PRIVATE_MESSAGE_3_ID));
        assertNull(privateMap.get(PRIVATE_MESSAGE_4_ID));

        Map<Integer, ChatMessagePublic> publicRuMap = hazelcastInstance.getMap(CACHE_PUBLIC_ROOM_RU_NAME);
        assertEquals(PUBLIC_MESSAGE_RU, publicRuMap.get(PUBLIC_MESSAGE_RU_ID));
        assertNotEquals(PUBLIC_MESSAGE_EN, publicRuMap.get(PUBLIC_MESSAGE_RU_ID));
        assertNull(publicRuMap.get(PUBLIC_MESSAGE_EN_ID));

        Map<Integer, ChatMessagePublic> publicEnMap = hazelcastInstance.getMap(CACHE_PUBLIC_ROOM_EN_NAME);
        assertEquals(PUBLIC_MESSAGE_EN, publicEnMap.get(PUBLIC_MESSAGE_EN_ID));
        assertNotEquals(PUBLIC_MESSAGE_RU, publicEnMap.get(PUBLIC_MESSAGE_EN_ID));
        assertNull(publicEnMap.get(PUBLIC_MESSAGE_RU_ID));
    }

    @Test
    @Transactional
    void savePrivate() {
        service.initRooms();
        int actual = service.savePrivate(CHAT_MESSAGE_TO, USER_ID_2);
        assertEquals(PRIVATE_MESSAGE_4_ID, actual);

        ChatMessagePrivate expected = privateRepository.getOne(PRIVATE_MESSAGE_4_ID);
        assertEquals(expected, hazelcastInstance.getMap(CACHE_PRIVATE_ROOM_NAME).get(PRIVATE_MESSAGE_4_ID));

        assertThrows(IllegalArgumentException.class, () -> service.savePrivate(null, USER_ID_2));
        assertThrows(IllegalArgumentException.class, () -> service.savePrivate(CHAT_MESSAGE_TO, null));
    }

    @Test
    @Transactional
    void savePublic() {
        service.initRooms();
        int actual = service.savePublic(CHAT_MESSAGE_TO, ROOM_ID_LOCALE_RU);
        assertEquals(PUBLIC_MESSAGE_NEXT_ID, actual);

        ChatMessagePublic expected = publicRepository.getOne(PUBLIC_MESSAGE_NEXT_ID);
        assertEquals(expected, hazelcastInstance.getMap(CACHE_PUBLIC_ROOM_RU_NAME).get(PUBLIC_MESSAGE_NEXT_ID));

        assertThrows(IllegalArgumentException.class, () -> service.savePublic(null, ROOM_ID_LOCALE_RU));
        assertThrows(IllegalArgumentException.class, () -> service.savePublic(CHAT_MESSAGE_TO, null));
    }

    @Test
    void getPrivateMessagesByUserId() {
        service.initRooms();
        List<ChatMessagePrivate> actual1 = service.getPrivateMessagesByUserId(USER_ID_1);
        assertEquals(PRIVATE_MESSAGES, actual1);
        actual1.add(PRIVATE_MESSAGE_4);
        assertNotEquals(PRIVATE_MESSAGES, actual1);

        List<ChatMessagePrivate> actual2 = service.getPrivateMessagesByUserId(USER_ID_2);
        assertEquals(PRIVATE_MESSAGES, actual2);

        List<ChatMessagePrivate> actual3 = service.getPrivateMessagesByUserId(USER_ID_3);
        assertTrue(actual3.isEmpty());

        assertThrows(IllegalArgumentException.class, () -> service.getPrivateMessagesByUserId(null));
    }

    @Test
    void getPublicMessagesByLocaleId() {
        service.initRooms();
        List<ChatMessagePublic> actualRu = service.getPublicMessagesByLocaleId(ROOM_ID_LOCALE_RU);
        assertEquals(List.of(PUBLIC_MESSAGE_RU), actualRu);
        assertNotEquals(List.of(PUBLIC_MESSAGE_EN), actualRu);

        List<ChatMessagePublic> actualEn = service.getPublicMessagesByLocaleId(ROOM_ID_LOCALE_EN);
        assertEquals(List.of(PUBLIC_MESSAGE_EN), actualEn);
        assertNotEquals(List.of(PUBLIC_MESSAGE_RU), actualEn);

        assertThrows(IllegalArgumentException.class, () -> service.getPublicMessagesByLocaleId(null));
    }

    @Test
    void deletePublicById() {
        service.initRooms();
        assertTrue(service.deletePublicById(PUBLIC_MESSAGE_RU_ID, ROOM_ID_LOCALE_RU));
        assertFalse(service.deletePublicById(PUBLIC_MESSAGE_NEXT_ID, ROOM_ID_LOCALE_RU));
        assertThrows(IllegalArgumentException.class, () -> service.deletePublicById(null, ROOM_ID_LOCALE_RU));
        assertThrows(IllegalArgumentException.class, () -> service.deletePublicById(PUBLIC_MESSAGE_RU_ID, null));
    }

    @Test
    void deletePrivateById() {
        service.initRooms();
        assertTrue(service.deletePrivateById(PRIVATE_MESSAGE_1_ID));
        assertFalse(service.deletePrivateById(PRIVATE_MESSAGE_4_ID));
        assertEquals(List.of(PRIVATE_MESSAGE_2, PRIVATE_MESSAGE_3), service.getPrivateMessagesByUserId(USER_ID_1));
        assertThrows(IllegalArgumentException.class, () -> service.deletePrivateById(null));
    }
}