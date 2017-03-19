package game;

import tile.*;
import tile.orientation.HexOrientation;
import tile.orientation.TileOrientation;

import java.util.HashMap;

class World {
    private HashMap<Integer, HashMap<Integer, HashMap<Integer, Hex>>> hexCoordinateSystem;

    World() {
        hexCoordinateSystem = new HashMap<>();
    }

    public void insertTileIntoWorld(Tile tile, Location locationOfVolcano, TileOrientation orientation) throws HexAlreadyAtLocationException{
        Location locationOfLeftHex;
        Location locationOfRightHex;

        HexOrientation orientationOfLeftHex = null;
        HexOrientation orientationOfRightHex = null;

        switch(orientation) {
            case SOUTHWEST_SOUTHEAST:
                orientationOfLeftHex = HexOrientation.SOUTHWEST;
                orientationOfRightHex = HexOrientation.SOUTHEAST;
                break;

            case WEST_SOUTHWEST:
                orientationOfLeftHex = HexOrientation.WEST;
                orientationOfRightHex = HexOrientation.SOUTHWEST;
                break;

            case NORTHWEST_WEST:
                orientationOfLeftHex = HexOrientation.NORTHWEST;
                orientationOfRightHex = HexOrientation.WEST;
                break;

            case NORTHEAST_NORTHWEST:
                orientationOfLeftHex = HexOrientation.NORTHEAST;
                orientationOfRightHex = HexOrientation.NORTHWEST;
                break;

            case EAST_NORTHEAST:
                orientationOfLeftHex = HexOrientation.EAST;
                orientationOfRightHex = HexOrientation.NORTHEAST;
                break;

            case SOUTHEAST_EAST:
                orientationOfLeftHex = HexOrientation.SOUTHEAST;
                orientationOfRightHex = HexOrientation.EAST;
                break;
        }

        locationOfLeftHex = getLocationRelativeToOrientationAndCenter(locationOfVolcano, orientationOfLeftHex);
        locationOfRightHex = getLocationRelativeToOrientationAndCenter(locationOfVolcano, orientationOfRightHex);

        if(ableToInsertTileIntoWorldOnLayerZero(locationOfVolcano, locationOfLeftHex, locationOfRightHex)) {
            insertHexIntoWorld(tile.getVolcanoHex(), locationOfVolcano);
            insertHexIntoWorld(tile.getLeftHexRelativeToVolcano(), locationOfLeftHex);
            insertHexIntoWorld(tile.getRightHexRelativeToVolcano(), locationOfRightHex);
        }

    }

    private Location getLocationRelativeToOrientationAndCenter(Location center, HexOrientation orientation) {
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

    private boolean ableToInsertTileIntoWorldOnLayerZero(Location locationOne, Location locationTwo, Location locationThree) throws HexAlreadyAtLocationException {
        boolean ableToInsertTileIntoWorld = true;
        Location notEmptyLocation = null;

        if (!hexLocationIsEmpty(locationOne)) {
            ableToInsertTileIntoWorld = false;
            notEmptyLocation = locationOne;
        }

        if(!hexLocationIsEmpty(locationTwo)) {
            ableToInsertTileIntoWorld = false;
            notEmptyLocation = locationTwo;
        }

        if (!hexLocationIsEmpty(locationThree)) {
            ableToInsertTileIntoWorld = false;
            notEmptyLocation = locationThree;
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
}
