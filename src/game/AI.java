package game;

import tile.*;
import tile.orientation.TileOrientation;

/**
 * Created by Liam on 4/8/2017.
 */
public class AI {
    Game game;

    public AI(Game game) {
        this.game = game;
    }

    public String chooseMove() {
        Hex hexToBePlacedNextTo = game.world.tileManager.getLeftmostHexOnBoard();

        int newTileXCoordinate = hexToBePlacedNextTo.getLocation().getxCoordinate() - 1;
        int newTileYCoordinate = hexToBePlacedNextTo.getLocation().getyCoordinate();
        Location locationOfNewTile = new Location(newTileXCoordinate, newTileYCoordinate, 0);

        int buildXCoordinate = newTileXCoordinate - 1;
        int buildYCoordinate = newTileYCoordinate;
        Location locationOnWhichToBuild = new Location(buildXCoordinate, buildYCoordinate, 0);

        return "new GameCommandMessage('gameID', 'moveNumber', 'playerID', 'TilePlaced', locationOfNewTile, TileOrientation.NORTHWEST_WEST, BuildAction.FOUNDED_SETTLEMENT, locationOnWhichToBuild, Terrain.GRASSLANDS)";

    }

}
