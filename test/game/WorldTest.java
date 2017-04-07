package game;

import game.world.*;
import game.world.rules.exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Hex;
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

    @Test
    public void testPlaceTileWithSouthwestSoutheastOrientation() throws IllegalTilePlacementException {
        world.placeFirstTile();

        world.attemptToInsertTileIntoTileManager(tile, new Location(2,1,0), TileOrientation.SOUTHWEST_SOUTHEAST);

        Assert.assertEquals(tile.getVolcanoHex(), world.getHexByCoordinate(2,1,0));
        Assert.assertEquals(tile.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(1,0,0));
        Assert.assertEquals(tile.getRightHexRelativeToVolcano(), world.getHexByCoordinate(2,0,0));
    }

    @Test
    public void testPlaceTileWithWestSouthwestOrientation() throws IllegalTilePlacementException {
        world.placeFirstTile();

        world.attemptToInsertTileIntoTileManager(tile, new Location(-2,-2,0), TileOrientation.WEST_SOUTHWEST);

        Assert.assertEquals(tile.getVolcanoHex(), world.getHexByCoordinate(-2,-2,0));
        Assert.assertEquals(tile.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(-3,-2,0));
        Assert.assertEquals(tile.getRightHexRelativeToVolcano(), world.getHexByCoordinate(-3,-3,0));
    }

    @Test
    public void testPlaceTileWithNorthwestWestOrientation() throws IllegalTilePlacementException {
        world.placeFirstTile();

        world.attemptToInsertTileIntoTileManager(tile, new Location(1,2,0), TileOrientation.NORTHWEST_WEST);

        Assert.assertEquals(tile.getVolcanoHex(), world.getHexByCoordinate(1,2,0));
        Assert.assertEquals(tile.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(1,3,0));
        Assert.assertEquals(tile.getRightHexRelativeToVolcano(), world.getHexByCoordinate(0,2,0));
    }

    @Test
    public void testPlaceTileWithNortheastNorthwestOrientation() throws IllegalTilePlacementException {
        world.placeFirstTile();

        world.attemptToInsertTileIntoTileManager(tile, new Location(2,0,0), TileOrientation.NORTHEAST_NORTHWEST);

        Assert.assertEquals(tile.getVolcanoHex(), world.getHexByCoordinate(2,0,0));
        Assert.assertEquals(tile.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(3,1,0));
        Assert.assertEquals(tile.getRightHexRelativeToVolcano(), world.getHexByCoordinate(2,1,0));
    }

    @Test
    public void testPlaceTileWithEastNortheastOrientation() throws IllegalTilePlacementException {
        world.placeFirstTile();

        world.attemptToInsertTileIntoTileManager(tile, new Location(2,2,0), TileOrientation.EAST_NORTHEAST);

        Assert.assertEquals(tile.getVolcanoHex(), world.getHexByCoordinate(2,2,0));
        Assert.assertEquals(tile.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(3,2,0));
        Assert.assertEquals(tile.getRightHexRelativeToVolcano(), world.getHexByCoordinate(3,3,0));
    }

    @Test
    public void testPlaceTileWithSoutheastEastOrientation() throws IllegalTilePlacementException {
        world.placeFirstTile();

        world.attemptToInsertTileIntoTileManager(tile, new Location(-2,1,0), TileOrientation.SOUTHEAST_EAST);

        Assert.assertEquals(tile.getVolcanoHex(), world.getHexByCoordinate(-2,1,0));
        Assert.assertEquals(tile.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(-2,0,0));
        Assert.assertEquals(tile.getRightHexRelativeToVolcano(), world.getHexByCoordinate(-1,1,0));
    }






}