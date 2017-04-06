package game.world;

import tile.Hex;

public class TileManager {
    public Hex[][] hexCoordinateSystem;

    public static final int SIZE_OF_BOARD = 200;

    public TileManager() {
        initializeHexCoordinateSystem();
    }

    private void initializeHexCoordinateSystem() {
        hexCoordinateSystem = new Hex[SIZE_OF_BOARD][SIZE_OF_BOARD];
    }
}
