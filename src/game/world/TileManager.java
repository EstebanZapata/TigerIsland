package game.world;

import game.world.exceptions.NoHexAtLocationException;
import tile.Hex;
import tile.Location;
import tile.orientation.HexOrientation;
import tile.orientation.TileOrientation;

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
        int x = location.getxCoordinate();
        int y = location.getyCoordinate();

        insertHexIntoCoordinateSystemAtCoordinates(hex, x, y);
    }

    private void insertHexIntoCoordinateSystemAtCoordinates(Hex hex, int x, int y) {
        int arrayCoordinateX = getArrayCoordinateFromTrueCoordinate(x);
        int arrayCoordinateY = getArrayCoordinateFromTrueCoordinate(y);

        hexCoordinateSystem[arrayCoordinateX][arrayCoordinateY] = hex;
    }

    private int getArrayCoordinateFromTrueCoordinate(int trueCoordinate) {
        return trueCoordinate + ORIGIN_OFFSET;
    }

    public Hex getHexByLocation(Location location) throws NoHexAtLocationException {
        int x = location.getxCoordinate();
        int y = location.getyCoordinate();
        int z = location.getzCoordinate();

        return getHexByCoordinate(x, y, z);
    }

    public Hex getHexByCoordinate(int x, int y, int z) throws NoHexAtLocationException {

        int arrayXCoordinate = getArrayCoordinateFromTrueCoordinate(x);
        int arrayYCoordinate = getArrayCoordinateFromTrueCoordinate(y);

        Hex hex = hexCoordinateSystem[arrayXCoordinate][arrayYCoordinate];

        if (hexDoesNotExist(hex) || hexDoesNotMatchGivenHeight(hex, z)) {
            String errorMessage = String.format("No hex at location (%d,%d,%d)", x,y,z);
            throw new NoHexAtLocationException(errorMessage);
        }

        return hex;

    }

    private boolean hexDoesNotExist(Hex hex) {
        return hex == null;
    }

    private boolean hexDoesNotMatchGivenHeight(Hex hex, int z) {
        return hex.getLocation().getzCoordinate() != z;
    }





}
