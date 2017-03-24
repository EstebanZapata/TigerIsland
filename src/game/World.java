package game;

import tile.*;
import tile.orientation.HexOrientationRelativeToVolcano;
import tile.orientation.TileOrientationRelativeToVolcano;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import static java.util.Arrays.asList;

//TODO: Enforce first tile placement and cannot use that method afterwards

public class World {
    private HashMap<Integer, HashMap<Integer, HashMap<Integer, Hex>>> hexCoordinateSystem;
    private ArrayList<Hex> allHexesInWorld;

    private static final int ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION = 0;
    private static final int ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION = 1;

    private boolean firstTileHasBeenPlaced = false;

    public World() {
        hexCoordinateSystem = new HashMap<>();
        allHexesInWorld = new ArrayList<>();
    }

    public void insertTileIntoWorld(Tile tile, Location locationOfVolcano, TileOrientationRelativeToVolcano tileOrientation)
            throws HexAlreadyAtLocationException, AirBelowTileException, NoHexAtLocationException, TopVolcanoDoesNotCoverBottomVolcanoException, TileNotAdjacentToAnotherException {
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
            ableToPlaceTile = noAirBelowTile(locationOfTileHexes) && topVolcanoCoversOneBelow(locationOfVolcano) && tileDoesNotLieCompletelyOnAnother(locationOfTileHexes) &&  noHexesExistAtLocations(locationOfTileHexes);
        }
        else {
            ableToPlaceTile = (!firstTileHasBeenPlaced || tileIsAdjacentToAnExistingTile(locationOfTileHexes, tileOrientation)) && noHexesExistAtLocations(locationOfTileHexes);
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

    public boolean topVolcanoCoversOneBelow(Location locationOfVolcano) throws TopVolcanoDoesNotCoverBottomVolcanoException, NoHexAtLocationException {
        int xCoordinate = locationOfVolcano.getxCoordinate();
        int yCoordinate = locationOfVolcano.getyCoordinate();
        int zCoordinateToCheck = locationOfVolcano.getzCoordinate() - 1;
        if (getHexByCoordinate(xCoordinate,yCoordinate,zCoordinateToCheck).getTerrain() != Terrain.VOLCANO) {
            throw new TopVolcanoDoesNotCoverBottomVolcanoException(String.format("Hex at (%d,%d,%d) is not volcano", xCoordinate,yCoordinate,zCoordinateToCheck));
        }
        return true;
    }

    public boolean tileIsAdjacentToAnExistingTile(Location[] locationOfHexes, TileOrientationRelativeToVolcano tileOrientation) throws TileNotAdjacentToAnotherException {
        int upArrowOrientation = 0;
        int downArrowOrientation = 1;

        int tileArrowOrientation;

        if(tileOrientation == TileOrientationRelativeToVolcano.SOUTHWEST_SOUTHEAST
                || tileOrientation == TileOrientationRelativeToVolcano.EAST_NORTHEAST
                || tileOrientation == TileOrientationRelativeToVolcano.NORTHWEST_WEST) {
            tileArrowOrientation = upArrowOrientation;
        }
        else {
            tileArrowOrientation = downArrowOrientation;
        }

        Location[] adjecentHexLocations = new Location[9];

        ArrayList<Location> locationOfHexesList = new ArrayList<>(Arrays.asList(locationOfHexes));

        if (tileArrowOrientation == upArrowOrientation) {

            sortByLargestXAndLargestYAtEndOfList(locationOfHexesList);
            adjecentHexLocations = getAdjacentHexesUpArrowOrientation(locationOfHexesList.get(2));

        }

        else {
            sortBySmallestXAndLargestYAtEndOfList(locationOfHexesList);
            adjecentHexLocations = getAdjacentHexesDownArrowOrientation(locationOfHexesList.get(2));
        }

        for (int i = 0; i < 9; i++) {
            try {
                Hex hex = getHexByLocation(adjecentHexLocations[i]);
                if (hex != null) {
                    return true;
                }
            } catch (NoHexAtLocationException e) {
                continue;
            }
        }
        throw new TileNotAdjacentToAnotherException("Tile being placed is not adjacent to an existing tile");


    }

    private Location[] getAdjacentHexesDownArrowOrientation(Location topLeftLocation) {
        int topX = topLeftLocation.getxCoordinate();
        int topY = topLeftLocation.getyCoordinate();
        int topZ = topLeftLocation.getzCoordinate();

        Location[] locationsToCheck = new Location[9];

        locationsToCheck[0] = new Location(topX-1,topY,topZ);
        locationsToCheck[1] = new Location(topX-1, topY-1,topZ);
        locationsToCheck[2] = new Location(topX-1,topY-2,topZ);
        locationsToCheck[3] = new Location(topX, topY-2, topZ);
        locationsToCheck[4] = new Location(topX+1, topY-1, topZ);
        locationsToCheck[5] = new Location(topX+2, topY,topZ);
        locationsToCheck[6] = new Location(topX+2,topY+1,topZ);
        locationsToCheck[7] = new Location(topX+1, topY+1, topZ);
        locationsToCheck[8] = new Location(topX, topY+1, topZ);

        return locationsToCheck;
    }

    private Location[] getAdjacentHexesUpArrowOrientation(Location topHexLocation) {
        int topX = topHexLocation.getxCoordinate();
        int topY = topHexLocation.getyCoordinate();
        int topZ = topHexLocation.getzCoordinate();

        Location[] locationsToCheck = new Location[9];

        locationsToCheck[0] = new Location(topX, topY + 1, topZ);
        locationsToCheck[1] = new Location(topX - 1, topY, topZ);
        locationsToCheck[2] = new Location(topX-2, topY - 1, topZ);
        locationsToCheck[3] = new Location(topX-2, topY-2, topZ);
        locationsToCheck[4] = new Location(topX-1, topY-2, topZ);
        locationsToCheck[5] = new Location(topX, topY-2, topZ);
        locationsToCheck[6] = new Location(topX+1, topY-1, topZ);
        locationsToCheck[7] = new Location(topX + 1, topY, topZ);
        locationsToCheck[8] = new Location(topX + 1, topY + 1, topZ);

        return locationsToCheck;
    }

    private void sortBySmallestXAndLargestYAtEndOfList(ArrayList<Location> locationOfHexesList) {
        locationOfHexesList.sort(new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                int o1XCoordinate = o1.getxCoordinate();
                int o2XCoordinate = o2.getxCoordinate();

                int o1YCoordinate = o1.getyCoordinate();
                int o2YCoordinate = o2.getyCoordinate();

                if (o1XCoordinate < o2XCoordinate && o1YCoordinate > o2YCoordinate) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        });
    }

