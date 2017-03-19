package game;

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

    @Before
    public void setupWorldAndTile() {
        world = new World();
        tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);

    }

    @Test(expected = NoHexAtLocationException.class)
    public void testHexFromCoordinateWithoutHexThrowsException() throws NoHexAtLocationException {
        world.getHexByCoordinate(0, 1, 2);
    }

    // TODO: Test each orientation
    @Test
    public void testAfterTileInsertGetHexesByCoordinate() throws HexAlreadyAtLocationException, NoHexAtLocationException {
        Location locationOfVolcano = new Location(1,2,0);
        world.insertTileIntoWorld(tile, locationOfVolcano, TileOrientation.SOUTHWEST_SOUTHEAST);

        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHexightHex = tile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(1,2,0));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(0,1,0));
        Assert.assertEquals(rightHexightHex, world.getHexByCoordinate(1,1,0));
    }

    @Test(expected = HexAlreadyAtLocationException.class)
    public void testPlacingTileOverlappingAnotherTileThrowsException() throws HexAlreadyAtLocationException {
        world.insertTileIntoWorld(tile, new Location(1,2,0), TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoWorld(new Tile(Terrain.JUNGLE, Terrain.ROCKY), new Location(2,4,0), TileOrientation.SOUTHWEST_SOUTHEAST);

    }
}