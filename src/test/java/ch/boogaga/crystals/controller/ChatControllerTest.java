package ch.boogaga.crystals.controller;

import ch.boogaga.crystals.model.message.MessageResponse;
import ch.boogaga.crystals.model.message.PageRequest;
import ch.boogaga.crystals.to.ChatMessageTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static ch.boogaga.crystals.ConfigData.*;
import static ch.boogaga.crystals.controller.ChatController.*;
import static ch.boogaga.crystals.testdata.TestDataChat.*;
import static ch.boogaga.crystals.testdata.TestDataUser.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:db/schemaTestDataChatDb.sql", "classpath:db/populateTestDataChatDb.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
class ChatControllerTest {

    @Autowired
    private ChatController chatController;

    @LocalServerPort
    private Integer port;
    private StompSession stompSession;

    @BeforeEach
    void setUp() throws ExecutionException, InterruptedException, TimeoutException {
        String URL = "ws://localhost:" + port + END_POINT;

        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(List.of(new WebSocketTransport(
                new StandardWebSocketClient()))));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setInboundMessageSizeLimit(Integer.MAX_VALUE);
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add(LOGIN_HEADER, USER_1.getLogin());
        stompHeaders.add(PASSWORD_HEADER, USER_1.getPassword());
        stompSession = stompClient.connect(URL, new WebSocketHttpHeaders(), stompHeaders,
                new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);
    }

    @Test
    void privateAdd() throws ExecutionException, InterruptedException, TimeoutException {
        assertTrue(stompSession.isConnected());
        CompletableFuture<ChatMessageTo> completableFuture = new CompletableFuture<>();
        stompSession.subscribe(APP_INCOMING_COMPLETE, new CreateStompFrameHandler<>(ChatMessageTo.class, completableFuture));
        stompSession.send(APP_DESTINATION_PREFIX + URI_CHAT_PRIVATE + ADD + "/" + USER_ID_2, CHAT_MESSAGE_TO);
        ChatMessageTo actual = completableFuture.get(5, TimeUnit.SECONDS);
        assertEquals(PRIVATE_MESSAGE_4_ID, actual.getId());
        assertThrows(IllegalArgumentException.class, () -> chatController.privateAdd(null, USER_ID_2));
        assertThrows(MessageConversionException.class,
                () -> stompSession.send(APP_DESTINATION_PREFIX + URI_CHAT_PRIVATE + ADD, new Object()));
    }

    @Test
    void privateGetAllByUserId() throws ExecutionException, InterruptedException {
        assertTrue(stompSession.isConnected());
        CompletableFuture<MessageResponse> completableFuture = new CompletableFuture<>();
        stompSession.subscribe(APP_INCOMING_COMPLETE,
                new CreateStompFrameHandler<>(MessageResponse.class, completableFuture));
        stompSession.send(APP_DESTINATION_PREFIX + URI_CHAT_PRIVATE + GET_ALL,
                new PageRequest(USER_ID_1, 1, 10, null));
        MessageResponse actual = completableFuture.get();
        System.out.println(actual);
    }

    @Test
    void publicAdd() throws ExecutionException, InterruptedException, TimeoutException {
        assertTrue(stompSession.isConnected());
        CompletableFuture<ChatMessageTo> completableFuture = new CompletableFuture<>();
        stompSession.subscribe(APP_INCOMING_COMPLETE, new CreateStompFrameHandler<>(ChatMessageTo.class, completableFuture));
        stompSession.send(APP_DESTINATION_PREFIX + URI_CHAT_PUBLIC + ADD, CHAT_MESSAGE_TO);
        ChatMessageTo actual = completableFuture.get(5, TimeUnit.SECONDS);
        assertEquals(PUBLIC_MESSAGE_6_ID, actual.getId());
        assertThrows(IllegalArgumentException.class, () -> chatController.publicAdd(null));
        assertThrows(MessageConversionException.class,
                () -> stompSession.send(APP_DESTINATION_PREFIX + URI_CHAT_PUBLIC + ADD, new Object()));
    }

    private static class CreateStompFrameHandler<T> implements StompFrameHandler {

        private final Class<T> tClass;
        private final CompletableFuture<T> completableFuture;

        public CreateStompFrameHandler(Class<T> tClass, CompletableFuture<T> completableFuture) {
            this.tClass = tClass;
            this.completableFuture = completableFuture;
        }

        @Override
        public Class<T> getPayloadType(StompHeaders stompHeaders) {
            return tClass;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((T) o);
        }
    }
}