package ch.boogaga.crystals.service.chat;

import ch.boogaga.crystals.ConfigData;
import ch.boogaga.crystals.model.persist.ChatMessagePrivate;
import ch.boogaga.crystals.model.persist.ChatMessagePublic;
import ch.boogaga.crystals.repository.chat.ChatPrivateRepository;
import ch.boogaga.crystals.repository.chat.ChatPublicRepository;
import ch.boogaga.crystals.to.ChatMessageTo;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

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
        Map<Integer, ChatMessagePrivate> privateMap = hazelcastInstance.getMap(CACHE_PRIVATE_ROOM_NAME);
        assertEquals(PRIVATE_MESSAGE_1, privateMap.get(PRIVATE_MESSAGE_1_ID));
        assertEquals(PRIVATE_MESSAGE_2, privateMap.get(PRIVATE_MESSAGE_2_ID));
        assertNotEquals(PRIVATE_MESSAGE_1, privateMap.get(PRIVATE_MESSAGE_3_ID));
        assertNull(privateMap.get(PRIVATE_MESSAGE_4_ID));

        Map<Integer, ChatMessagePublic> publicRuMap = hazelcastInstance.getMap(CACHE_PUBLIC_ROOM_RU_NAME);
        assertEquals(PUBLIC_MESSAGE_RU, publicRuMap.get(PUBLIC_MESSAGE_RU_ID));
        assertNotEquals(PUBLIC_MESSAGE_EN, publicRuMap.get(PUBLIC_MESSAGE_RU_ID));
        assertNull(publicRuMap.get(PUBLIC_MESSAGE_EN_ID));

        Map<Integer, ChatMessagePublic> publicEnMap = hazelcastInstance.getMap(ConfigData.CACHE_PUBLIC_ROOM_EN_NAME);
        assertEquals(PUBLIC_MESSAGE_EN, publicEnMap.get(PUBLIC_MESSAGE_EN_ID));
        assertNotEquals(PUBLIC_MESSAGE_RU, publicEnMap.get(PUBLIC_MESSAGE_EN_ID));
        assertNull(publicEnMap.get(PUBLIC_MESSAGE_RU_ID));
    }

    @Test
    @Transactional
    void savePrivate() {
        int actual = service.savePrivate(CHAT_MESSAGE_TO, USER_ID_2);
        assertEquals(PRIVATE_MESSAGE_4_ID, actual);
        ChatMessagePrivate expected = privateRepository.getOne(PRIVATE_MESSAGE_4_ID);
        assertEquals(expected, hazelcastInstance.getMap(CACHE_PRIVATE_ROOM_NAME).get(PRIVATE_MESSAGE_4_ID));
    }

    @Test
    @Transactional
    void savePublic() {
        int actual = service.savePublic(CHAT_MESSAGE_TO, ROOM_ID_LOCALE_RU);
        assertEquals(PUBLIC_MESSAGE_NEXT_ID, actual);
        ChatMessagePublic expected = publicRepository.getOne(PUBLIC_MESSAGE_NEXT_ID);
        assertEquals(expected, hazelcastInstance.getMap(CACHE_PUBLIC_ROOM_RU_NAME).get(PUBLIC_MESSAGE_NEXT_ID));
    }
}