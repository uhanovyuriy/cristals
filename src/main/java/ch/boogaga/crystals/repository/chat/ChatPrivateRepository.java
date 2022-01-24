package ch.boogaga.crystals.repository.chat;

import ch.boogaga.crystals.model.persist.ChatMessagePrivate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface ChatPrivateRepository extends JpaRepository<ChatMessagePrivate, Integer> {
}
