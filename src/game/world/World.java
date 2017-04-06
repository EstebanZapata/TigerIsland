package game.world;

import game.world.exceptions.*;
import settlements.SettlementManager;
import tile.*;
import tile.orientation.HexOrientation;
import tile.orientation.TileOrientation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

//TODO: Enforce first tile placement and cannot use that method afterwards

public class World {
    private Hex[][] hexCoordinateSystem;
    private ArrayList<Hex> allHexesInWorld;
    private SettlementManager settlementManager;

    private static final int SIZE_OF_BOARD = 200;
    private static final int ORIGIN_OFFSET = SIZE_OF_BOARD/2;

    private static final int ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION = 0;
    private static final int ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION = 1;

    private boolean firstTileHasBeenPlaced = false;

    public World() {
        initializeCoordinateSystem();
        allHexesInWorld = new ArrayList<>();

    }

    private void initializeCoordinateSystem() {
        hexCoordinateSystem = new Hex[SIZE_OF_BOARD][SIZE_OF_BOARD];

    }

    public void foundSettlement() {

    }

    public void insertTileIntoWorld(Tile tile, Location locationOfVolcano, TileOrientation tileOrientation)
            throws TilePlacementException {
        Location locationOfLeftHex = getTentativeLeftHexLocation(locationOfVolcano, tileOrientation);
        Location locationOfRightHex = getTentativeRightHexLocation(locationOfVolcano, tileOrientation);


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

            insertHexIntoCoordinateSystem(volcanoHex, locationOfVolcano);
            insertHexIntoCoordinateSystem(leftHex, locationOfLeftHex);
            insertHexIntoCoordinateSystem(rightHex, locationOfRightHex);

            allHexesInWorld.add(volcanoHex);
            allHexesInWorld.add(leftHex);
            allHexesInWorld.add(rightHex);

            volcanoHex.setLocation(locationOfVolcano);
            leftHex.setLocation(locationOfLeftHex);
            rightHex.setLocation(locationOfRightHex);
        }

    }

    public boolean topVolcanoCoversOneBelow(Location locationOfVolcano) throws TilePlacementException {
        int xCoordinate = locationOfVolcano.getxCoordinate();
        int yCoordinate = locationOfVolcano.getyCoordinate();
        int zCoordinateToCheck = locationOfVolcano.getzCoordinate() - 1;
        if (getHexByCoordinate(xCoordinate,yCoordinate,zCoordinateToCheck).getTerrain() != Terrain.VOLCANO) {
            throw new TopVolcanoDoesNotCoverBottomVolcanoException(String.format("Hex at (%d,%d,%d) is not volcano", xCoordinate,yCoordinate,zCoordinateToCheck));
        }
        return true;
    }

    public boolean tileIsAdjacentToAnExistingTile(Location[] locationOfHexes, TileOrientation tileOrientation) throws TileNotAdjacentToAnotherException {
        int upArrowOrientation = 0;
        int downArrowOrientation = 1;

        int tileArrowOrientation;

        if(tileOrientation == TileOrientation.SOUTHWEST_SOUTHEAST
                || tileOrientation == TileOrientation.EAST_NORTHEAST
                || tileOrientation == TileOrientation.NORTHWEST_WEST) {
            tileArrowOrientation = upArrowOrientation;
        }
        else {
            tileArrowOrientation = downArrowOrientation;
        }

        Location[] adjecentHexLocations = new Location[9];

        ArrayList<Location> locationOfHexesList = new ArrayList<>(Arrays.asList(locationOfHexes));

        if (tileArrowOrientation == upArrowOrientation) {

            sortByLargestXAndLargestYAtEndOfList(locationOfHexesList);
            adjecentHexLocations = CoordinateSystem.getAdjacentHexLocationsToTile(locationOfHexes);

        }

        else {
            sortBySmallestXAndLargestYAtEndOfList(locationOfHexesList);
            adjecentHexLocations = CoordinateSystem.getAdjacentHexLocationsToTile(locationOfHexes);
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

    public boolean tileDoesNotLieCompletelyOnAnother(Location[] locationOfTileHexes) throws TilePlacementException {

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
            throw new TileCompletelyOverlapsAnotherException("Tile completely overlaps another");
        }
        else {
            return true;
        }
    }

    private HexOrientation[] getHexOrientationFromTileOrientation(TileOrientation tileOrientation) {
        HexOrientation[] hexOrientations = new HexOrientation[2];

        switch(tileOrientation) {
            case SOUTHWEST_SOUTHEAST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientation.SOUTHWEST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientation.SOUTHEAST;
                break;

            case WEST_SOUTHWEST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientation.WEST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientation.SOUTHWEST;
                break;

            case NORTHWEST_WEST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientation.NORTHWEST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientation.WEST;
                break;

            case NORTHEAST_NORTHWEST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientation.NORTHEAST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientation.NORTHWEST;
                break;

            case EAST_NORTHEAST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientation.EAST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientation.NORTHEAST;
                break;

            case SOUTHEAST_EAST:
                hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION] = HexOrientation.SOUTHEAST;
                hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION] = HexOrientation.EAST;
                break;
        }

        return hexOrientations;
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

    private void insertHexIntoCoordinateSystem(Hex hex, Location location) throws HexAlreadyAtLocationException {
        int arrayCoordinateX = getArrayCoordinateFromTrueCoordinate(location.getxCoordinate());
        int arrayCoordinateY = getArrayCoordinateFromTrueCoordinate(location.getyCoordinate());

        hexCoordinateSystem[arrayCoordinateX][arrayCoordinateY] = hex;

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

    public void placeFirstTile(Tile tile, TileOrientation orientation) throws TilePlacementException {

        insertTileIntoWorld(tile, new Location(0,0,0), orientation);
        firstTileHasBeenPlaced = true;
    }
    public boolean getFirstTileHasBeenPlaced(){
        return firstTileHasBeenPlaced;
    }
    public ArrayList<Hex> getAllHexesInWorld() {
        return this.allHexesInWorld;
    }

    private int getArrayCoordinateFromTrueCoordinate(int trueCoordinate) {
        return trueCoordinate + ORIGIN_OFFSET;
    }


    public Location getTentativeLeftHexLocation(Location locationOfVolcano, TileOrientation tileOrientation) {
        HexOrientation[] hexOrientations = getHexOrientationFromTileOrientation(tileOrientation);

        Location locationOfLeftHex = CoordinateSystem.getLocationRelativeToOrientationAndCenter(locationOfVolcano, hexOrientations[ARRAY_INDEX_OF_LEFT_HEX_ORIENTATION]);

        return locationOfLeftHex;
    }

    public Location getTentativeRightHexLocation(Location locationOfVolcano, TileOrientation tileOrientation) {
        HexOrientation[] hexOrientations = getHexOrientationFromTileOrientation(tileOrientation);

        Location locationOfRightHex = CoordinateSystem.getLocationRelativeToOrientationAndCenter(locationOfVolcano, hexOrientations[ARRAY_INDEX_OF_RIGHT_HEX_ORIENTATION]);

        return locationOfRightHex;
    }


}
