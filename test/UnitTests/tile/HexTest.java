package UnitTests.tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HexTest {
    private Tile tile;
    private Hex hex;

    @Before
    public void setupHex() {
        tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        hex = tile.getLeftHexRelativeToVolcano();
    }

    @Test
    public void testHexContainsTerrainPassedToConstructor() {
        Assert.assertEquals(Terrain.GRASSLANDS, hex.getTerrain());
    }

    @Test
    public void testGetOwnerOfHex() {
        Assert.assertEquals(tile, hex.getOwner());
    }

}