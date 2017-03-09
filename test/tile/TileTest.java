package tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TileTest {
    Tile tile;
    Terrain terrainOne;
    Terrain terrainTwo;

    @Before
    public void setupTile() {
        terrainOne = Terrain.GRASSLANDS;
        terrainTwo = Terrain.JUNGLE;
        tile = new Tile(terrainOne, terrainTwo);
    }

    @Test
    public void testTileContainsOneVolcano() {
        int volcanoCount = 0;

        Hex[] hexes = tile.getHexes();

        for(Hex hex:hexes) {
            if (hex.getTerrain() == Terrain.VOLCANO) {
                volcanoCount++;
            }
        }

        Assert.assertEquals(1, volcanoCount);
    }

    @Test
    public void testOtherHexesContainTerrainsPassedToConstructor() {
        Hex[] hexes = tile.getHexes();

        Assert.assertEquals(Terrain.GRASSLANDS, hexes[1].getTerrain());
        Assert.assertEquals(Terrain.JUNGLE, hexes[2].getTerrain());
    }
}