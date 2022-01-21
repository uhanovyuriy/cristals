package ch.boogaga.crystals.model.persist;

import ch.boogaga.crystals.util.validation.NoHtml;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message_public", uniqueConstraints =
        {@UniqueConstraint(name = "sender_id_unique_idx", columnNames = "sender_id"),
                @UniqueConstraint(name = "locale_id_unique_idx", columnNames = "locale_id")})
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ChatMessagePublic extends ChatMessage {
    @Column(name = "locale_id", nullable = false)
    @NotBlank
    @NoHtml
    @Size(min = 3, max = 20)
    private String localeId;

    public ChatMessagePublic(Integer id, @Range(max = Integer.MAX_VALUE) Integer senderId,
                             @NotBlank @Size(min = 3, max = 50) String senderName,
                             @NotBlank @Size(max = 4096) String message,
                             LocalDateTime created, LocalDateTime expireTime, String localeId) {
        super(senderId, senderName, message, created, expireTime);
        this.localeId = localeId;
        setId(id);
    }
}
