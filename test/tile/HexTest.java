package tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Hex;
import tile.Terrain;


public class HexTest {

    private Hex hex;

    @Before
    public void setupHex() {
        hex = new Hex(Terrain.JUNGLE);
    }

    @Test
    public void testHexContainsTerrainPassedToConstructor() {
        Assert.assertEquals(Terrain.JUNGLE, hex.getTerrain());
    }


}