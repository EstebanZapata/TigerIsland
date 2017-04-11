package game.settlements;

import game.settlements.exceptions.*;
import game.world.World;
import game.world.rules.exceptions.IllegalTilePlacementException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import game.tile.*;
import game.tile.orientation.*;

import java.util.ArrayList;

public class SettlementTest {
    private Settlement settlement1, settlement2, settlement3, settlement4;
    private Hex hex1, hex2, hex3, hex4, hex5;
    private World world;

    @Before
    public void setUpSettlements() {
        Tile tile1, tile2, tile3, tile4, tile5;
        world = new World();

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

        tile5 = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        hex5 = new Hex(tile4, Terrain.LAKE);
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
    public void testAddingAHexToASettlement() throws SettlementAlreadyExistsOnHexException {
        Assert.assertTrue(!settlement1.containsHex(hex2));
        settlement1.addHexToSettlement(hex2);
        Assert.assertTrue((settlement1.containsHex(hex2)));
    }

    @Test
    public void testRemovingAHexFromASettlement() throws
            SettlementAlreadyExistsOnHexException,
            SettlementCannotBeCompletelyWipedOutException
    {
        settlement1.addHexToSettlement(hex2);
        Assert.assertTrue(settlement1.containsHex(hex2));
        settlement1.removeHexFromSettlement(hex2);
        Assert.assertTrue(!settlement1.containsHex(hex2));
    }

    @Test (expected = SettlementCannotBeCompletelyWipedOutException.class)
    public void testTryingToWipeOutASettlement() throws SettlementCannotBeCompletelyWipedOutException {
        Assert.assertTrue(settlement1.containsHex(hex1));
        Assert.assertEquals(settlement1.getSettlementSize(), 1);
        settlement1.removeHexFromSettlement(hex1);
    }

    @Test
    public void testGettingSettlementSize() throws
            SettlementCannotBeCompletelyWipedOutException,
            SettlementAlreadyExistsOnHexException
    {
        Assert.assertEquals(settlement2.getSettlementSize(), 1);
        settlement2.addHexToSettlement(hex1);
        settlement2.addHexToSettlement(hex3);
        settlement2.addHexToSettlement(hex4);
        Assert.assertEquals(settlement2.getSettlementSize(), 4);
        settlement2.removeHexFromSettlement(hex3);
        settlement2.removeHexFromSettlement(hex4);
        Assert.assertEquals(settlement2.getSettlementSize(), 2);
    }

    @Test
    public void testGettingHexesFromSettlement() throws SettlementAlreadyExistsOnHexException {
        settlement2.addHexToSettlement(hex1);
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

    @Test (expected = SettlementAlreadyHasTotoroSanctuaryException.class)
    public void testCheckingSanctuarySettlementConditions() throws
            SettlementAlreadyExistsOnHexException,
            SettlementDoesNotSizeRequirementsException,
            SettlementAlreadyHasTotoroSanctuaryException
    {
        Assert.assertFalse(settlement3.hasTotoroSanctuary());
        Assert.assertEquals(settlement3.getSettlementSize(), 1);

        try {
            settlement3.checkSanctuaryConditions();
        }
        catch (SettlementDoesNotSizeRequirementsException e) {
            settlement3.addHexToSettlement(hex1);
            settlement3.addHexToSettlement(hex2);
            settlement3.addHexToSettlement(hex4);
            settlement3.addHexToSettlement(hex5);

            Assert.assertEquals(settlement3.getSettlementSize(), 5);
            settlement3.checkSanctuaryConditions();
            settlement3.setHasTotoroSanctuary();
            settlement3.checkSanctuaryConditions();
        }

        Assert.assertTrue(false);
    }

    @Test (expected = SettlementAlreadyHasTigerPlaygroundException.class)
    public void testCheckingPlaygroundSettlementConditions() throws SettlementAlreadyHasTigerPlaygroundException {
        Assert.assertTrue(!settlement3.hasTigerPlayground());
        settlement3.checkPlaygroundSettlementConditions();
        settlement3.setHasTigerPlayground();
        settlement3.checkPlaygroundSettlementConditions();
    }

    @Test
    public void testGetPotentialSettlementHexes() throws
            IllegalTilePlacementException
    {
        World world = new World();
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        Location volcanoLocation3 = new Location(0, 3, 0);
        world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        Hex foundingHex = expansionTile1.getLeftHexRelativeToVolcano();
        Settlement newSettlement = new Settlement(foundingHex);
        world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.SOUTHEAST_EAST);
        ArrayList<Hex> potentialSettlementHexes = newSettlement.getPotentialSettlementHexes(foundingHex, world, foundingHex.getTerrain());
        System.out.println(potentialSettlementHexes.get(0).getLocation());
        Assert.assertEquals(potentialSettlementHexes.size(), 1);
    }

    @Test
    public void testGetHexesToExpandTo() throws
            IllegalTilePlacementException,
            SettlementAlreadyExistsOnHexException,
            SettlementCannotBeBuiltOnVolcanoException,
            NoHexesToExpandToException
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        Location volcanoLocation3 = new Location(0, 3, 0);
        Location volcanoLocation4 = new Location(-2, 1, 0);
        world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        Hex foundingHex = expansionTile1.getLeftHexRelativeToVolcano();
        Settlement newSettlement = new Settlement(foundingHex);
        world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.SOUTHEAST_EAST);
        world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);
        ArrayList<Hex> potentialSettlementHexes = newSettlement.getHexesToExpandTo(world, foundingHex.getTerrain());
        System.out.println(potentialSettlementHexes.get(0).getLocation());
        System.out.println(potentialSettlementHexes.get(1).getLocation());
        System.out.println(potentialSettlementHexes.get(2).getLocation());
        System.out.println(potentialSettlementHexes.get(3).getLocation());
        Assert.assertEquals(potentialSettlementHexes.size(), 4);
    }

    @Test (expected = SettlementCannotBeBuiltOnVolcanoException.class)
    public void testTryToExpandOnVolcano() throws SettlementCannotBeBuiltOnVolcanoException, NoHexesToExpandToException {
        settlement1.getHexesToExpandTo(world, Terrain.VOLCANO);
    }

    @Test (expected = NoHexesToExpandToException.class)
    public void testForNoExpansionHexes() throws
            IllegalTilePlacementException,
            SettlementCannotBeBuiltOnVolcanoException,
            NoHexesToExpandToException
    {
        settlement1.getHexesToExpandTo(world, Terrain.LAKE);
        world.placeFirstTile();
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);

        world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);

        Hex foundingHex = expansionTile2.getLeftHexRelativeToVolcano();
        Settlement newSettlement = new Settlement(foundingHex);
        newSettlement.getHexesToExpandTo(world, foundingHex.getTerrain());
    }
}