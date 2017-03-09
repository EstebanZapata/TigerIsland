package tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SideTest {
    private Hex hexContainingGrasslands;
    private Hex hexContainingLake;

    private Side sideWithoutAdjacentSide;
    private Side sideWithAdjacentSide;

    @Before
    public void setupSides() {
        hexContainingGrasslands = new Hex(null, Terrain.GRASSLANDS);
        hexContainingLake = new Hex(null, Terrain.LAKE);

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
    public void testGetAdjacentSideOwner() {
        Side adjacentSide = sideWithAdjacentSide.getAdjacentSide();
        Assert.assertEquals(hexContainingLake, adjacentSide.getOwner());
    }

    @Test
    public void testGetAdjacentSideOwnerDirectly(){
        try {
            Assert.assertEquals(hexContainingLake, sideWithAdjacentSide.getAdjacentSideOwner());
        } catch (NoAdjacentSideException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSideWithoutAdjacentSideHasNoAdjacentSide() {
        Assert.assertEquals(Side.NO_ADJACENT_SIDE, sideWithoutAdjacentSide.getAdjacentSide());
    }

    @Test(expected = NoAdjacentSideException.class)
    public void testAttemptToAccessOwnerOfNoAdjacentSideThrowsException() throws NoAdjacentSideException {
        sideWithoutAdjacentSide.getAdjacentSideOwner();
    }

    @Test
    public void testAdjacentSidesShouldBeAdjacentToEachOther() {
        Side adjacentSide = sideWithAdjacentSide.getAdjacentSide();

        Assert.assertEquals(adjacentSide, sideWithAdjacentSide.getAdjacentSide());
        Assert.assertEquals(sideWithAdjacentSide, adjacentSide.getAdjacentSide());
    }


}