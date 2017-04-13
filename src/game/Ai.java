/**
 * Created by thomasbaldwin on 4/11/17.
 */

package game;

import game.settlements.BuildAction;
import game.tile.Hex;
import game.tile.Location;
import game.tile.Tile;
import game.world.World;
import game.world.rules.exceptions.IllegalTilePlacementException;
import thread.message.GameActionMessage;

import static game.settlements.BuildAction.FOUNDED_SETTLEMENT;
import static game.settlements.BuildAction.UNABLE_TO_BUILD;
import static game.tile.Terrain.GRASSLANDS;
import static game.tile.orientation.TileOrientation.NORTHWEST_WEST;

public class Ai {
    public World world;
    public int moveNumber = 0;
    public Ai(World world){
        this.world = world;
    }

    public GameActionMessage chooseMove(String gameID, int moveNumber, String playerID, Tile tileToBePlaced) throws IllegalTilePlacementException {
        Hex hexToBePlacedNextTo = world.getLeftMostHex();
        int newTileXCoordinate = hexToBePlacedNextTo.getLocation().getxCoordinate() - 1;
        int newTileYCoordinate = hexToBePlacedNextTo.getLocation().getyCoordinate();
        Location locationOfNewTile = new Location(newTileXCoordinate, newTileYCoordinate, 0);

        int buildXCoordinate = newTileXCoordinate - 1;
        int buildYCoordinate = newTileYCoordinate;
        Location locationOnWhichToBuild = new Location(buildXCoordinate, buildYCoordinate, 0);
        world.insertTileIntoTileManager(tileToBePlaced,locationOfNewTile,NORTHWEST_WEST);

        BuildAction buildAction = null;

        if (moveNumber == 20) {
            buildAction = UNABLE_TO_BUILD;
        }
        else {
            buildAction = FOUNDED_SETTLEMENT;
        }

        return new GameActionMessage(gameID, moveNumber, playerID, tileToBePlaced, locationOfNewTile, NORTHWEST_WEST, buildAction, locationOnWhichToBuild, GRASSLANDS);



    }
}
