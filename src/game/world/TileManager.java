package game.world;

import game.world.rules.exceptions.IllegalTilePlacementException;
import game.world.rules.exceptions.NoHexAtLocationException;
import game.world.rules.exceptions.SpecialFirstTileHasNotBeenPlacedException;
import tile.FirstTile;
import tile.Hex;
import tile.Location;
import tile.Tile;
import tile.orientation.TileOrientation;

import java.util.ArrayList;

public class TileManager {
    public Hex[][] hexCoordinateSystem;
    private ArrayList<Hex> allHexesInWorld;

    private static final int SIZE_OF_BOARD = 200;
    private static final int ORIGIN_OFFSET = SIZE_OF_BOARD/2;

    private boolean firstTileHasBeenPlaced;

    public TileManager() {
        hexCoordinateSystem = new Hex[SIZE_OF_BOARD][SIZE_OF_BOARD];
        allHexesInWorld = new ArrayList<>();

        firstTileHasBeenPlaced = false;
    }

    public void insertTileIntoCoordinateSystemAndAddHexesToList(Tile tile, Location[] locationsOfTileHexes) {

        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHex = tile.getRightHexRelativeToVolcano();

        insertHexIntoCoordinateSystemAtLocation(volcanoHex, locationsOfTileHexes[0]);
        insertHexIntoCoordinateSystemAtLocation(leftHex, locationsOfTileHexes[1]);
        insertHexIntoCoordinateSystemAtLocation(rightHex, locationsOfTileHexes[2]);

        allHexesInWorld.add(volcanoHex);
        allHexesInWorld.add(leftHex);
        allHexesInWorld.add(rightHex);

        volcanoHex.setLocation(locationsOfTileHexes[0]);
        leftHex.setLocation(locationsOfTileHexes[1]);
        rightHex.setLocation(locationsOfTileHexes[2]);
    }

    private void insertHexIntoCoordinateSystemAtLocation(Hex hex, Location location) {
        int x = location.getxCoordinate();
        int y = location.getyCoordinate();

        insertHexIntoCoordinateSystemAtCoordinates(hex, x, y);
    }

    private void insertHexIntoCoordinateSystemAtCoordinates(Hex hex, int x, int y) {
        int arrayCoordinateX = getArrayCoordinateFromTrueCoordinate(x);
        int arrayCoordinateY = getArrayCoordinateFromTrueCoordinate(y);

        hexCoordinateSystem[arrayCoordinateX][arrayCoordinateY] = hex;
    }

    public void placeFirstTile()  {
        Location startingLocationOfVolcanoHex = new Location(0,0, 0);
        Location startingLocationOfJungleHex = new Location(0,1, 0);
        Location startingLocationOfLakeHex = new Location(1,1, 0);
        Location startingLocationOfGrasslandsHex = new Location(0,-1, 0);
        Location startingLocationOfRockyHex = new Location(-1,-1, 0);

        FirstTile firstTile = new FirstTile();

        Hex volcano = firstTile.getVolcanoHex();
        Hex jungle = firstTile.getJungleHex();
        Hex lake = firstTile.getLakeHex();
        Hex grasslands = firstTile.getGrasslandsHex();
        Hex rocky = firstTile.getRockyHex();

        insertHexIntoCoordinateSystemAtLocation(volcano, startingLocationOfVolcanoHex);
        insertHexIntoCoordinateSystemAtLocation(jungle, startingLocationOfJungleHex);
        insertHexIntoCoordinateSystemAtLocation(lake, startingLocationOfLakeHex);
        insertHexIntoCoordinateSystemAtLocation(grasslands, startingLocationOfGrasslandsHex);
        insertHexIntoCoordinateSystemAtLocation(rocky, startingLocationOfRockyHex);

        volcano.setLocation(startingLocationOfVolcanoHex);
        jungle.setLocation(startingLocationOfJungleHex);
        lake.setLocation(startingLocationOfLakeHex);
        grasslands.setLocation(startingLocationOfGrasslandsHex);
        rocky.setLocation(startingLocationOfRockyHex);

        allHexesInWorld.add(volcano);
        allHexesInWorld.add(jungle);
        allHexesInWorld.add(lake);
        allHexesInWorld.add(grasslands);
        allHexesInWorld.add(rocky);

        firstTileHasBeenPlaced = true;
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


    public boolean getFirstTileHasBeenPlaced() {
        return firstTileHasBeenPlaced;
    }

    public ArrayList<Hex> getAllHexesInWorld() {
        return this.allHexesInWorld;
    }


}
