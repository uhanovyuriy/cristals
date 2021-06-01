package ch.boogaga.crystals.model.message;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class ResponseMessage extends Message {
    private String content;
    private boolean completed;

    public ResponseMessage(String content, boolean error) {
        this.content = content;
        this.setError(error);
    }

    public ResponseMessage(boolean completed) {
        this.completed = completed;
    }
}
