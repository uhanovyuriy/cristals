package ch.boogaga.crystals.model.message;

public class RequestMessage {
    private String login;

    public RequestMessage() {
    }

    public RequestMessage(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
