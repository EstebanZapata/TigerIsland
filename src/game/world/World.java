package game.world;

import game.world.rules.TileRulesManager;
import game.world.rules.exceptions.*;
import tile.*;
import tile.orientation.TileOrientation;

import java.util.ArrayList;


public class World {
    private TileManager tileManager;
    public TileRulesManager tileRulesManager;

    public boolean firstTileHasBeenPlaced = false;

    public World() {
        tileManager = new TileManager();
        tileRulesManager = new TileRulesManager(tileManager);
    }

    public void insertTileIntoWorld(Tile tile, Location locationOfVolcano, TileOrientation tileOrientation) throws IllegalTilePlacementException {
        Location locationOfLeftHex = CoordinateSystemHelper.getTentativeLeftHexLocation(locationOfVolcano, tileOrientation);
        Location locationOfRightHex = CoordinateSystemHelper.getTentativeRightHexLocation(locationOfVolcano, tileOrientation);

        Location[] locationOfTileHexes = new Location[Tile.MAX_HEXES_PER_TILE];
        locationOfTileHexes[0] = locationOfVolcano;
        locationOfTileHexes[1] = locationOfLeftHex;
        locationOfTileHexes[2] = locationOfRightHex;

        boolean ableToPlaceTile = tileRulesManager.ableToPlaceTileAtLocation(tile, locationOfTileHexes, firstTileHasBeenPlaced);

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

    private void insertHexIntoCoordinateSystem(Hex hex, Location location) throws HexAlreadyAtLocationException {
        tileManager.insertHexIntoCoordinateSystemAtLocation(hex, location);
    }

    public void placeFirstTile(Tile tile, TileOrientation orientation) throws IllegalTilePlacementException {

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
