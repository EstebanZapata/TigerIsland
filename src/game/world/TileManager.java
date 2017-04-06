package game.world;

import game.world.exceptions.NoHexAtLocationException;
import tile.Hex;
import tile.Location;

import java.util.ArrayList;

public class TileManager {
    public Hex[][] hexCoordinateSystem;
    public ArrayList<Hex> allHexesInWorld;


    private static final int SIZE_OF_BOARD = 200;
    private static final int ORIGIN_OFFSET = SIZE_OF_BOARD/2;


    public TileManager() {
        hexCoordinateSystem = new Hex[SIZE_OF_BOARD][SIZE_OF_BOARD];
        allHexesInWorld = new ArrayList<>();
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

    public Hex getHexByLocation(Location location) throws NoHexAtLocationException {
        return getHexByCoordinate(location.getxCoordinate(), location.getyCoordinate(), location.getzCoordinate());
    }

    public Hex getHexByCoordinate(int x, int y, int z) throws NoHexAtLocationException {
        try {
            int arrayXCoordinate = getArrayCoordinateFromTrueCoordinate(x);
            int arrayYCoordinate = getArrayCoordinateFromTrueCoordinate(y);
            Hex hex = hexCoordinateSystem[arrayXCoordinate][arrayYCoordinate];
            if (hex == null) {
                throw new NullPointerException();
            }
            if (hex.getLocation().getzCoordinate() != z) {
                throw new NullPointerException();
            }
            return hex;

        }
        catch (NullPointerException e) {
            String errorMessage = String.format("No hex at location (%d,%d,%d)", x,y,z);
            throw new NoHexAtLocationException(errorMessage);
        }
    }

}
