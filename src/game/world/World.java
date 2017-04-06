package game.world;

import game.world.exceptions.*;
import tile.*;
import tile.orientation.HexOrientation;
import tile.orientation.TileOrientation;

import java.util.ArrayList;
import java.util.Arrays;


public class World {
    private TileManager tileManager;


    private boolean firstTileHasBeenPlaced = false;

    public World() {
        initializeTileManager();

    }

    private void initializeTileManager() {
        tileManager = new TileManager();

    }

    public void insertTileIntoWorld(Tile tile, Location locationOfVolcano, TileOrientation tileOrientation) throws TilePlacementException {
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

            tileManager.allHexesInWorld.add(volcanoHex);
            tileManager.allHexesInWorld.add(leftHex);
            tileManager.allHexesInWorld.add(rightHex);

            volcanoHex.setLocation(locationOfVolcano);
            leftHex.setLocation(locationOfLeftHex);
            rightHex.setLocation(locationOfRightHex);
        }

    }

    public Location getTentativeLeftHexLocation(Location locationOfVolcano, TileOrientation tileOrientation) {
        HexOrientation leftHexOrientation = CoordinateSystemHelper.getLeftHexOrientationFromTileOrientation(tileOrientation);

        Location locationOfLeftHex = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(locationOfVolcano, leftHexOrientation);

        return locationOfLeftHex;
    }

    public Location getTentativeRightHexLocation(Location locationOfVolcano, TileOrientation tileOrientation) {
        HexOrientation rightHexOrientation = CoordinateSystemHelper.getRightHexOrientationFromTileOrientation(tileOrientation);

        Location locationOfRightHex = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(locationOfVolcano, rightHexOrientation);

        return locationOfRightHex;
    }

    public boolean topVolcanoCoversOneBelow(Location locationOfVolcano) throws TilePlacementException {
        int xCoordinate = locationOfVolcano.getxCoordinate();
        int yCoordinate = locationOfVolcano.getyCoordinate();
        int zCoordinateToCheck = locationOfVolcano.getzCoordinate() - 1;
        if (tileManager.getHexByCoordinate(xCoordinate,yCoordinate,zCoordinateToCheck).getTerrain() != Terrain.VOLCANO) {
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

            adjecentHexLocations = CoordinateSystemHelper.getAdjacentHexLocationsToTile(locationOfHexes);

        }

        else {
            adjecentHexLocations = CoordinateSystemHelper.getAdjacentHexLocationsToTile(locationOfHexes);
        }

        for (int i = 0; i < 9; i++) {
            try {
                Hex hex = tileManager.getHexByLocation(adjecentHexLocations[i]);
                if (hex != null) {
                    return true;
                }
            } catch (NoHexAtLocationException e) {
                continue;
            }
        }
        throw new TileNotAdjacentToAnotherException("Tile being placed is not adjacent to an existing tile");


    }






    public boolean noAirBelowTile(Location[] locationOfTileHexes) throws AirBelowTileException {
        int zCoordinate = locationOfTileHexes[0].getzCoordinate();
        int zLayerToCheck = zCoordinate - 1;

        try {
            tileManager.getHexByCoordinate(locationOfTileHexes[0].getxCoordinate(), locationOfTileHexes[0].getyCoordinate(), zLayerToCheck);
            tileManager.getHexByCoordinate(locationOfTileHexes[1].getxCoordinate(), locationOfTileHexes[1].getyCoordinate(), zLayerToCheck);
            tileManager.getHexByCoordinate(locationOfTileHexes[2].getxCoordinate(), locationOfTileHexes[2].getyCoordinate(), zLayerToCheck);
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


        tileOne = tileManager.getHexByLocation(locationOneToCheck).getOwner();
        tileTwo = tileManager.getHexByLocation(locationTwoToCheck).getOwner();
        tileThree = tileManager.getHexByLocation(locationThreeToCheck).getOwner();



        if (tileOne == tileTwo && tileOne == tileThree) {
            throw new TileCompletelyOverlapsAnotherException("Tile completely overlaps another");
        }
        else {
            return true;
        }
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
            Hex hex = tileManager.getHexByLocation(location);
            if (hex != null) {
                return false;
            }
        }
        catch (NoHexAtLocationException e) {
            return true;
        }
        return true;
    }



    private void insertHexIntoCoordinateSystem(Hex hex, Location location) throws HexAlreadyAtLocationException {
        tileManager.insertHexIntoCoordinateSystemAtLocation(hex, location);
    }

    public void placeFirstTile(Tile tile, TileOrientation orientation) throws TilePlacementException {

        insertTileIntoWorld(tile, new Location(0,0,0), orientation);
        firstTileHasBeenPlaced = true;
    }
    public boolean getFirstTileHasBeenPlaced(){
        return firstTileHasBeenPlaced;
    }
    public ArrayList<Hex> getAllHexesInWorld() {
        return tileManager.allHexesInWorld;
    }

    public Hex getHexByCoordinate(int x, int y, int z) throws NoHexAtLocationException {
        return tileManager.getHexByCoordinate(x,y,z);
    }




}
