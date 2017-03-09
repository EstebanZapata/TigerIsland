package tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TileTest {
    Tile tile;
    Terrain terrainOne;
    Terrain terrainTwo;
    Hex[] tileHexes;

    @Before
    public void setupTile() {
        terrainOne = Terrain.GRASSLANDS;
        terrainTwo = Terrain.JUNGLE;
        tile = new Tile(terrainOne, terrainTwo);
        tileHexes = tile.getHexes();
    }

    @Test
    public void testTileContainsOneVolcano() {
        int volcanoCount = 0;

        for(Hex hex:tileHexes) {
            if (hex.getTerrain() == Terrain.VOLCANO) {
                volcanoCount++;
            }
        }

        Assert.assertEquals(1, volcanoCount);
    }

    @Test
    public void testOtherHexesContainTerrainsPassedToConstructor() {

        Assert.assertEquals(Terrain.GRASSLANDS, tileHexes[1].getTerrain());
        Assert.assertEquals(Terrain.JUNGLE, tileHexes[2].getTerrain());
    }

    @Test
    public void testInnerHexesAreAdjacentToEachOther() throws NoAdjacentSideException {
        Hex hexZero = tileHexes[0];
        Hex hexOne = tileHexes[1];
        Hex hexTwo = tileHexes[2];

        Assert.assertEquals(hexOne, hexZero.getSides()[4].getAdjacentSideOwner());
        Assert.assertEquals(hexTwo, hexZero.getSides()[5].getAdjacentSideOwner());

        Assert.assertEquals(hexZero, hexOne.getSides()[5].getAdjacentSideOwner());
        Assert.assertEquals(hexTwo, hexOne.getSides()[4].getAdjacentSideOwner());

        Assert.assertEquals(hexZero, hexTwo.getSides()[4].getAdjacentSideOwner());
        Assert.assertEquals(hexOne, hexTwo.getSides()[5].getAdjacentSideOwner());
    }
}