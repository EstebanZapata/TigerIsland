import tile.Tile;

public class GameMessage extends Message {
    private String gameId;
    private double moveTime;
    private Tile tileToPlace;

    public GameMessage(String gameId, double moveTime, Tile tileToPlace) {
        this.gameId = gameId;
        this.moveTime = moveTime;
        this.tileToPlace = tileToPlace;
    }

    public String getGameId() {
        return gameId;
    }

    public double getMoveTime() {
        return moveTime;
    }

    public Tile getTileToPlace() {
        return tileToPlace;
    }
}
