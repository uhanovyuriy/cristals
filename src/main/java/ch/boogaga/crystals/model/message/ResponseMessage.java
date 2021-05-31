package ch.boogaga.crystals.model.message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @EqualsAndHashCode(callSuper = true) @ToString
public class ResponseMessage extends Message {
    private final boolean isComplete;

    public ResponseMessage(boolean isComplete) {
        this.isComplete = isComplete;
    }
}
