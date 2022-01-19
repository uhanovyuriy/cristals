package ch.boogaga.crystals.model.persist;

import ch.boogaga.crystals.util.validation.NoHtml;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "chat_message_public", uniqueConstraints =
        {@UniqueConstraint(name = "sender_id_unique_idx", columnNames = "sender_id"),
                @UniqueConstraint(name = "locale_id_unique_idx", columnNames = "locale_id")})
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ChatMessagePublic extends ChatMessage {
    @Column(name = "locale_id", nullable = false)
    @NotBlank
    @NoHtml
    @Size(min = 3, max = 20)
    private String localeId;
}
