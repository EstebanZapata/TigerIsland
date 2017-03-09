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
    public void testHexContainsSixSides() {
        Assert.assertEquals(6, hexWithoutOwner.getSides().length);
    }

    @Test
    public void testSidesHaveHexPassedToSideConstructorAsOwner() {
        Side[] sides = hexWithoutOwner.getSides();

        for(int i = 0; i < sides.length; i++) {
            Assert.assertEquals(hexWithoutOwner, sides[i].getOwner());
        }
    }

    @Test
    public void testGetOwnerOfHex() {
        Assert.assertEquals(tile, hexWithOwner.getOwner());
    }

}