package game;

import tile.*;
import tile.orientation.HexOrientationRelativeToVolcano;
import tile.orientation.TileOrientationRelativeToVolcano;

import java.util.ArrayList;
import java.util.HashMap;

class World {
    private HashMap<Integer, HashMap<Integer, HashMap<Integer, Hex>>> hexCoordinateSystem;
    private ArrayList<Hex> allHexesInWorld;

    private static final int ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION = 0;
    private static final int ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION = 1;

    private static boolean firstTileHasBeenPlaced = false;

    World() {
        hexCoordinateSystem = new HashMap<>();
        allHexesInWorld = new ArrayList<>();
    }

    public void insertTileIntoWorld(Tile tile, Location locationOfVolcano, TileOrientationRelativeToVolcano tileOrientation)
            throws HexAlreadyAtLocationException
    {
        Location locationOfLeftHex;
        Location locationOfRightHex;

        HexOrientationRelativeToVolcano[] hexOrientations = getHexOrientationFromTileOrientation(tileOrientation);

        HexOrientationRelativeToVolcano orientationOfLeftHex = hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION];
        HexOrientationRelativeToVolcano orientationOfRightHex = hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION];

        locationOfLeftHex = getLocationRelativeToOrientationAndCenter(locationOfVolcano, orientationOfLeftHex);
        locationOfRightHex = getLocationRelativeToOrientationAndCenter(locationOfVolcano, orientationOfRightHex);

        boolean ableToPlaceTile = false;

        Location[] locationOfTileHexes = new Location[Tile.MAX_HEXES_PER_TILE];
        locationOfTileHexes[0] = locationOfVolcano;
        locationOfTileHexes[1] = locationOfLeftHex;
        locationOfTileHexes[2] = locationOfRightHex;

        if (locationOfVolcano.getzCoordinate() != 0) {
            ableToPlaceTile = noAirBelowTile(locationOfTileHexes) && tileDoesNotLieCompletelyOnAnother(locationOfTileHexes) &&  noHexesExistAtLocations(locationOfTileHexes);
        }
        else {
            ableToPlaceTile = (tileIsAdjacentToAnExistingTile(locationOfTileHexes) || !firstTileHasBeenPlaced) && noHexesExistAtLocations(locationOfTileHexes);
        }

        if(ableToPlaceTile) {
            Hex volcanoHex = tile.getVolcanoHex();
            Hex leftHex = tile.getLeftHexRelativeToVolcano();
            Hex rightHex = tile.getRightHexRelativeToVolcano();

            insertHexIntoWorld(volcanoHex, locationOfVolcano);
            insertHexIntoWorld(leftHex, locationOfLeftHex);
            insertHexIntoWorld(rightHex, locationOfRightHex);

            allHexesInWorld.add(volcanoHex);
            allHexesInWorld.add(leftHex);
            allHexesInWorld.add(rightHex);

            volcanoHex.setLocation(locationOfVolcano);
            leftHex.setLocation(locationOfLeftHex);
            rightHex.setLocation(locationOfRightHex);
        }

    }

    private boolean tileIsAdjacentToAnExistingTile(Location[] locationOfHexes) {
        //TODO: implement
        return true;
    }

    private boolean noAirBelowTile(Location[] locationOfTileHexes) {
        int zCoordinate = locationOfTileHexes[0].getzCoordinate();
        int zLayerToCheck = zCoordinate - 1;

        try {
            getHexByCoordinate(locationOfTileHexes[0].getxCoordinate(), locationOfTileHexes[0].getyCoordinate(), zLayerToCheck);
            getHexByCoordinate(locationOfTileHexes[1].getxCoordinate(), locationOfTileHexes[1].getyCoordinate(), zLayerToCheck);
            getHexByCoordinate(locationOfTileHexes[2].getxCoordinate(), locationOfTileHexes[2].getyCoordinate(), zLayerToCheck);
        }
        catch (NoHexAtLocationException e) {
            return false;
        }

        return true;
    }

    private boolean tileDoesNotLieCompletelyOnAnother(Location[] locationOfTileHexes) {

        Tile tileOne;
        Tile tileTwo;
        Tile tileThree;

        try {
            tileOne = getHexByLocation(locationOfTileHexes[0]).getOwner();
            tileTwo = getHexByLocation(locationOfTileHexes[1]).getOwner();
            tileThree = getHexByLocation(locationOfTileHexes[2]).getOwner();

        }
        catch (NoHexAtLocationException e) {
            return true;
        }

        if (tileOne == tileTwo && tileOne == tileThree) {
            return true;
        }
        else {
            return false;
        }
    }

    private HexOrientationRelativeToVolcano[] getHexOrientationFromTileOrientation(TileOrientationRelativeToVolcano tileOrientation) {
        HexOrientationRelativeToVolcano[] hexOrientations = new HexOrientationRelativeToVolcano[2];

        switch(tileOrientation) {
            case SOUTHWEST_SOUTHEAST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.SOUTHWEST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.SOUTHEAST;
                break;

            case WEST_SOUTHWEST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.WEST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.SOUTHWEST;
                break;

            case NORTHWEST_WEST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.NORTHWEST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.WEST;
                break;

            case NORTHEAST_NORTHWEST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.NORTHEAST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.NORTHWEST;
                break;

            case EAST_NORTHEAST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.EAST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.NORTHEAST;
                break;

            case SOUTHEAST_EAST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.SOUTHEAST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientationRelativeToVolcano.EAST;
                break;
        }

        return hexOrientations;
    }

    private Location getLocationRelativeToOrientationAndCenter(Location center, HexOrientationRelativeToVolcano orientation) {
        int xCoordinateOfCenter = center.getxCoordinate();
        int yCoordinateOfCenter = center.getyCoordinate();

        int xCoordinateOfHex = 0;
        int yCoordinateOfHex = 0;
        int zCoordinateOfHex = center.getzCoordinate();

        switch(orientation) {
            case SOUTHWEST:
                xCoordinateOfHex = xCoordinateOfCenter - 1;
                yCoordinateOfHex = yCoordinateOfCenter - 1;
                break;
            case WEST:
                xCoordinateOfHex = xCoordinateOfCenter - 1;
                yCoordinateOfHex = yCoordinateOfCenter;
                break;
            case NORTHWEST:
                xCoordinateOfHex = xCoordinateOfCenter;
                yCoordinateOfHex = yCoordinateOfCenter + 1;
                break;
            case NORTHEAST:
                xCoordinateOfHex = xCoordinateOfCenter + 1;
                yCoordinateOfHex = yCoordinateOfCenter + 1;
                break;
            case EAST:
                xCoordinateOfHex = xCoordinateOfCenter + 1;
                yCoordinateOfHex = yCoordinateOfCenter;
                break;
            case SOUTHEAST:
                xCoordinateOfHex = xCoordinateOfCenter;
                yCoordinateOfHex = yCoordinateOfCenter - 1;
                break;
        }

        return new Location(xCoordinateOfHex, yCoordinateOfHex, zCoordinateOfHex);

    }

    private boolean noHexesExistAtLocations(Location[] locationOfHexes) throws HexAlreadyAtLocationException {
        boolean ableToInsertTileIntoWorld = true;
        Location notEmptyLocation = null;

        if (!hexLocationIsEmpty(locationOfHexes[0])) {
            ableToInsertTileIntoWorld = false;
            notEmptyLocation = locationOfHexes[0];
        }

        if(!hexLocationIsEmpty(locationOfHexes[1])) {
            ableToInsertTileIntoWorld = false;
            notEmptyLocation = locationOfHexes[1];
        }

        if (!hexLocationIsEmpty(locationOfHexes[2])) {
            ableToInsertTileIntoWorld = false;
            notEmptyLocation = locationOfHexes[2];
        }

        if (ableToInsertTileIntoWorld) {
            return true;
        }
        else {
            String errorMessage = String.format("Hex already exists at location (%d,%d,%d)", notEmptyLocation.getxCoordinate(), notEmptyLocation.getyCoordinate(), notEmptyLocation.getzCoordinate());
            throw new HexAlreadyAtLocationException(errorMessage);
        }

    }

    private boolean hexLocationIsEmpty(Location location) {
        try {
            Hex hex = getHexByLocation(location);
            if (hex != null) {
                return false;
            }
        }
        catch (NoHexAtLocationException e) {
            return true;
        }
        return true;
    }

    private Hex getHexByLocation(Location location) throws NoHexAtLocationException {
        return getHexByCoordinate(location.getxCoordinate(), location.getyCoordinate(), location.getzCoordinate());
    }

    public Hex getHexByCoordinate(int x, int y, int z) throws NoHexAtLocationException {
        try {
            return hexCoordinateSystem.get(x).get(y).get(z);
        }
        catch (NullPointerException e) {
            String errorMessage = String.format("No hex at location (%d,%d,%d)", x,y,z);
            throw new NoHexAtLocationException(errorMessage);
        }
    }

    private void insertHexIntoWorld(Hex hex, Location location) throws HexAlreadyAtLocationException {
        int xCoordinate = location.getxCoordinate();
        int yCoordinate = location.getyCoordinate();
        int zCoordinate = location.getzCoordinate();

        hexCoordinateSystem.putIfAbsent(xCoordinate, new HashMap<Integer, HashMap<Integer, Hex>>());
        hexCoordinateSystem.get(xCoordinate).putIfAbsent(yCoordinate, new HashMap<Integer, Hex>());


        hexCoordinateSystem.get(xCoordinate).get(yCoordinate).put(zCoordinate, hex);

    }

    public void printAllHexesAndTheirInformation() {
        for (Hex hex:allHexesInWorld) {
            Location hexLocation = hex.getLocation();
            int xCoordinate = hexLocation.getxCoordinate();
            int yCoordinate = hexLocation.getyCoordinate();
            int zCoordinate = hexLocation.getzCoordinate();

            Terrain hexTerrain = hex.getTerrain();
            Tile hexOwner = hex.getOwner();

            System.out.println(String.format("Hex at (%d,%d,%d) with terrain %s and in tile %s" ,
                    xCoordinate, yCoordinate, zCoordinate, hexTerrain.toString(), hexOwner.toString()));
        }
    }

    public void placeFirstTile(Tile tile, TileOrientationRelativeToVolcano orientation) throws HexAlreadyAtLocationException {

        insertTileIntoWorld(tile, new Location(0,0,0), orientation);
        firstTileHasBeenPlaced = true;
    }
}
