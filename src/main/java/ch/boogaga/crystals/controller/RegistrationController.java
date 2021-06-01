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
    public RegistrationController(UserCrudRepository userRepository) {
        this.userRepository = userRepository;
    }

    @MessageMapping("/incoming")
    @SendTo("/incoming/complete")
    public Message incoming(RequestMessage requestMessage) {
        final User user = new User(requestMessage.getName());
        userRepository.save(user);
        return new ResponseMessage(true);
    }

    @MessageExceptionHandler(NotFoundException.class)
    @SendTo("/incoming/complete")
    public Message error(NotFoundException e) {
        return new ResponseMessage(e.getMessage(), true);
    }
}