    private void sortByLargestXAndLargestYAtEndOfList(ArrayList<Location> locationOfHexesList) {
        locationOfHexesList.sort(new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                int o1XCoordinate = o1.getxCoordinate();
                int o2XCoordinate = o2.getxCoordinate();

                int o1YCoordinate = o1.getyCoordinate();
                int o2YCoordinate = o2.getyCoordinate();

                if (o1XCoordinate > o2XCoordinate && o1YCoordinate > o2YCoordinate) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        });
    }


    public boolean noAirBelowTile(Location[] locationOfTileHexes) throws AirBelowTileException {
        int zCoordinate = locationOfTileHexes[0].getzCoordinate();
        int zLayerToCheck = zCoordinate - 1;

        try {
            getHexByCoordinate(locationOfTileHexes[0].getxCoordinate(), locationOfTileHexes[0].getyCoordinate(), zLayerToCheck);
            getHexByCoordinate(locationOfTileHexes[1].getxCoordinate(), locationOfTileHexes[1].getyCoordinate(), zLayerToCheck);
            getHexByCoordinate(locationOfTileHexes[2].getxCoordinate(), locationOfTileHexes[2].getyCoordinate(), zLayerToCheck);
        }
        catch (NoHexAtLocationException e) {
            throw new AirBelowTileException("Air below tile");
        }

        return true;
    }

    public boolean tileDoesNotLieCompletelyOnAnother(Location[] locationOfTileHexes) throws NoHexAtLocationException {

        Tile tileOne;
        Tile tileTwo;
        Tile tileThree;

        int zCoordinateToCheck = locationOfTileHexes[0].getzCoordinate() - 1;

        Location locationOne = locationOfTileHexes[0];
        Location locationTwo = locationOfTileHexes[1];
        Location locationThree = locationOfTileHexes[2];

        Location locationOneToCheck = new Location(locationOne.getxCoordinate(), locationOne.getyCoordinate(), zCoordinateToCheck);
        Location locationTwoToCheck = new Location(locationTwo.getxCoordinate(), locationTwo.getyCoordinate(), zCoordinateToCheck);
        Location locationThreeToCheck = new Location(locationThree.getxCoordinate(), locationThree.getyCoordinate(), zCoordinateToCheck);


        tileOne = getHexByLocation(locationOneToCheck).getOwner();
        tileTwo = getHexByLocation(locationTwoToCheck).getOwner();
        tileThree = getHexByLocation(locationThreeToCheck).getOwner();



        if (tileOne == tileTwo && tileOne == tileThree) {
            return false;
        }
        else {
            return true;
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

    public boolean noHexesExistAtLocations(Location[] locationOfHexes) throws HexAlreadyAtLocationException {
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

    public void placeFirstTile(Tile tile, TileOrientationRelativeToVolcano orientation) throws HexAlreadyAtLocationException, AirBelowTileException, NoHexAtLocationException, TopVolcanoDoesNotCoverBottomVolcanoException, TileNotAdjacentToAnotherException {

        insertTileIntoWorld(tile, new Location(0,0,0), orientation);
        firstTileHasBeenPlaced = true;
    }
    public boolean getFirstTileHasBeenPlaced(){
        return firstTileHasBeenPlaced;
    }
    public ArrayList<Hex> getAllHexesInWorld() {
        return this.allHexesInWorld;
    }
}
