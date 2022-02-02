package ch.boogaga.crystals.repository.chat;

import ch.boogaga.crystals.model.persist.ChatMessagePrivate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface ChatPrivateRepository extends JpaRepository<ChatMessagePrivate, Integer> {
    @Modifying
    @Query("DELETE FROM ChatMessagePrivate c WHERE c.id = :id")
    int delete(@Param("id") Integer id);
}
