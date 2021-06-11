package ch.boogaga.crystals.controller;

import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.model.message.Message;
import ch.boogaga.crystals.model.message.RequestMessage;
import ch.boogaga.crystals.model.message.ResponseMessage;
import ch.boogaga.crystals.service.UserService;
import ch.boogaga.crystals.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class RegistrationController {
    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    private final UserService userService;

    @Autowired
    public RegistrationController(final UserService userService) {
        this.userService = userService;
    }

    @MessageMapping("/incoming")
    @SendTo("/incoming/complete")
    public Message incoming(final RequestMessage requestMessage) {
        final User user = userService.findByLogin(requestMessage.getLogin());
        if (log.isDebugEnabled()) {
            log.debug("Debug message:" + requestMessage);
        }
        if (log.isInfoEnabled()) {
            log.info("Info message:" + requestMessage);
        }
        return user != null ? new ResponseMessage(user.toString(), false)
                : new ResponseMessage("user not found", true);
    }

    @MessageExceptionHandler(NotFoundException.class)
    @SendTo("/incoming/complete")
    public Message error(final NotFoundException e) {
        log.error("RegistrationController error Exception:", e);
        return new ResponseMessage(e.getMessage(), true);
    }
}
