package tile;

import org.junit.Assert;
import org.junit.Test;
import tile.Terrain;

public class TerrainTest {

    @Test
    public void testNonVolcanoTerrainsAreHabitable() {
        Assert.assertEquals(true, Terrain.JUNGLE.isHabitable());
        Assert.assertEquals(true, Terrain.LAKE.isHabitable());
        Assert.assertEquals(true, Terrain.GRASSLANDS.isHabitable());
        Assert.assertEquals(true, Terrain.ROCKY.isHabitable());
    }

    @Test
    public void testVolcanoTerrainIsNotHabitable() {
        Assert.assertEquals(false, Terrain.VOLCANO.isHabitable());
    }
}