package ch.boogaga.crystals.repository;

import ch.boogaga.crystals.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface LevelCrudRepository extends JpaRepository<Level, Integer> {
}
