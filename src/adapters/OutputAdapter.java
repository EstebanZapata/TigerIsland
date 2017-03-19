package adapters;

import tile.Location;
import tile.Tile;
import tile.orientation.TileOrientation;

public interface OutputAdapter {
    void placeTile(Tile tile, Location locationOfVolcano, TileOrientation tileOrientation);
}
