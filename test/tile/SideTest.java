package tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Hex;
import tile.Side;
import tile.Terrain;


public class SideTest {
    Hex hexContainingGrasslands;
    Hex hexContainingLake;

    Side sideWithoutAdjacentSide;
    Side sideWithAdjacentSide;

    @Before
    public void setupSides() {
        hexContainingGrasslands = new Hex(Terrain.GRASSLANDS);
        hexContainingLake = new Hex(Terrain.LAKE);

        Side adjacentSide = new Side(hexContainingLake);

        sideWithoutAdjacentSide = new Side(hexContainingGrasslands);
        sideWithAdjacentSide = new Side(hexContainingGrasslands, adjacentSide);
    }

    @Test
    public void testGetSideOwner() {
        Assert.assertEquals(hexContainingGrasslands, sideWithoutAdjacentSide.getOwner());
        Assert.assertEquals(hexContainingGrasslands, sideWithAdjacentSide.getOwner());
    }

    @Test
    public void testSideWithoutAdjacentSideHasNullAdjacentSide() {
        Assert.assertEquals(null, sideWithoutAdjacentSide.getAdjacentSide());
    }


    @Test
    public void testGetAdjacentSideOwner() {
        Side adjacentSide = sideWithAdjacentSide.getAdjacentSide();
        Assert.assertEquals(hexContainingLake, adjacentSide.getOwner());
    }

    @Test
    public void testAdjacentSidesShouldBeAdjacentToEachOther() {
        Side adjacentSide = sideWithAdjacentSide.getAdjacentSide();

        Assert.assertEquals(adjacentSide, sideWithAdjacentSide.getAdjacentSide());
        Assert.assertEquals(sideWithAdjacentSide, adjacentSide.getAdjacentSide());
    }

}