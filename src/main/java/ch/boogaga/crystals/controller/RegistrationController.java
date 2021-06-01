package ch.boogaga.crystals.controller;

import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.model.message.Message;
import ch.boogaga.crystals.model.message.RequestMessage;
import ch.boogaga.crystals.model.message.ResponseMessage;
import ch.boogaga.crystals.repository.UserCrudRepository;
import ch.boogaga.crystals.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class RegistrationController {

    private final UserCrudRepository userRepository;

    @Autowired
    public RegistrationController(final UserCrudRepository userRepository) {
        this.userRepository = userRepository;
    }

    @MessageMapping("/incoming")
    @SendTo("/incoming/complete")
    public Message incoming(final RequestMessage requestMessage) {
        final User user = userRepository.findByLogin(requestMessage.getLogin());
        return user != null ? new ResponseMessage(user.toString(), false)
                : new ResponseMessage("user not found", true);
    }

    @MessageExceptionHandler(NotFoundException.class)
    @SendTo("/incoming/complete")
    public Message error(final NotFoundException e) {
        return new ResponseMessage(e.getMessage(), true);
    }
}
