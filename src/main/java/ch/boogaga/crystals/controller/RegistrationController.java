package ch.boogaga.crystals.controller;

import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.model.message.Message;
import ch.boogaga.crystals.model.message.RequestMessage;
import ch.boogaga.crystals.model.message.ResponseMessage;
import ch.boogaga.crystals.repository.UserCrudRepository;
import ch.boogaga.crystals.util.UserUtils;
import ch.boogaga.crystals.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@Controller
public class RegistrationController {

    private final UserCrudRepository userRepository;

    @Autowired
    public RegistrationController(UserCrudRepository userRepository) {
        this.userRepository = userRepository;
    }

    @MessageMapping("/signIn")
    @SendTo("/signIn/complete")
    public Message signIn(RequestMessage requestMessage) {
//        userRepository.findById(requestMessage.getUserId()).orElseThrow(() -> new NotFoundException("error in signIn"));
        return new ResponseMessage(true);
    }

    @MessageMapping("/register")
    @SendTo("/register/complete")
    public Message register(RequestMessage requestMessage) {
        final User user = new User(requestMessage.getName());
        userRepository.save(user);
        return new ResponseMessage(true);
    }

    @ExceptionHandler(NotFoundException.class)
    @SendTo("/signIn/complete")
    public Message error(NotFoundException e) {
        ResponseMessage responseMessage = new ResponseMessage(false);
        responseMessage.setContent(new HashMap<>());
        responseMessage.getContent().put("error", e.getMessage());
        return responseMessage;
    }
}
