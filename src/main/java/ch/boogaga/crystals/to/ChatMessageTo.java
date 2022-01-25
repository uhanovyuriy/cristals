package ch.boogaga.crystals.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ChatMessageTo extends BaseTo {
    private final Integer senderId;
    private final String senderName;
    private final String message;
}
