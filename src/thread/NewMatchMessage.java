package thread;

public class NewMatchMessage extends Message {
    private String opponentId;

    public NewMatchMessage(String opponentId) {
        this.opponentId = opponentId;
    }

    public String getOpponentId() {
        return this.opponentId;
    }
}
