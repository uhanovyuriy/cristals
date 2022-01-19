package ch.boogaga.crystals.model.persist;

import ch.boogaga.crystals.model.MessageStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "chat_message_private", uniqueConstraints =
        {@UniqueConstraint(name = "sender_id_unique_idx", columnNames = "sender_id"),
                @UniqueConstraint(name = "recipient_id_unique_idx", columnNames = "recipient_id")})
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = "status")
public class ChatMessagePrivate extends ChatMessage {
    @Column(name = "recipient_id", nullable = false)
    @Range(max = Integer.MAX_VALUE)
    private Integer recipientId;

    @Column(name = "status", nullable = false)
    private MessageStatus status;
}
