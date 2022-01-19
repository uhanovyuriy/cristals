package ch.boogaga.crystals.model.persist;

import ch.boogaga.crystals.util.validation.NoHtml;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"senderName", "message", "created", "expireTime"})
@ToString(exclude = "message")
@MappedSuperclass
public class ChatMessage extends AbstractBaseEntity implements Serializable {
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
}
