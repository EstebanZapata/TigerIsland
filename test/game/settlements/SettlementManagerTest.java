package game.settlements;

import game.settlements.exceptions.*;
import game.world.rules.exceptions.IllegalTilePlacementException;
import game.world.rules.exceptions.NoHexAtLocationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import game.tile.*;
import game.tile.orientation.*;
import game.world.*;

import java.util.ArrayList;

public class SettlementManagerTest {
    private World world;
    private SettlementManager settlementManager;
    private Tile expansionTile1, expansionTile2;
    private Hex hex1, hex2;

    @Before
    public void setUpSettlements() throws IllegalTilePlacementException {
        this.world = new World();
        this.settlementManager = new SettlementManager(this.world);

        expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        hex1 = new Hex(expansionTile1, Terrain.JUNGLE);
        hex2 = new Hex(expansionTile2, Terrain.GRASSLANDS);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);

        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
    }

    @Test
    public void testFoundSettlement() throws SettlementAlreadyExistsOnHexException {
        Assert.assertEquals(this.settlementManager.settlements.size(), 0);
        this.settlementManager.foundSettlement(expansionTile1.getLeftHexRelativeToVolcano());
        Assert.assertEquals(this.settlementManager.settlements.size(), 1);
    }

    @Test
    public void testGetSettlementFromHex() {
        try {
            Settlement newSettlement = this.settlementManager.foundSettlement(expansionTile1.getLeftHexRelativeToVolcano());
            Settlement testing = settlementManager.getSettlementFromHex(hex1);
            Assert.assertEquals(newSettlement, testing);
        }
        catch (SettlementAlreadyExistsOnHexException e) {
            System.out.println(e.getMessage());
        }
        catch (NoSettlementOnHexException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testAdvanceExpansion() throws
            SettlementAlreadyExistsOnHexException,
            SettlementCannotBeBuiltOnVolcanoException,
            NoHexesToExpandToException,
            NoHexAtLocationException
    {
        Settlement newSettlement = this.settlementManager.foundSettlement(expansionTile1.getRightHexRelativeToVolcano());
        this.settlementManager.expandSettlement(newSettlement, Terrain.JUNGLE);

        Hex jungleHex1 = world.getHexByCoordinate(-1,0,0);
        Hex jungleHex2 = world.getHexByCoordinate(0,1,0);
        Hex jungleHex3 = world.getHexByCoordinate(1,2,0);

        Settlement jungleHex1SettlementReturned = jungleHex1.getSettlement();
        Settlement jungleHex2SettlementReturned = jungleHex2.getSettlement();
        Settlement jungleHex3SettlementReturned = jungleHex3.getSettlement();

        Assert.assertEquals(newSettlement, jungleHex1SettlementReturned);
        Assert.assertEquals(newSettlement, jungleHex2SettlementReturned);
//        Assert.assertEquals(newSettlement, jungleHex3SettlementReturned);
    }

    @Test
    public void testBuildTigerPlayground() throws
            IllegalTilePlacementException,
            SettlementAlreadyExistsOnHexException,
            NoSettlementOnAdjacentHexesException,
            SettlementAlreadyHasTigerPlaygroundException
    {
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        // 1st level
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);

        // 2nd level
        Location volcanoLocation6 = new Location(0, 2, 1);
        Location volcanoLocation7 = new Location(0, 0, 1);
        Location volcanoLocation8 = new Location(2, 1, 1);

        // 3rd level
        Location volcanoLocation9 = new Location(0, 0, 2);

        world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.NORTHEAST_NORTHWEST);
        world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(expansionTile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);

        world.insertTileIntoTileManager(expansionTile6, volcanoLocation6, TileOrientation.SOUTHEAST_EAST);
        world.insertTileIntoTileManager(expansionTile7, volcanoLocation7, TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(expansionTile8, volcanoLocation8, TileOrientation.NORTHEAST_NORTHWEST);

        world.insertTileIntoTileManager(expansionTile9, volcanoLocation9, TileOrientation.NORTHEAST_NORTHWEST);

        Settlement expansion2Settlement = this.settlementManager.foundSettlement(expansionTile1.getRightHexRelativeToVolcano());
        this.settlementManager.buildTigerPlayground(expansionTile9.getRightHexRelativeToVolcano());
    }

    @Test (expected = NoSettlementOnAdjacentHexesException.class)
    public void testBuildTigerPlaygroundThrowsExceptionWhenTryingToBuildOnAnotherPlayground() throws
            IllegalTilePlacementException,
            SettlementAlreadyExistsOnHexException,
            NoSettlementOnAdjacentHexesException,
            SettlementAlreadyHasTigerPlaygroundException
    {
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        // 1st level
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);

        // 2nd level
        Location volcanoLocation6 = new Location(0, 2, 1);
        Location volcanoLocation7 = new Location(0, 0, 1);
        Location volcanoLocation8 = new Location(2, 1, 1);

        // 3rd level
        Location volcanoLocation9 = new Location(0, 0, 2);

        world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.NORTHEAST_NORTHWEST);
        world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(expansionTile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);

        world.insertTileIntoTileManager(expansionTile6, volcanoLocation6, TileOrientation.SOUTHEAST_EAST);
        world.insertTileIntoTileManager(expansionTile7, volcanoLocation7, TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(expansionTile8, volcanoLocation8, TileOrientation.NORTHEAST_NORTHWEST);

        world.insertTileIntoTileManager(expansionTile9, volcanoLocation9, TileOrientation.NORTHEAST_NORTHWEST);

        Settlement expansion2Settlement = this.settlementManager.foundSettlement(expansionTile1.getRightHexRelativeToVolcano());
        this.settlementManager.buildTigerPlayground(expansionTile9.getRightHexRelativeToVolcano());
        this.settlementManager.buildTigerPlayground(expansionTile9.getRightHexRelativeToVolcano());
    }

    @Test (expected = NoSettlementOnAdjacentHexesException.class)
    public void testBuildTigerPlaygroundThrowsNoSettlementOnAdjacentHexesException() throws
            IllegalTilePlacementException,
            SettlementAlreadyExistsOnHexException,
            NoSettlementOnAdjacentHexesException,
            SettlementAlreadyHasTigerPlaygroundException
    {
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        // 1st level
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);

        // 2nd level
        Location volcanoLocation6 = new Location(0, 2, 1);
        Location volcanoLocation7 = new Location(0, 0, 1);
        Location volcanoLocation8 = new Location(2, 1, 1);

        // 3rd level
        Location volcanoLocation9 = new Location(0, 0, 2);

        world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.NORTHEAST_NORTHWEST);
        world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(expansionTile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);

        world.insertTileIntoTileManager(expansionTile6, volcanoLocation6, TileOrientation.SOUTHEAST_EAST);
        world.insertTileIntoTileManager(expansionTile7, volcanoLocation7, TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(expansionTile8, volcanoLocation8, TileOrientation.NORTHEAST_NORTHWEST);

        world.insertTileIntoTileManager(expansionTile9, volcanoLocation9, TileOrientation.NORTHEAST_NORTHWEST);

        Settlement expansion2Settlement = this.settlementManager.foundSettlement(expansionTile1.getVolcanoHex());
        this.settlementManager.buildTigerPlayground(expansionTile9.getRightHexRelativeToVolcano());
    }

    @Test
    public void testExpandSettlement() throws
            SettlementAlreadyExistsOnHexException,
            NoHexesToExpandToException,
            SettlementCannotBeBuiltOnVolcanoException,
            IllegalTilePlacementException
    {
        Settlement newSettlement = this.settlementManager.foundSettlement(expansionTile2.getLeftHexRelativeToVolcano());
        Assert.assertEquals(newSettlement.getSettlementSize(), 1);

        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        Location volcanoLocation3 = new Location(0, 3, 0);
        Location volcanoLocation4 = new Location(-2, 1, 0);

        world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.SOUTHEAST_EAST);
        world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);

        newSettlement.addHexToSettlement(expansionTile3.getLeftHexRelativeToVolcano());
        this.settlementManager.expandSettlement(newSettlement, Terrain.JUNGLE);

        ArrayList<Hex> test = newSettlement.getHexesFromSettlement();
        for (Hex testing : test ) {
            System.out.println(testing.getLocation());
        }

        Assert.assertEquals(newSettlement.getSettlementSize(), 5);
    }
}