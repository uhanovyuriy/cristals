package ch.boogaga.crystals.service.chat;

import ch.boogaga.crystals.ConfigData;
import ch.boogaga.crystals.model.persist.ChatMessagePrivate;
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

import java.util.List;
import java.util.Map;

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
        Map<Integer, List<ChatMessagePrivate>> actualPrivateSender =
                (Map<Integer, List<ChatMessagePrivate>>) hazelcastInstance.getMap(ConfigData.CACHE_CHAT_NAME)
                        .get(ConfigData.PRIVATE_SENDER_ROOMS);
        assertEquals(List.of(PRIVATE_MESSAGE_1, PRIVATE_MESSAGE_2), actualPrivateSender.get(USER_ID_1));
        assertEquals(List.of(PRIVATE_MESSAGE_3), actualPrivateSender.get(USER_ID_2));

        Map<Integer, List<ChatMessagePrivate>> actualPrivateRecipient =
                (Map<Integer, List<ChatMessagePrivate>>) hazelcastInstance.getMap(ConfigData.CACHE_CHAT_NAME)
                        .get(ConfigData.PRIVATE_RECIPIENT_ROOMS);
        assertEquals(List.of(PRIVATE_MESSAGE_1, PRIVATE_MESSAGE_2), actualPrivateRecipient.get(USER_ID_2));
        assertEquals(List.of(PRIVATE_MESSAGE_3), actualPrivateRecipient.get(USER_ID_1));

        assertEquals(List.of(PUBLIC_MESSAGE_1),
                hazelcastInstance.getMap(ConfigData.CACHE_CHAT_NAME).get(ConfigData.ROOM_ID_LOCALE_RU));
        assertEquals(List.of(PUBLIC_MESSAGE_2),
                hazelcastInstance.getMap(ConfigData.CACHE_CHAT_NAME).get(ConfigData.ROOM_ID_LOCALE_EN));
    }
}