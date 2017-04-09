package io;

import game.settlements.BuildAction;
import game.tile.Location;
import game.tile.Terrain;
import game.tile.Tile;
import game.tile.orientation.TileOrientation;
import org.junit.Assert;
import org.junit.Test;
import thread.message.GameActionMessage;

public class ClientToServerParserTest {
    @Test
    public void testClientToServerCoordinatesFromOrigin() {
        String serverCoordinates = ClientToServerParser.convertClientCoordinatesToServerCoordinates(new Location(0,0,0));

        Assert.assertEquals("0 0 0", serverCoordinates);
    }

    @Test
    public void testClientToServerCoordinatesFromArbitraryPoint() {
        String serverCoordinates = ClientToServerParser.convertClientCoordinatesToServerCoordinates(new Location(-2,-1,1));

        Assert.assertEquals("-2 1 1", serverCoordinates);
    }

    @Test
    public void testClientToServerCoordinatesFromAnotherArbitrary() {
        String serverCoordinates = ClientToServerParser.convertClientCoordinatesToServerCoordinates(new Location(3,-1,5));

        Assert.assertEquals("3 -4 1", serverCoordinates);
    }

    @Test
    public void testGameActionToServer() {
        GameActionMessage gameActionMessage =
                new GameActionMessage("1", 4, "spag", new Tile(Terrain.GRASSLANDS, Terrain.ROCKY),
                        new Location(1,2,3), TileOrientation.NORTHWEST_WEST, BuildAction.BUILT_TIGER_PLAYGROUND,
                        new Location(-2,1,0), null);

        String serverString = ClientToServerParser.getStringFromGameActionMessage(gameActionMessage);

        Assert.assertEquals("GAME 1 MOVE 4 PLACE ROCK+GRASS AT 1 1 -2 6 BUILD TIGER PLAYGROUND AT -2 3 -1", serverString);
    }
}
