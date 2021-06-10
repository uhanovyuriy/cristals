package ch.boogaga.crystals.service;

import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.repository.UserCrudRepository;
import ch.boogaga.crystals.util.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserService {
    private final UserCrudRepository repository;

    public UserService(UserCrudRepository repository) {
        this.repository = repository;
    }

    public User findByLogin(@NotNull final String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User not found by login:" + login));
    }
}
