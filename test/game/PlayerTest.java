package game;

import game.player.exceptions.NotEnoughPiecesException;
import game.settlements.Settlement;
import game.settlements.exceptions.BuildConditionsNotMetException;
import game.settlements.exceptions.NoHexesToExpandToException;
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

    @Before
    public void setupPlayer() {
        this.world = new World();
        this.player = new Player(this.world);
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
            IllegalTilePlacementException,
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        Tile foundingTile = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location foundingLocation = new Location(-2, 0, 0);
        this.world.insertTileIntoTileManager(foundingTile, foundingLocation, TileOrientation.EAST_NORTHEAST);

        Assert.assertEquals(Settings.STARTING_VILLAGER_COUNT, player.getVillagerCount());
        this.player.foundSettlement(foundingTile.getLeftHexRelativeToVolcano());
        int numberOfVillagersUsed = 1;
        int updatedVillagerCount = Settings.STARTING_VILLAGER_COUNT - numberOfVillagersUsed;
        Assert.assertEquals(updatedVillagerCount, player.getVillagerCount());
    }

    @Test (expected = SettlementAlreadyExistsOnHexException.class)
    public void testFoundSettlementThrowsSettlementAlreadyExistsOnHexException() throws
            IllegalTilePlacementException,
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        Tile foundingTile = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location foundingLocation = new Location(-2, 0, 0);
        this.world.insertTileIntoTileManager(foundingTile, foundingLocation, TileOrientation.EAST_NORTHEAST);

        this.player.foundSettlement(foundingTile.getLeftHexRelativeToVolcano());
        this.player.foundSettlement(foundingTile.getLeftHexRelativeToVolcano());
    }

    @Test
    public void testExpandSettlement() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException,
            NotEnoughPiecesException
    {
        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile10 = new Tile(Terrain.ROCKY, Terrain.ROCKY);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(-2, -1, 0);
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);
        Location volcanoLocation6 = new Location(-1, -2, 0);
        Location volcanoLocation7 = new Location(0, 2, 1);
        Location volcanoLocation8 = new Location(0, 0, 1);
        Location volcanoLocation9 = new Location(0, 0, 2);
        Location volcanoLocation10 = new Location(1, -2, 0);

        this.world.insertTileIntoTileManager(tile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile3, volcanoLocation3, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile6, volcanoLocation6, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile7, volcanoLocation7, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile8, volcanoLocation8, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile9, volcanoLocation9, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile10, volcanoLocation10, TileOrientation.SOUTHWEST_SOUTHEAST);

        int pieceCount = Settings.STARTING_VILLAGER_COUNT;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        Settlement newSettlement = this.player.foundSettlement(tile1.getRightHexRelativeToVolcano());
        pieceCount = Settings.STARTING_VILLAGER_COUNT - 1;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        pieceCount -= 7;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.JUNGLE);
        pieceCount -= 8;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        pieceCount -= 2;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.ROCKY);
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, player.getVillagerCount());
    }

    public void testFoundSettlementCanUseAllPlayers() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException,
            NotEnoughPiecesException
    {
        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile10 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(-2, -1, 0);
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);
        Location volcanoLocation6 = new Location(-1, -2, 0);
        Location volcanoLocation7 = new Location(2, -1, 0);
        Location volcanoLocation8 = new Location(3, 0, 0);
        Location volcanoLocation9 = new Location(4, 0, 0);
        Location volcanoLocation10 = new Location(1, -3, 0);

        this.world.insertTileIntoTileManager(tile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile3, volcanoLocation3, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile6, volcanoLocation6, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile7, volcanoLocation7, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile8, volcanoLocation8, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile9, volcanoLocation9, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile10, volcanoLocation10, TileOrientation.NORTHWEST_WEST);

        int pieceCount = Settings.STARTING_VILLAGER_COUNT;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile1.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile1.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile2.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile2.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile3.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile3.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile4.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile4.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile5.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile5.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile6.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile6.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile7.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile7.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile8.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile8.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile9.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile9.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile10.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile10.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());
    }

    @Test (expected = NotEnoughPiecesException.class)
    public void testFoundSettlementThrowsNotEnoughPiecesException() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException,
            NotEnoughPiecesException
    {
        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile10 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile11 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(-2, -1, 0);
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);
        Location volcanoLocation6 = new Location(-1, -2, 0);
        Location volcanoLocation7 = new Location(2, -1, 0);
        Location volcanoLocation8 = new Location(3, 0, 0);
        Location volcanoLocation9 = new Location(4, 0, 0);
        Location volcanoLocation10 = new Location(1, -3, 0);
        Location volcanoLocation11 = new Location(6, 0, 0);

        this.world.insertTileIntoTileManager(tile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile3, volcanoLocation3, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile6, volcanoLocation6, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile7, volcanoLocation7, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile8, volcanoLocation8, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile9, volcanoLocation9, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile10, volcanoLocation10, TileOrientation.NORTHWEST_WEST);
        this.world.insertTileIntoTileManager(tile11, volcanoLocation11, TileOrientation.SOUTHEAST_EAST);

        int pieceCount = Settings.STARTING_VILLAGER_COUNT;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile1.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile1.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile2.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile2.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile3.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile3.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile4.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile4.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile5.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile5.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile6.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile6.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile7.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile7.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile8.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile8.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile9.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile9.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile10.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile10.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile11.getLeftHexRelativeToVolcano());
    }

    @Test (expected = NotEnoughPiecesException.class)
    public void testExpandSettlementThrowsNotEnoughPiecesException() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException,
            NotEnoughPiecesException
    {
        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile10 = new Tile(Terrain.ROCKY, Terrain.ROCKY);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(-2, -1, 0);
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);
        Location volcanoLocation6 = new Location(-1, -2, 0);
        Location volcanoLocation7 = new Location(0, 2, 1);
        Location volcanoLocation8 = new Location(0, 0, 1);
        Location volcanoLocation9 = new Location(0, 0, 2);
        Location volcanoLocation10 = new Location(1, -3, 0);

        this.world.insertTileIntoTileManager(tile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile3, volcanoLocation3, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile6, volcanoLocation6, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile7, volcanoLocation7, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile8, volcanoLocation8, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile9, volcanoLocation9, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile10, volcanoLocation10, TileOrientation.NORTHWEST_WEST);

        int pieceCount = Settings.STARTING_VILLAGER_COUNT;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        Settlement newSettlement = this.player.foundSettlement(tile1.getRightHexRelativeToVolcano());
        pieceCount = Settings.STARTING_VILLAGER_COUNT - 1;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        pieceCount -= 7;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.JUNGLE);
        pieceCount -= 8;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        pieceCount -= 2;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.ROCKY);
    }
}
