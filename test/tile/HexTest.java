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

    @Test
    public void testHexContainsSixSides() {
        Assert.assertEquals(6, hex.getSides().length);
    }

    @Test
    public void testSidesHaveHexPassedToConstructorAsOwner() {
        Side[] sides = hex.getSides();

        for(int i = 0; i < sides.length; i++) {
            Assert.assertEquals(hex, sides[i].getOwner());
        }
    }

}