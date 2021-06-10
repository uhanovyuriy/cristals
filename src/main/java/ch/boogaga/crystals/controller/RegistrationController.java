package ch.boogaga.crystals.controller;

import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.model.message.Message;
import ch.boogaga.crystals.model.message.RequestMessage;
import ch.boogaga.crystals.model.message.ResponseMessage;
import ch.boogaga.crystals.service.UserService;
import ch.boogaga.crystals.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class RegistrationController {
    private static final Logger log = Logger.getLogger(RegistrationController.class.getName());

    private final UserService userService;

    @Autowired
    public RegistrationController(final UserService userService) {
        this.userService = userService;
    }

    @MessageMapping("/incoming")
    @SendTo("/incoming/complete")
    public Message incoming(final RequestMessage requestMessage) {
        final User user = userService.findByLogin(requestMessage.getLogin());
        log.logp(Level.INFO, "RegistrationController", "incoming",
                "Request:" + requestMessage);
        return user != null ? new ResponseMessage(user.toString(), false)
                : new ResponseMessage("user not found", true);
    }

    @MessageExceptionHandler(NotFoundException.class)
    @SendTo("/incoming/complete")
    public Message error(final NotFoundException e) {
        log.logp(Level.WARNING, "RegistrationController", "error",
                "Exception:" + e.getMessage());
        return new ResponseMessage(e.getMessage(), true);
    }
}
