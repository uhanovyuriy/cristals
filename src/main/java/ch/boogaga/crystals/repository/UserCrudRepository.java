package ch.boogaga.crystals.repository;

import ch.boogaga.crystals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserCrudRepository extends JpaRepository<User, Integer> {

    User findByLogin(final String login);
}
