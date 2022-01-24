package ch.boogaga.crystals.repository.chat;

import ch.boogaga.crystals.model.persist.ChatMessagePublic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatPublicRepository extends JpaRepository<ChatMessagePublic, String> {

}
