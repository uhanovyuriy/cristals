package ch.boogaga.crystals.repository.chat;

import ch.boogaga.crystals.model.persist.ChatMessagePrivate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
public interface ChatPrivateRepository extends JpaRepository<ChatMessagePrivate, Integer> {
    default List<ChatMessagePrivate> getPrivateMessagesByUserId(Integer userId) {
       return findAllBySenderIdOrRecipientId(userId, userId);
    }
    List<ChatMessagePrivate> findAllBySenderIdOrRecipientId(Integer senderId, Integer recipientId);
}
