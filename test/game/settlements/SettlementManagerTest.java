package game.settlements;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Hex;
import tile.Terrain;
import tile.Tile;

import java.util.ArrayList;

/**
 * Created by thomasbaldwin on 4/8/17.
 */

public class SettlementManagerTest {
    private SettlementManager settlementManager;

    @Before
    public void setUpSettlements() {
        settlementManager = new SettlementManager();
    }

    @Test
    public void testFoundSettlementReturnsTrueWhenHexIsOnSettlement() {
        Tile foundingTile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Hex foundingHex = new Hex(foundingTile, Terrain.JUNGLE);
        this.settlementManager.foundSettlement(foundingHex);
        Assert.assertTrue(this.settlementManager.hasSettlementOnHex(foundingHex));
    }

    @Test
    public void testFoundSettlementReturnsFalseWhenHexIsNotOnSettlement() {
        Tile tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Hex hex = new Hex(tile, Terrain.JUNGLE);
        Assert.assertFalse(this.settlementManager.hasSettlementOnHex(hex));
    }

    @Test
    public void testGetSettlementFromHex() {
        Tile foundingTile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Hex foundingHex = new Hex(foundingTile, Terrain.JUNGLE);
        Settlement settlementAdded = this.settlementManager.foundSettlement(foundingHex);
        Settlement settlementReturned = this.settlementManager.getSettlementFromHex(foundingHex);
        Assert.assertEquals(settlementAdded, settlementReturned);
    }
}