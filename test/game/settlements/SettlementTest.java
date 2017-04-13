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
    private Hex hex1, hex2, hex3, hex4, hex5, hex6, hex7, hex8, hex9, hex10;
    private World world;

    @Before
    public void setUpSettlements() throws
            BuildConditionsNotMetException
    {
        this.world = new World();

        Tile tile1 = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        this.hex1 = new Hex(tile1, Terrain.JUNGLE);
        this.settlement1 = new Settlement(hex1);

        Tile tile2 = new Tile(Terrain.LAKE, Terrain.ROCKY);
        this.hex2 = new Hex(tile2, Terrain.ROCKY);
        this.settlement2 = new Settlement(hex2);

        Tile tile3 = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);
        this.hex3 = new Hex(tile3, Terrain.GRASSLANDS);
        this.settlement3 = new Settlement(hex3);

        Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        this.hex4 = new Hex(tile4, Terrain.LAKE);
        this.settlement4 = new Settlement(hex4);

        Tile tile5 = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        this.hex5 = new Hex(tile4, Terrain.LAKE);

        Tile tile6 = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        this.hex6 = new Hex(tile6, Terrain.JUNGLE);

        Tile tile7 = new Tile(Terrain.LAKE, Terrain.ROCKY);
        this.hex7 = new Hex(tile7, Terrain.ROCKY);

        Tile tile8 = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);
        this.hex8 = new Hex(tile8, Terrain.GRASSLANDS);

        Tile tile9 = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        this.hex9 = new Hex(tile9, Terrain.LAKE);

        Tile tile10 = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        this.hex10 = new Hex(tile10, Terrain.LAKE);
    }

    @Test
    public void testFoundingHexIsInSettlement() {
        Assert.assertTrue(this.settlement1.containsHex(hex1));
        Assert.assertTrue(this.settlement2.containsHex(hex2));
        Assert.assertTrue(this.settlement3.containsHex(hex3));
        Assert.assertTrue(this.settlement4.containsHex(hex4));
    }

    @Test
    public void testForAHexNotInSettlement() {
        Assert.assertFalse(this.settlement1.containsHex(hex2));
        Assert.assertFalse(this.settlement2.containsHex(hex3));
        Assert.assertFalse(this.settlement3.containsHex(hex4));
        Assert.assertFalse(this.settlement4.containsHex(hex1));
    }

    @Test
    public void testAddingAHexToASettlement() throws SettlementAlreadyExistsOnHexException {
        Tile foundingTileInSettlement = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        Hex foundingHexInSettlement = new Hex(foundingTileInSettlement, Terrain.LAKE);

        Tile tileToAddToSettlement = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        Hex hexToAddToSettlement = new Hex(tileToAddToSettlement, Terrain.LAKE);

        Settlement newSettlement = new Settlement(foundingHexInSettlement);

        Assert.assertFalse(newSettlement.containsHex(hexToAddToSettlement));
        newSettlement.addHexToSettlement(hexToAddToSettlement);
        Assert.assertTrue(newSettlement.containsHex(hexToAddToSettlement));
    }

    @Test
    public void testRemovingAHexFromASettlement() throws
            SettlementAlreadyExistsOnHexException,
            SettlementCannotBeCompletelyWipedOutException
    {
        Tile foundingTileInSettlement = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        Hex foundingHexInSettlement = new Hex(foundingTileInSettlement, Terrain.LAKE);

        Tile tileToTest = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        Hex hexToTest = new Hex(tileToTest, Terrain.LAKE);

        Settlement newSettlement = new Settlement(foundingHexInSettlement);

        // Adding the settlement
        Assert.assertFalse(newSettlement.containsHex(hexToTest));
        newSettlement.addHexToSettlement(hexToTest);
        Assert.assertTrue(newSettlement.containsHex(hexToTest));

        // Removing the settlement
        newSettlement.removeHexFromSettlement(hexToTest);
        Assert.assertFalse(newSettlement.containsHex(hexToTest));
    }

    @Test (expected = SettlementCannotBeCompletelyWipedOutException.class)
    public void testTryingToWipeOutASettlement() throws
            BuildConditionsNotMetException,
            SettlementCannotBeCompletelyWipedOutException
    {
        Tile foundingTileInSettlement = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        Hex foundingHexInSettlement = new Hex(foundingTileInSettlement, Terrain.LAKE);

        Settlement newSettlement = new Settlement(foundingHexInSettlement);

        Assert.assertTrue(newSettlement.containsHex(foundingHexInSettlement));
        Assert.assertEquals(newSettlement.getSettlementSize(), 1);
        newSettlement.removeHexFromSettlement(foundingHexInSettlement);
    }

    @Test
    public void testGettingSettlementSize() throws
            SettlementCannotBeCompletelyWipedOutException,
            SettlementAlreadyExistsOnHexException
    {
        Tile tileToAdd1 = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        Hex hexToAdd1 = new Hex(tileToAdd1, Terrain.LAKE);

        Tile tileToAdd2 = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Hex hexToAdd2 = new Hex(tileToAdd2, Terrain.JUNGLE);

        Tile tileToAdd3 = new Tile(Terrain.LAKE, Terrain.ROCKY);
        Hex hexToAdd3 = new Hex(tileToAdd3, Terrain.ROCKY);

        Tile tileToAdd4 = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);
        Hex hexToAdd4 = new Hex(tileToAdd4, Terrain.GRASSLANDS);

        Tile tileToAdd5 = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Hex hexToAdd5 = new Hex(tileToAdd5, Terrain.LAKE);

        Settlement newSettlement = new Settlement(hexToAdd1);
        newSettlement.addHexToSettlement(hexToAdd2);
        newSettlement.addHexToSettlement(hexToAdd3);
        newSettlement.addHexToSettlement(hexToAdd4);
        newSettlement.addHexToSettlement(hexToAdd5);
        Assert.assertEquals(newSettlement.getSettlementSize(), 5);

        newSettlement.removeHexFromSettlement(hexToAdd4);
        newSettlement.removeHexFromSettlement(hexToAdd5);

        Assert.assertEquals(newSettlement.getSettlementSize(), 3);
    }

    @Test
    public void testGettingHexesFromSettlement() throws SettlementAlreadyExistsOnHexException {
        Tile foundingTileInSettlement = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        Hex foundingHexInSettlement = new Hex(foundingTileInSettlement, Terrain.LAKE);

        Tile tileInSettlement = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);
        Hex hexInSettlement = new Hex(tileInSettlement, Terrain.LAKE);

        Tile tileNotInSettlement = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Hex hexNotInSettlement = new Hex(tileNotInSettlement, Terrain.JUNGLE);

        Settlement newSettlement = new Settlement(foundingHexInSettlement);
        newSettlement.addHexToSettlement(hexInSettlement);

        ArrayList<Hex> settlementHexes = newSettlement.getHexesFromSettlement();

        Assert.assertTrue(settlementHexes.contains(foundingHexInSettlement));
        Assert.assertTrue(settlementHexes.contains(hexInSettlement));
        Assert.assertFalse(settlementHexes.contains(hexNotInSettlement));
    }

    @Test
    public void testCheckingForTotoro() {
        Assert.assertFalse(this.settlement3.hasTotoroSanctuary());
        this.settlement3.setHasTotoroSanctuary();
        Assert.assertTrue(this.settlement3.hasTotoroSanctuary());
    }

    @Test
    public void testCheckingForTiger() {
        Assert.assertFalse(this.settlement4.hasTigerPlayground());
        this.settlement4.setHasTigerPlayground();
        Assert.assertTrue(this.settlement4.hasTigerPlayground());
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
            this.settlement3.checkSanctuaryConditions();
        }
        catch (SettlementDoesNotSizeRequirementsException e) {
            this.settlement3.addHexToSettlement(this.hex6);
            this.settlement3.addHexToSettlement(this.hex7);
            this.settlement3.addHexToSettlement(this.hex8);
            this.settlement3.addHexToSettlement(this.hex9);

            Assert.assertEquals(this.settlement3.getSettlementSize(), 5);
            this.settlement3.checkSanctuaryConditions();
            this.settlement3.setHasTotoroSanctuary();
            this.settlement3.checkSanctuaryConditions();
        }

        Assert.assertTrue(false);
    }

    @Test (expected = SettlementAlreadyHasTigerPlaygroundException.class)
    public void testCheckingPlaygroundSettlementConditions() throws SettlementAlreadyHasTigerPlaygroundException {
        Assert.assertTrue(!this.settlement3.hasTigerPlayground());
        this.settlement3.checkPlaygroundConditions();
        this.settlement3.setHasTigerPlayground();
        this.settlement3.checkPlaygroundConditions();
    }

    @Test
    public void testGetPotentialSettlementHexes() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException
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
            NoHexesToExpandToException,
            BuildConditionsNotMetException
    {
        this.settlement1.getHexesToExpandTo(this.world, Terrain.LAKE);

        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);

        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);

        Hex foundingHex = expansionTile2.getLeftHexRelativeToVolcano();
        Settlement newSettlement = new Settlement(foundingHex);
        newSettlement.getHexesToExpandTo(this.world, foundingHex.getTerrain());
    }
}