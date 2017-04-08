package thread.message;

public class GameEndMessage extends Message {
    private String gameId;

    public GameEndMessage(String gameId){
        this.gameId = gameId;
    }

    public String getGameId() {
        return this.gameId;
    }
}
