package game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Hex;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientationRelativeToVolcano;

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
    public void testAfterTileWithOrientationSouthwestSoutheastInsertGetHexesByCoordinate() throws HexAlreadyAtLocationException, NoHexAtLocationException {
        Location locationOfVolcano = new Location(1,2,0);
        world.insertTileIntoWorld(tile, locationOfVolcano, TileOrientationRelativeToVolcano.SOUTHWEST_SOUTHEAST);

        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHexightHex = tile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(1,2,0));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(0,1,0));
        Assert.assertEquals(rightHexightHex, world.getHexByCoordinate(1,1,0));
    }

    @Test
    public void testAfterTileWithOrientationEastNortheastInsertGetHexesByCoordinate() throws HexAlreadyAtLocationException, NoHexAtLocationException {
        Location locationOfVolcano = new Location(3,4,0);
        world.insertTileIntoWorld(tile, locationOfVolcano, TileOrientationRelativeToVolcano.EAST_NORTHEAST);

        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHex = tile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(3,4,0));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(4,4,0));
        Assert.assertEquals(rightHex, world.getHexByCoordinate(4,5,0));
    }

    @Test(expected = HexAlreadyAtLocationException.class)
    public void testPlacingTileOverlappingAnotherTileThrowsException() throws HexAlreadyAtLocationException {
        world.insertTileIntoWorld(tile, new Location(1,2,0), TileOrientationRelativeToVolcano.EAST_NORTHEAST);
        world.insertTileIntoWorld(new Tile(Terrain.JUNGLE, Terrain.ROCKY), new Location(2,4,0), TileOrientationRelativeToVolcano.SOUTHWEST_SOUTHEAST);
    }



}