package thread.message;

import game.tile.Location;
import game.tile.Terrain;
import game.tile.Tile;
import game.tile.orientation.TileOrientation;
import game.settlements.BuildAction;

public class GameActionMessage extends Message {
    private String gameId;
    private int moveNumber;
    private String playerId;
    private Tile tilePlaced;
    private Location locationOfVolcano;
    private TileOrientation tileOrientationPlaced;
    private BuildAction buildActionPerformed;
    private Location locationOfBuildAction;
    private Terrain terrainExpandedOnto;

    public GameActionMessage(String gameId, int moveNumber, String playerId,  Tile tilePlaced,
                             Location locationOfVolcano, TileOrientation tileOrientationPlaced,
                             BuildAction buildActionPerformed, Location locationOfBuildAction, Terrain terrainToExpandOnto)
    {
        this.gameId = gameId;
        this.moveNumber = moveNumber;
        this.playerId = playerId;
        this.tilePlaced = tilePlaced;
        this.locationOfVolcano = locationOfVolcano;
        this.tileOrientationPlaced = tileOrientationPlaced;
        this.buildActionPerformed = buildActionPerformed;
        this.locationOfBuildAction = locationOfBuildAction;
        this.terrainExpandedOnto = terrainToExpandOnto;
    }

    public String getGameId() {
        return gameId;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Tile getTilePlaced() {
        return tilePlaced;
    }

    public Location getLocationOfVolcano() {
        return locationOfVolcano;
    }

    public TileOrientation getTileOrientationPlaced() {
        return tileOrientationPlaced;
    }

    public BuildAction getBuildActionPerformed() {
        return buildActionPerformed;
    }

    public Location getLocationOfBuildAction() {
        return locationOfBuildAction;
    }

    public Terrain getTerrainExpandedOnto() {
        return terrainExpandedOnto;
    }


}
