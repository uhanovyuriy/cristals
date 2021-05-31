package ch.boogaga.crystals.model.message;

public class RequestMessage {
    private String name;

    public RequestMessage() {
    }

    public RequestMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
