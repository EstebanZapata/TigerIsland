package UnitTests.game;

import game.world.*;
import game.world.rules.exceptions.*;
import org.junit.Before;
import org.junit.Test;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientation;

public class WorldTest {
    private World world;

    private Tile tile;
    private Tile tileTwo;
    private Tile tileThree;
    private Tile tileFour;
    private Tile tileFive;
    private Tile tileSix;

    @Before
    public void setupWorldAndTiles() {
        world = new World();
        tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        tileTwo = new Tile(Terrain.JUNGLE, Terrain.ROCKY);
        tileThree = new Tile(Terrain.LAKE, Terrain.ROCKY);
        tileFour = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);
        tileFive = new Tile(Terrain.ROCKY, Terrain.LAKE);
        tileSix = new Tile(Terrain.LAKE, Terrain.JUNGLE);

    }

    @Test(expected = SpecialFirstTileHasNotBeenPlacedException.class)
    public void testMustPlaceSpecialFirstTileFirst() throws IllegalTilePlacementException {
        world.attemptToInsertTileIntoTileManager(tile, new Location(1,0,0), TileOrientation.SOUTHWEST_SOUTHEAST);
    }

    @Test(expected = SpecialFirstTileHasAlreadyBeenPlacedExeption.class)
    public void testSpecialFirstTileCannotBePlacedAgain() throws IllegalTilePlacementException {
        world.placeFirstTile();
        world.placeFirstTile();
    }








}