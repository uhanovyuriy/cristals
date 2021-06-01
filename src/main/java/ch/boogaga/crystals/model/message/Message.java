package ch.boogaga.crystals.model.message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter @Setter @EqualsAndHashCode @ToString
public class Message {
    private boolean error;
    private int userId;
    private Map<String, String> param;

    public Message() {
    }
}
