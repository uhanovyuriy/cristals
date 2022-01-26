package ch.boogaga.crystals.repository.chat;

import ch.boogaga.crystals.model.persist.ChatMessagePublic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatPublicRepository extends JpaRepository<ChatMessagePublic, Integer> {
    List<ChatMessagePublic> findAllByLocaleId(String localeId);
}
