package ch.boogaga.crystals.repository;

import ch.boogaga.crystals.model.persist.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserCrudRepository extends JpaRepository<User, Integer> {

     Optional<User> findByLogin(final String login);

     @Modifying
     @Query("DELETE FROM User u WHERE u.id=:id")
     int delete(@Param("id") int id);
}
