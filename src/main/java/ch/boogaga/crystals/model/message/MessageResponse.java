package ch.boogaga.crystals.model.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse implements Serializable {
    private String target;
    private String content;
    private boolean completed;

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "target='" + target + '\'' +
                ", completed=" + completed +
                '}';
    }
}
