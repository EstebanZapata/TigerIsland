package game.world.rules;

import game.world.TileManager;
import game.world.rules.exceptions.HexAlreadyAtLocationException;
import game.world.rules.exceptions.NoHexAtLocationException;
import tile.Hex;
import tile.Location;
import tile.Tile;
import tile.orientation.TileOrientation;

public class TileRulesManager {
    private TileManager tileManager;

    public TileRulesManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    //public boolean ableToPlaceTileAtLocation(Tile tile, Location locationOfVolcano, TileOrientation tileOrientation) {

    //}

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

