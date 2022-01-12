package ch.boogaga.crystals.service;

import ch.boogaga.crystals.ConfigData;
import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.repository.UserCrudRepository;
import ch.boogaga.crystals.util.UserUtils;
import ch.boogaga.crystals.util.exception.NotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

@Service
public class UserService {
    private final UserCrudRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserCrudRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByLogin(@NotNull final String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User not found by login:" + login));
    }

    @Cacheable(value = ConfigData.CACHE_USERS_NAME)
    public User findByLoginWithCaching(@NotNull final String login) {
        return findByLogin(login);
    }

    @Transactional
    public User prepareAndCreate(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.saveAndFlush(UserUtils.prepareToSave(user, passwordEncoder));
    }
}
