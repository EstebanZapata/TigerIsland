package game.world;

import game.world.rules.TileRulesManager;
import game.world.rules.exceptions.*;
import game.tile.*;
import game.tile.orientation.TileOrientation;

import java.util.ArrayList;


public class World {
    private TileManager tileManager;
    public TileRulesManager tileRulesManager;

    public World() {
        tileManager = new TileManager();
        tileRulesManager = new TileRulesManager(tileManager);

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




}
