package thread.message;

public class PlayerIdMessage extends Message {
    private String playerId;

    public PlayerIdMessage(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return this.playerId;
    }
}
