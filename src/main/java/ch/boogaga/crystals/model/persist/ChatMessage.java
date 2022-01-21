package ch.boogaga.crystals.model.persist;

import ch.boogaga.crystals.util.validation.NoHtml;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"senderName", "message", "created", "expireTime"})
@MappedSuperclass
@ToString(callSuper = true)
public class ChatMessage extends AbstractBaseEntity {
    @Column(name = "sender_id", nullable = false)
    @Range(max = Integer.MAX_VALUE)
    private Integer senderId;

    @Column(name = "sender_name", nullable = false)
    @NotBlank
    @NoHtml
    @Size(min = 3, max = 50)
    private String senderName;

    @Column(name = "message", nullable = false)
    @NotBlank
    @Size(max = 4096)
    private String message;

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    private LocalDateTime created;

    @Column(name = "expire_time", nullable = false)
    private LocalDateTime expireTime;

    public ChatMessage() {
    }

    public ChatMessage(Integer senderId, String senderName, String message, LocalDateTime created, LocalDateTime expireTime) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.message = message;
        this.created = created;
        this.expireTime = expireTime;
    }
}
