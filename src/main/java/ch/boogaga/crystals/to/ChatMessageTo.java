package ch.boogaga.crystals.to;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatMessageTo extends BaseTo {
    private Integer senderId;
    private String senderName;
    private String message;
}
