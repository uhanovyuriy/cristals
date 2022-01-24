package ch.boogaga.crystals.model.notification;

import ch.boogaga.crystals.HasId;
import lombok.Data;

@Data
public class ChatNotification implements HasId<Integer> {
    private Integer id;
    private Integer senderId;
    private String senderName;
}
