package game.tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


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
    }

    @Test
    public void testTileContainsOneVolcano() {
        int volcanoCount = 0;

        if (tile.getVolcanoHex().getTerrain() == Terrain.VOLCANO)
            volcanoCount++;

        if (tile.getLeftHexRelativeToVolcano().getTerrain() == Terrain.VOLCANO)
            volcanoCount++;

        if (tile.getRightHexRelativeToVolcano().getTerrain() == Terrain.VOLCANO)
            volcanoCount++;

        Assert.assertEquals(1, volcanoCount);
    }

    @Test
    public void testOtherHexesContainTerrainsPassedToConstructor() {

        Assert.assertEquals(Terrain.GRASSLANDS, tile.getLeftHexRelativeToVolcano().getTerrain());
        Assert.assertEquals(Terrain.JUNGLE, tile.getRightHexRelativeToVolcano().getTerrain());
    }

}