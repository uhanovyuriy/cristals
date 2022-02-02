package ch.boogaga.crystals.testdata;

import ch.boogaga.crystals.model.MessageStatus;
import ch.boogaga.crystals.model.persist.ChatMessage;
import ch.boogaga.crystals.model.persist.ChatMessagePrivate;
import ch.boogaga.crystals.model.persist.ChatMessagePublic;
import ch.boogaga.crystals.to.ChatMessageTo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static ch.boogaga.crystals.ConfigData.ROOM_ID_LOCALE_EN;
import static ch.boogaga.crystals.ConfigData.ROOM_ID_LOCALE_RU;
import static ch.boogaga.crystals.testdata.TestDataUser.*;

public class TestDataChat {
    public static final int PRIVATE_MESSAGE_1_ID = 1;
    public static final int PRIVATE_MESSAGE_2_ID = 2;
    public static final int PRIVATE_MESSAGE_3_ID = 3;
    public static final int PRIVATE_MESSAGE_4_ID = 4;
    public static final int PUBLIC_MESSAGE_RU_1_ID = 1;
    public static final int PUBLIC_MESSAGE_RU_2_ID = 2;
    public static final int PUBLIC_MESSAGE_RU_3_ID = 3;
    public static final int PUBLIC_MESSAGE_RU_4_ID = 4;
    public static final int PUBLIC_MESSAGE_EN_5_ID = 5;
    public static final int PUBLIC_MESSAGE_6_ID = 6;

    public static final ChatMessageTo CHAT_MESSAGE_TO =
            new ChatMessageTo(USER_ID_1, USER_1.getName(), "test data");
    public static final ChatMessageTo INVALID_CHAT_MESSAGE_TO =
            new ChatMessageTo(USER_ID_1, null, "test data");

    public static final ChatMessagePrivate PRIVATE_MESSAGE_1 = new ChatMessagePrivate(PRIVATE_MESSAGE_1_ID,
            USER_ID_1, USER_1.getName(), "private message 1", LocalDateTime.of(2022, Month.JANUARY,
            8, 12, 30), LocalDateTime.of(2022, Month.JANUARY,
            8, 13, 30), USER_ID_2,
            MessageStatus.DELIVERED);
    public static final ChatMessagePrivate PRIVATE_MESSAGE_2 = new ChatMessagePrivate(PRIVATE_MESSAGE_2_ID,
            USER_ID_1, USER_1.getName(), "private message 2", LocalDateTime.of(2022, Month.JANUARY,
            9, 12, 40), LocalDateTime.of(2022, Month.JANUARY,
            9, 13, 40), USER_ID_2,
            MessageStatus.RECEIVED);
    public static final ChatMessagePrivate PRIVATE_MESSAGE_3 = new ChatMessagePrivate(PRIVATE_MESSAGE_3_ID,
            USER_ID_2, USER_2.getName(), "private message 3", LocalDateTime.of(2022, Month.JANUARY,
            9, 12, 45), LocalDateTime.of(2022, Month.JANUARY,
            9, 13, 45), USER_ID_1,
            MessageStatus.RECEIVED);
    public static final ChatMessagePrivate PRIVATE_MESSAGE_4 = new ChatMessagePrivate(PRIVATE_MESSAGE_4_ID,
            USER_ID_3, USER_3.getName(), "private message 4", LocalDateTime.of(2022, Month.JANUARY,
            9, 13, 45), LocalDateTime.of(2022, Month.JANUARY,
            9, 14, 45), USER_ID_2,
            MessageStatus.RECEIVED);
    public static final ChatMessagePublic PUBLIC_MESSAGE_RU_1 = new ChatMessagePublic(PUBLIC_MESSAGE_RU_1_ID, USER_ID_1,
            USER_1.getName(), "public message 1", LocalDateTime.of(2022, Month.JANUARY,
            8, 12, 35), LocalDateTime.of(2022, Month.JANUARY,
            8, 13, 35), ROOM_ID_LOCALE_RU);
    public static final ChatMessagePublic PUBLIC_MESSAGE_RU_2 = new ChatMessagePublic(PUBLIC_MESSAGE_RU_2_ID, USER_ID_1,
            USER_1.getName(), "public message 2", LocalDateTime.of(2022, Month.JANUARY,
            8, 12, 36), LocalDateTime.of(2022, Month.JANUARY,
            8, 13, 36), ROOM_ID_LOCALE_RU);
    public static final ChatMessagePublic PUBLIC_MESSAGE_RU_3 = new ChatMessagePublic(PUBLIC_MESSAGE_RU_3_ID, USER_ID_1,
            USER_1.getName(), "public message 3", LocalDateTime.of(2022, Month.JANUARY,
            8, 12, 37), LocalDateTime.of(2022, Month.JANUARY,
            8, 13, 37), ROOM_ID_LOCALE_RU);
    public static final ChatMessagePublic PUBLIC_MESSAGE_RU_4 = new ChatMessagePublic(PUBLIC_MESSAGE_RU_4_ID, USER_ID_1,
            USER_1.getName(), "public message 4", LocalDateTime.of(2022, Month.JANUARY,
            8, 12, 38), LocalDateTime.of(2022, Month.JANUARY,
            8, 13, 38), ROOM_ID_LOCALE_RU);
    public static final ChatMessagePublic PUBLIC_MESSAGE_EN = new ChatMessagePublic(PUBLIC_MESSAGE_EN_5_ID, USER_ID_2,
            USER_2.getName(), "public message 5", LocalDateTime.of(2022, Month.JANUARY,
            8, 13, 35), LocalDateTime.of(2022, Month.JANUARY,
            8, 14, 35), ROOM_ID_LOCALE_EN);

    public static final List<ChatMessage> PRIVATE_MESSAGES_PAGE_1_COUNT_2 = List.of(PRIVATE_MESSAGE_1,
            PRIVATE_MESSAGE_2);
    public static final List<ChatMessage> PRIVATE_MESSAGES_PAGE_2_COUNT_2 = List.of(PRIVATE_MESSAGE_3);

    public static final List<ChatMessage> PUBLIC_MESSAGES_RU_PAGE_1_COUNT_3 = List.of(PUBLIC_MESSAGE_RU_1,
            PUBLIC_MESSAGE_RU_2, PUBLIC_MESSAGE_RU_3);
    public static final List<ChatMessage> PUBLIC_MESSAGES_RU_PAGE_2_COUNT_3 =
            Collections.singletonList(PUBLIC_MESSAGE_RU_4);
}
