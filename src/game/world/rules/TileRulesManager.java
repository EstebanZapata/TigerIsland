package game.world.rules;

import game.world.TileManager;
import game.world.rules.exceptions.HexAlreadyAtLocationException;
import game.world.rules.exceptions.NoHexAtLocationException;
import game.world.rules.exceptions.TileCompletelyOverlapsAnotherException;
import game.world.rules.exceptions.TilePlacementException;
import tile.Hex;
import tile.Location;
import tile.Tile;

public class TileRulesManager {
    private TileManager tileManager;

    public TileRulesManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    //public boolean ableToPlaceTileAtLocation(Tile tile, Location locationOfVolcano, TileOrientation tileOrientation) {

    //}



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

            String errorMessage = "Hex already exists at location " + notEmptyLocation.toString();
            throw new HexAlreadyAtLocationException(errorMessage);
        }
    }

    private boolean hexLocationIsEmpty(Location location) {
        try {
            Hex hex = tileManager.getHexByLocation(location);
        }
        catch (NoHexAtLocationException e) {
            return true;
        }
        return false;
    }
}

