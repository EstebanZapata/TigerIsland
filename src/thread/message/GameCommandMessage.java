package thread.message;

import game.tile.Tile;

public class GameCommandMessage extends Message {
    private String gameId;
    private double moveTime;
    private int moveNumber;
    private Tile tileToPlace;

    public GameCommandMessage(String gameId, double moveTime, int moveNumber, Tile tileToPlace) {
        this.gameId = gameId;
        this.moveTime = moveTime;
        this.moveNumber = moveNumber;
        this.tileToPlace = tileToPlace;
    }

    public String getGameId() {
        return gameId;
    }

    public double getMoveTime() {
        return moveTime;
    }

    public int getMoveNumber() {
        return this.moveNumber;
    }

    public Tile getTileToPlace() {
        return tileToPlace;
    }
}
