package game.settlements;

import game.settlements.exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Hex;
import tile.Terrain;
import tile.Tile;

import java.util.ArrayList;

public class SettlementTest {
    private Settlement settlement1, settlement2, settlement3, settlement4;
    private Hex hex1, hex2, hex3, hex4;

    @Before
    public void setUpSettlements() {
        Tile tile1, tile2, tile3, tile4;

        tile1 = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        hex1 = new Hex(tile1, Terrain.JUNGLE);
        settlement1 = new Settlement(hex1);

        tile2 = new Tile(Terrain.LAKE, Terrain.ROCKY);
        hex2 = new Hex(tile2, Terrain.ROCKY);
        settlement2 = new Settlement(hex2);

        tile3 = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);
        hex3 = new Hex(tile3, Terrain.GRASSLANDS);
        settlement3 = new Settlement(hex3);

        tile4 = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        hex4 = new Hex(tile4, Terrain.LAKE);
        settlement4 = new Settlement(hex4);
    }

    @Test
    public void testFoundingHexIsInSettlement() {
        Assert.assertTrue(settlement1.containsHex(hex1));
        Assert.assertTrue(settlement2.containsHex(hex2));
        Assert.assertTrue(settlement3.containsHex(hex3));
        Assert.assertTrue(settlement4.containsHex(hex4));
    }

    @Test
    public void testForAHexNotInSettlement() {
        Assert.assertTrue(!settlement1.containsHex(hex2));
        Assert.assertTrue(!settlement2.containsHex(hex3));
        Assert.assertTrue(!settlement3.containsHex(hex4));
        Assert.assertTrue(!settlement4.containsHex(hex1));
    }

    @Test
    public void testAddingAHexToASettlement() {
        Assert.assertTrue(!settlement1.containsHex(hex2));
        settlement1.addHexToSettlement(hex2);
        Assert.assertTrue((settlement1.containsHex(hex2)));
    }

    @Test
    public void testRemovingAHexFromASettlement() {
        Assert.assertTrue(settlement1.containsHex(hex2));
        try {
            settlement1.removeHexFromSettlement(hex2);
            Assert.assertTrue(!settlement1.containsHex(hex2));
        } catch (SettlementCannotBeCompletelyWipedOutException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test (expected = SettlementCannotBeCompletelyWipedOutException.class)
    public void testTryingToWipeOutASettlement() throws SettlementCannotBeCompletelyWipedOutException {
        Assert.assertTrue(settlement1.containsHex(hex1));
        Assert.assertEquals(settlement1.getSettlementSize(), 1);
        settlement1.removeHexFromSettlement(hex1);
    }

    @Test
    public void testGettingSettlementSize() {
        Assert.assertEquals(settlement2.getSettlementSize(), 1);
        settlement2.addHexToSettlement(hex1);
        settlement2.addHexToSettlement(hex3);
        settlement2.addHexToSettlement(hex4);
        Assert.assertEquals(settlement2.getSettlementSize(), 4);
        try {
            settlement2.removeHexFromSettlement(hex3);
            settlement2.removeHexFromSettlement(hex4);
            Assert.assertEquals(settlement2.getSettlementSize(), 2);
        } catch (SettlementCannotBeCompletelyWipedOutException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGettingHexesFromSettlement() {
        ArrayList<Hex> hexes = settlement2.getHexesFromSettlement();
        Assert.assertTrue(hexes.contains(hex1));
        Assert.assertTrue(hexes.contains(hex2));
        Assert.assertTrue(!hexes.contains(hex3));
        Assert.assertTrue(!hexes.contains(hex4));
    }

    @Test
    public void testCheckingForTotoro() {
        Assert.assertTrue(!settlement3.hasTotoroSanctuary());
        settlement3.setHasTotoroSanctuary();
        Assert.assertTrue(settlement3.hasTotoroSanctuary());
    }

    @Test
    public void testCheckingForTiger() {
        Assert.assertTrue(!settlement4.hasTigerPlayground());
        settlement4.setHasTigerPlayground();
        Assert.assertTrue(settlement4.hasTigerPlayground());
    }
}