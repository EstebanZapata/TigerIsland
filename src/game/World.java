package game;

import tile.*;
import tile.orientation.HexOrientationRelativeToVolcano;
import tile.orientation.TileOrientationRelativeToVolcano;

import java.util.ArrayList;
import java.util.HashMap;

class World {
    private HashMap<Integer, HashMap<Integer, HashMap<Integer, Hex>>> hexCoordinateSystem;
    private ArrayList<Hex> allHexesInWorld;

    World() {
        hexCoordinateSystem = new HashMap<>();
        allHexesInWorld = new ArrayList<>();
    }

    public void insertTileIntoWorld(Tile tile, Location locationOfVolcano, TileOrientationRelativeToVolcano orientation)
            throws HexAlreadyAtLocationException
    {
        Location locationOfLeftHex;
        Location locationOfRightHex;

        HexOrientationRelativeToVolcano orientationOfLeftHex = null;
        HexOrientationRelativeToVolcano orientationOfRightHex = null;

        switch(orientation) {
            case SOUTHWEST_SOUTHEAST:
                orientationOfLeftHex = HexOrientationRelativeToVolcano.SOUTHWEST;
                orientationOfRightHex = HexOrientationRelativeToVolcano.SOUTHEAST;
                break;

            case WEST_SOUTHWEST:
                orientationOfLeftHex = HexOrientationRelativeToVolcano.WEST;
                orientationOfRightHex = HexOrientationRelativeToVolcano.SOUTHWEST;
                break;

            case NORTHWEST_WEST:
                orientationOfLeftHex = HexOrientationRelativeToVolcano.NORTHWEST;
                orientationOfRightHex = HexOrientationRelativeToVolcano.WEST;
                break;

            case NORTHEAST_NORTHWEST:
                orientationOfLeftHex = HexOrientationRelativeToVolcano.NORTHEAST;
                orientationOfRightHex = HexOrientationRelativeToVolcano.NORTHWEST;
                break;

            case EAST_NORTHEAST:
                orientationOfLeftHex = HexOrientationRelativeToVolcano.EAST;
                orientationOfRightHex = HexOrientationRelativeToVolcano.NORTHEAST;
                break;

            case SOUTHEAST_EAST:
                orientationOfLeftHex = HexOrientationRelativeToVolcano.SOUTHEAST;
                orientationOfRightHex = HexOrientationRelativeToVolcano.EAST;
                break;
        }

        locationOfLeftHex = getLocationRelativeToOrientationAndCenter(locationOfVolcano, orientationOfLeftHex);
        locationOfRightHex = getLocationRelativeToOrientationAndCenter(locationOfVolcano, orientationOfRightHex);

        if(ableToInsertTileIntoWorldOnLayerZero(locationOfVolcano, locationOfLeftHex, locationOfRightHex)) {
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

    public void printAllHexLocationsInWorld() {
        for (Hex hex:allHexesInWorld) {
            Location hexLocation = hex.getLocation();
            int xCoordinate = hexLocation.getxCoordinate();
            int yCoordinate = hexLocation.getyCoordinate();
            int zCoordinate = hexLocation.getzCoordinate();

            System.out.println(String.format("Hex at (%d,%d,%d)" , xCoordinate, yCoordinate, zCoordinate));
        }
    }

    public void placeFirstTile(Tile tile, TileOrientationRelativeToVolcano orientation) throws HexAlreadyAtLocationException {
        insertTileIntoWorld(tile, new Location(0,0,0), orientation);
    }
}
