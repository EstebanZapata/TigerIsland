package thread;

import tile.Location;
import tile.Tile;
import tile.orientation.TileOrientation;

public class GameResponseMessage extends Message {
    private Tile tilePlaced;
    private Location locationOfVolcano;
    private TileOrientation tileOrientationPlaced;

    private BuildAction buildActionPerformed;
    private Location locationOfBuildAction;

    public GameResponseMessage(Tile tilePlaced, Location locationOfVolcano, TileOrientation tileOrientationPlaced,
                               BuildAction buildActionPerformed, Location locationOfBuildAction)
    {
        this.tilePlaced = tilePlaced;
        this.locationOfVolcano = locationOfVolcano;
        this.tileOrientationPlaced = tileOrientationPlaced;
        this.buildActionPerformed = buildActionPerformed;
        this.locationOfBuildAction = locationOfBuildAction;
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




}
