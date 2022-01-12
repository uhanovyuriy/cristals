package ch.boogaga.crystals.controller;

import ch.boogaga.crystals.ConfigData;
import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.service.UserService;
import ch.boogaga.crystals.to.UserTo;
import ch.boogaga.crystals.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(value = RegisterController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RegisterController {
    public static final String REST_URL = ConfigData.APP_DESTINATION_PREFIX + "/rest/register";

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@RequestBody UserTo userTo) {
        Assert.notNull(userTo, "userTo must not be null");
        final User created = userService.prepareAndCreate(UserUtils.fromTo(userTo));
        final URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL).build().toUri();
        return ResponseEntity.created(uri).body(created);
    }
}
