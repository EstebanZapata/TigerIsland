package game.settlements;

import game.settlements.exceptions.*;
import game.world.rules.exceptions.IllegalTilePlacementException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.*;
import game.world.*;
import tile.orientation.TileOrientation;

import java.util.ArrayList;

public class SettlementManagerTest {
    private World world;
    private SettlementManager settlementManager;
    private Tile expansionTile1, expansionTile2;
    private Location volcanoLocation1, volcanoLocation2;
    private Hex hex1, hex2;

    @Before
    public void setUpSettlements() {
        this.world = new World();
        this.settlementManager = new SettlementManager(this.world);
        try {
            this.world.placeFirstTile();
            expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
            expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
            hex1 = new Hex(expansionTile1, Terrain.JUNGLE);
            hex2 = new Hex(expansionTile2, Terrain.GRASSLANDS);
            volcanoLocation1 = new Location(-2, 0, 0);
            volcanoLocation2 = new Location(2, 3, 0);
            this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
            this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        }
        catch (IllegalTilePlacementException e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testFoundSettlement() {
        try {
            Assert.assertEquals(this.settlementManager.settlements.size(), 0);
            this.settlementManager.foundSettlement(expansionTile1.getLeftHexRelativeToVolcano());
            Assert.assertEquals(this.settlementManager.settlements.size(), 1);
        }
        catch (SettlementAlreadyExistsOnHexException e) {
            Assert.assertTrue(false);
        }
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
    public void testBuildTigerPlayground() throws
            IllegalTilePlacementException,
            SettlementAlreadyExistsOnHexException,
            NoSettlementOnAdjacentHexesException
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

    @Test
    public void testExpandSettlement() {
        try {
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
        catch (SettlementAlreadyExistsOnHexException e) {
            Assert.assertTrue(false);
        }
        catch (NoHexesToExpandToException e) {
            Assert.assertTrue(false);
        }
        catch (SettlementCannotBeBuiltOnVolcanoException e) {
            Assert.assertTrue(false);
        }
        catch (IllegalTilePlacementException e) {
            Assert.assertTrue(false);
        }
    }
}