package game;

import game.player.exceptions.NotEnoughPiecesException;
import game.settlements.exceptions.BuildConditionsNotMetException;
import game.settlements.exceptions.SettlementAlreadyExistsOnHexException;
import game.tile.Hex;
import game.tile.Location;
import game.tile.Terrain;
import game.tile.Tile;
import game.tile.orientation.TileOrientation;
import game.world.*;
import game.player.Player;
import game.world.rules.exceptions.IllegalTilePlacementException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private World world;
    private Player player;
    private Tile expansionTile1, expansionTile2;
    private Hex hex1, hex2;

    @Before
    public void setupPlayer() throws IllegalTilePlacementException {
        this.world = new World();
        this.player = new Player(this.world);

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
    public void testPreConditions() {
        Assert.assertEquals(Settings.STARTING_SCORE_COUNT, player.getScore());
        Assert.assertEquals(Settings.STARTING_VILLAGER_COUNT, player.getVillagerCount());
        Assert.assertEquals(Settings.STARTING_TOTORO_COUNT, player.getTotoroCount());
        Assert.assertEquals(Settings.STARTING_TIGER_COUNT, player.getTigerCount());
    }

    @Test
    public void testFoundSettlement() throws
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        Assert.assertEquals(Settings.STARTING_VILLAGER_COUNT, player.getVillagerCount());
        this.player.foundSettlement(expansionTile1.getLeftHexRelativeToVolcano());
        int numberOfVillagersUsed = 1;
        int updatedVillagerCount = Settings.STARTING_VILLAGER_COUNT - numberOfVillagersUsed;
        Assert.assertEquals(updatedVillagerCount, player.getVillagerCount());
    }

    @Test (expected = SettlementAlreadyExistsOnHexException.class)
    public void testFoundSettlementThrowsSettlementAlreadyExistsOnHexException() throws
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        this.player.foundSettlement(expansionTile1.getLeftHexRelativeToVolcano());
        this.player.foundSettlement(expansionTile1.getLeftHexRelativeToVolcano());
    }
}
