package ch.boogaga.crystals.controller;

import ch.boogaga.crystals.ConfigData;
import ch.boogaga.crystals.model.message.MessageResponse;
import ch.boogaga.crystals.model.message.PageRequest;
import ch.boogaga.crystals.model.persist.ChatMessagePrivate;
import ch.boogaga.crystals.service.chat.ChatRoomService;
import ch.boogaga.crystals.to.ChatMessageTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.util.List;

import static ch.boogaga.crystals.ConfigData.*;

@Controller
public class ChatController {
    private static final Logger Log = LoggerFactory.getLogger(ChatController.class.getName());
    public static final String URI_CHAT_PUBLIC = "/chat/public";
    public static final String URI_CHAT_PRIVATE = "/chat/private";
    public static final String ADD = "/add";
    public static final String GET_ALL = "/all";
    public static final String DELETE = "/delete";
    public static final String TARGET_GET_ALL = "chat.getAllPrivate";

    private final ChatRoomService service;
    private final ObjectMapper objectMapper;

    public ChatController(ChatRoomService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @MessageMapping(value = URI_CHAT_PRIVATE + ADD + "/{rid}")
    @SendTo(value = APP_INCOMING_COMPLETE)
    public ChatMessageTo privateAdd(@Payload ChatMessageTo to, @DestinationVariable("rid") int rid) {
        Assert.notNull(to, "to must not be null");
        to.setId(service.savePrivate(to, rid));
        return to;
    }

    @MessageMapping(value = URI_CHAT_PRIVATE + GET_ALL)
    @SendTo(value = APP_INCOMING_COMPLETE)
    public MessageResponse privateGetAllByUserId(@Payload PageRequest pageRequest) throws JsonProcessingException {
        Assert.notNull(pageRequest, "pageRequest must not be null");
        final List<ChatMessagePrivate> privates = service.getPrivateMessagesByUserId(pageRequest.getUserId(),
                pageRequest.getStartPage(), pageRequest.getEndPage());
        return new MessageResponse(TARGET_GET_ALL, objectMapper.writeValueAsString(privates), true);
    }

    @MessageMapping(value = URI_CHAT_PRIVATE + DELETE)
    @SendTo(value = APP_INCOMING_COMPLETE)
    public boolean privateDeleteById(@Param("mid") int mid) {
        return service.deletePrivateById(mid);
    }

    @MessageMapping(value = URI_CHAT_PUBLIC + ADD)
    @SendTo(value = APP_INCOMING_COMPLETE)
    public ChatMessageTo publicAdd(@Payload ChatMessageTo to) {
        Assert.notNull(to, "to must not be null");
        to.setId(service.savePublic(to, ConfigData.ROOM_ID_LOCALE_RU));
        return to;
    }

    @MessageExceptionHandler
    @SendTo("/crystals/incoming/complete")
    public MessageResponse error(Throwable e) {
        Log.error(e.getMessage());
        return new MessageResponse("error", e.getMessage(), true);
    }
}
