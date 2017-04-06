package game.world;

import tile.Hex;
import tile.Location;

public class TileManager {
    public Hex[][] hexCoordinateSystem;

    private static final int SIZE_OF_BOARD = 200;
    private static final int ORIGIN_OFFSET = SIZE_OF_BOARD/2;


    public TileManager() {
        initializeHexCoordinateSystem();
    }

    private void initializeHexCoordinateSystem() {
        hexCoordinateSystem = new Hex[SIZE_OF_BOARD][SIZE_OF_BOARD];
    }

    public void insertHexIntoCoordinateSystemAtLocation(Hex hex, Location location) {
        insertHexIntoCoordinateSystemAtCoordinates(hex, location.getxCoordinate(), location.getyCoordinate());
    }

    private void insertHexIntoCoordinateSystemAtCoordinates(Hex hex, int x, int y) {
        int arrayCoordinateX = getArrayCoordinateFromTrueCoordinate(x);
        int arrayCoordinateY = getArrayCoordinateFromTrueCoordinate(y);

        hexCoordinateSystem[arrayCoordinateX][arrayCoordinateY] = hex;
    }

    public int getArrayCoordinateFromTrueCoordinate(int trueCoordinate) {
        return trueCoordinate + ORIGIN_OFFSET;
    }

}
