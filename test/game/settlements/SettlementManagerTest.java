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

/**
 * Created by thomasbaldwin on 4/8/17.
 */

public class SettlementManagerTest {
    private World world;
    private SettlementManager settlementManager;

    @Before
    public void setUpSettlements() {
        this.world = new World();
        this.settlementManager = new SettlementManager();
        try {
            this.world.placeFirstTile();
        }
        catch (IllegalTilePlacementException e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void expandSettlementTest() {
        Tile expansionTile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Location volcanoLocation = new Location(-2, 0, 0);
        try {
            this.world.insertTileIntoTileManager(expansionTile, volcanoLocation, TileOrientation.EAST_NORTHEAST);
            Settlement newSettlement = this.settlementManager.foundSettlement(expansionTile.getRightHexRelativeToVolcano());
            this.settlementManager.expandSettlement(world, newSettlement, Terrain.JUNGLE);
            Assert.assertEquals(newSettlement.getSettlementSize(), 2);
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

    @Test
    public void testFoundSettlement() {
        Tile expansionTile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Location volcanoLocation = new Location(-2, 0, 0);
        try {
            this.world.insertTileIntoTileManager(expansionTile, volcanoLocation, TileOrientation.EAST_NORTHEAST);
            Assert.assertEquals(this.settlementManager.settlements.size(), 0);
            this.settlementManager.foundSettlement(expansionTile.getRightHexRelativeToVolcano());
            Assert.assertEquals(this.settlementManager.settlements.size(), 1);
        }
        catch (IllegalTilePlacementException e) {
            Assert.assertTrue(false);
        }
        catch (SettlementAlreadyExistsOnHexException e) {
            Assert.assertTrue(false);
        }
    }
}