package ch.boogaga.crystals.repository.chat;

import ch.boogaga.crystals.model.persist.ChatMessagePublic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ChatPublicRepository extends JpaRepository<ChatMessagePublic, Integer> {
    @Modifying
    @Query("DELETE FROM ChatMessagePublic c WHERE c.id = :id")
    int delete(@Param("id") Integer id);
}
