package ch.boogaga.crystals.model.persist;

import ch.boogaga.crystals.model.MessageStatus;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message_private", uniqueConstraints =
        {@UniqueConstraint(name = "sender_id_unique_idx", columnNames = "sender_id"),
                @UniqueConstraint(name = "recipient_id_unique_idx", columnNames = "recipient_id")})
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ChatMessagePrivate extends ChatMessage {
    @Column(name = "recipient_id", nullable = false)
    @Range(max = Integer.MAX_VALUE)
    private Integer recipientId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "status")
    private MessageStatus status;

    public ChatMessagePrivate(Integer id, @Range(max = Integer.MAX_VALUE) Integer senderId,
                              @NotBlank @Size(min = 3, max = 50) String senderName,
                              @NotBlank @Size(max = 4096) String message,
                              LocalDateTime created, LocalDateTime expireTime, Integer recipientId,
                              MessageStatus status) {
        super(senderId, senderName, message, created, expireTime);
        this.recipientId = recipientId;
        this.status = status;
        setId(id);
    }
}
