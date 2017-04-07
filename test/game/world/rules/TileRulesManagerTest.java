package game.world.rules;

import game.world.TileManager;
import game.world.rules.exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Location;
import tile.Terrain;
import tile.Tile;

public class TileRulesManagerTest {
    private TileManager tileManager;
    private TileRulesManager tileRulesManager;

    private Tile tileOne;
    private Tile tileTwo;

    private Location[] locationsAdjacentToSpecialFirstTile;
    private Location[] locationsOverlappingSpecialFirstTile;
    private Location[] locationsNotAdjacentToSpecialFirtTile;

    private Location[] locationsOnAHigherLevelAndAreValid;

    @Before
    public void setup() {
        tileManager = new TileManager();
        tileRulesManager = new TileRulesManager(tileManager);

        tileOne = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        tileTwo = new Tile(Terrain.LAKE, Terrain.ROCKY);

        locationsAdjacentToSpecialFirstTile = new Location[] {new Location(1,0,0), new Location(2,0,0), new Location(2,1,0)};
        locationsOverlappingSpecialFirstTile = new Location[] {new Location(0,0,0), new Location(1,0,0), new Location(1,1,0)};
        locationsNotAdjacentToSpecialFirtTile = new Location[] {new Location(2,0,0), new Location(3,0,0), new Location(3,1,0)};
        locationsOnAHigherLevelAndAreValid = new Location[] {new Location(0,0,1), new Location(1,0,1), new Location(1,1,1)};

    }

    @Test(expected = SpecialFirstTileHasNotBeenPlacedException.class)
    public void testFirstTilePlacedMustBeSpecialTile() throws IllegalTilePlacementException {
        tileRulesManager.ableToPlaceTileAtLocation(tileOne, locationsAdjacentToSpecialFirstTile);
    }

    @Test(expected = SpecialFirstTileHasAlreadyBeenPlacedExeption.class)
    public void testSpecialFirstTileCannotBePlacedAgain() throws IllegalTilePlacementException {
        tileManager.placeFirstTile();
        tileRulesManager.ableToPlaceFirstTile();

    }

    @Test
    public void testLegalPlacementOfTileOnLowestLevel() throws IllegalTilePlacementException {
        tileManager.placeFirstTile();

        Assert.assertTrue(tileRulesManager.ableToPlaceTileAtLocation(tileOne, locationsAdjacentToSpecialFirstTile));
    }

    @Test(expected = HexAlreadyAtLocationException.class)
    public void testIllegalPlacementOnLowestLevelDueToOverlap() throws IllegalTilePlacementException {
        tileManager.placeFirstTile();

        tileRulesManager.ableToPlaceTileAtLocation(tileOne, locationsOverlappingSpecialFirstTile);
    }

    @Test(expected = TileNotAdjacentToAnotherException.class)
    public void testIllegalPlacementOnLowestLevelDueToNotAdjacentToAnotherTile() throws IllegalTilePlacementException {
        tileManager.placeFirstTile();

        tileRulesManager.ableToPlaceTileAtLocation(tileOne, locationsNotAdjacentToSpecialFirtTile);
    }

    @Test
    public void testLegalPlacementOfTileOnAHigherLevel() throws IllegalTilePlacementException {
        tileManager.placeFirstTile();

        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileOne, locationsAdjacentToSpecialFirstTile);

        Assert.assertTrue(tileRulesManager.ableToPlaceTileAtLocation(tileTwo, locationsOnAHigherLevelAndAreValid));

    }

}
