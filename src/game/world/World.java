package game.world;

import game.settlements.Settlement;
import game.world.rules.TileRulesManager;
import game.world.rules.exceptions.*;
import game.tile.*;
import game.tile.orientation.TileOrientation;

import java.util.ArrayList;


public class World {
    public TileManager tileManager;
    public TileRulesManager tileRulesManager;

    public World() {
        tileManager = new TileManager();
        tileRulesManager = new TileRulesManager(tileManager);

        tileManager.placeFirstTile();

    }

    public void insertTileIntoTileManager(Tile tile, Location locationOfVolcano, TileOrientation tileOrientation) throws IllegalTilePlacementException {

        Location locationOfLeftHex = CoordinateSystemHelper.getTentativeLeftHexLocation(locationOfVolcano, tileOrientation);
        Location locationOfRightHex = CoordinateSystemHelper.getTentativeRightHexLocation(locationOfVolcano, tileOrientation);

        Location[] locationOfTileHexes = new Location[Tile.MAX_HEXES_PER_TILE];
        locationOfTileHexes[0] = locationOfVolcano;
        locationOfTileHexes[1] = locationOfLeftHex;
        locationOfTileHexes[2] = locationOfRightHex;

        boolean ableToPlaceTile = tileRulesManager.ableToPlaceTileAtLocation(tile, locationOfTileHexes);

        if(ableToPlaceTile) {
            tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tile, locationOfTileHexes);
        }

    }

    public void placeFirstTile() throws IllegalTilePlacementException {
        if (tileRulesManager.ableToPlaceFirstTile()) {
            tileManager.placeFirstTile();
        }
    }



    public ArrayList<Hex> getAllHexesInWorld() {
        return tileManager.getAllHexesInWorld();
    }

    public Hex getHexByCoordinate(int x, int y, int z) throws NoHexAtLocationException {
        return tileManager.getHexByCoordinate(x,y,z);
    }

    public Hex getHexByLocation(Location locationOfHex) throws NoHexAtLocationException {
        return tileManager.getHexByLocation(locationOfHex);
    }

    public Hex getLeftMostHex() {
        return tileManager.getLeftMostHex();
    }

    public Hex getHexRegardlessOfHeight(int x, int y) throws NoHexAtLocationException {
        return tileManager.getHexRegardlessOfHeight(x, y);
    }

    public int getHeightOfHexByCoordinates(int x, int y) throws NoHexAtLocationException {
        return tileManager.getHeightOfHexByCoordinates(x, y);
    }

    public boolean ableToInsertTileIntoTileManager(Tile tile, Location locationOfVolcano, TileOrientation tileOrientation) {
        Location locationOfLeftHex = CoordinateSystemHelper.getTentativeLeftHexLocation(locationOfVolcano, tileOrientation);
        Location locationOfRightHex = CoordinateSystemHelper.getTentativeRightHexLocation(locationOfVolcano, tileOrientation);

        Location[] locationOfTileHexes = new Location[Tile.MAX_HEXES_PER_TILE];
        locationOfTileHexes[0] = locationOfVolcano;
        locationOfTileHexes[1] = locationOfLeftHex;
        locationOfTileHexes[2] = locationOfRightHex;

        boolean ableToPlaceTile;

        try {
            ableToPlaceTile = tileRulesManager.ableToPlaceTileAtLocation(tile, locationOfTileHexes);
        }
        catch (IllegalTilePlacementException e) {
            ableToPlaceTile = false;
        }

        return ableToPlaceTile;
    }

    public TileOrientation calculateTileOrientationToCoverVolcanoLocationAndAdjacentLocation(Location locationOfVolcano, Location locationOfAdjacent)
            throws NoValidTileOrientationException {
        Location locationOfUpperVolcano = Location.incrementZ(locationOfVolcano);

        Tile mockTile = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);

        for (TileOrientation tileOrientation:TileOrientation.values()) {
            if (ableToInsertTileIntoTileManager(mockTile, locationOfUpperVolcano, tileOrientation)) {
                Location mockLeftAdjacentLocation = CoordinateSystemHelper.getTentativeLeftHexLocation(locationOfVolcano, tileOrientation);
                Location mockRightAdjacentLocation = CoordinateSystemHelper.getTentativeRightHexLocation(locationOfVolcano, tileOrientation);

                if (mockLeftAdjacentLocation.equals(locationOfAdjacent) || mockRightAdjacentLocation.equals(locationOfAdjacent)) {
                    return tileOrientation;
                }
                else {
                    continue;
                }
            }

        }

        String errorMessage = "No valid way to place a tile on " + locationOfVolcano + " " + locationOfAdjacent;
        throw new NoValidTileOrientationException(errorMessage);

    }

    public TileOrientation calculateTileOrientationToCoverVolcanoLocationAndAdjacentLocationAndNOTAnotherLocation(Location locationOfVolcano, Location locationOfAdjacent, Location locationNotToCover)
            throws NoValidTileOrientationException {
        Location locationOfUpperVolcano = Location.incrementZ(locationOfVolcano);

        Tile mockTile = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);

        for (TileOrientation tileOrientation:TileOrientation.values()) {
            if (ableToInsertTileIntoTileManager(mockTile, locationOfUpperVolcano, tileOrientation)) {
                Location mockLeftAdjacentLocation = CoordinateSystemHelper.getTentativeLeftHexLocation(locationOfVolcano, tileOrientation);
                Location mockRightAdjacentLocation = CoordinateSystemHelper.getTentativeRightHexLocation(locationOfVolcano, tileOrientation);

                if (mockLeftAdjacentLocation.equals(locationOfAdjacent) && !mockRightAdjacentLocation.equals(locationNotToCover))
                    return tileOrientation;
                else if (!mockLeftAdjacentLocation.equals(locationNotToCover) && mockRightAdjacentLocation.equals(locationOfAdjacent))
                    return tileOrientation;
            }

        }

        String errorMessage = "No valid way to place a tile on " + locationOfVolcano + " " + locationOfAdjacent;
        throw new NoValidTileOrientationException(errorMessage);

    }

    public ArrayList<Hex> getHexesAdjacentToLocation(Location location) {
        ArrayList<Hex> adjacentHexes = new ArrayList<>();

        Location[] adjacentLocations = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(location);

        for (Location adjacentLocation:adjacentLocations) {
            try {
                Hex adjacentHex = tileManager.getHexByLocation(adjacentLocation);
                adjacentHexes.add(adjacentHex);
            }
            catch (NoHexAtLocationException e) {
                continue;
            }
        }

        return adjacentHexes;
    }

    public ArrayList<Hex> getAllHexesAdjacentToSettlement(Settlement settlementToCheck) {
        Location[] locationsAdjacentToSettlement = settlementToCheck.getAllHexLocationsAdjacentToSettlement();
        ArrayList<Hex> hexesAdjacentToSettlement = new ArrayList<>();
        for(Location locationToCheck : locationsAdjacentToSettlement) {
            int xCoordinateToCheck = locationToCheck.getxCoordinate();
            int yCoordinateToCheck = locationToCheck.getyCoordinate();
            try {
                hexesAdjacentToSettlement.add(this.getHexRegardlessOfHeight(xCoordinateToCheck,yCoordinateToCheck));
            } catch (NoHexAtLocationException e) {
                continue;
            }
        }
        return hexesAdjacentToSettlement;
    }
}
