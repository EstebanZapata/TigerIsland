package tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HexTest {
    private Tile tile;
    private Hex hexWithoutOwner;
    private Hex hexWithOwner;

    @Before
    public void setupHex() {
        tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        hexWithoutOwner = new Hex(null, Terrain.JUNGLE);
        hexWithOwner = new Hex(tile, Terrain.GRASSLANDS);
    }

    @Test
    public void testHexContainsTerrainPassedToConstructor() {
        Assert.assertEquals(Terrain.JUNGLE, hexWithoutOwner.getTerrain());
    }

    @Test
    public void testGetOwnerOfHex() {
        Assert.assertEquals(tile, hexWithOwner.getOwner());
    }

}