package game.world.rules;

import game.world.TileManager;
import game.world.rules.exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import game.tile.Location;
import game.tile.Terrain;
import game.tile.Tile;

public class TileRulesManagerTest {
    private TileManager tileManager;
    private TileRulesManager tileRulesManager;

    private Tile tileOne;
    private Tile tileTwo;
    private Tile tileThree;
    private Tile tileFour;
    private Tile tileFive;
    private Tile tileSix;

    private Location[] locationsAdjacentToSpecialFirstTile;
    private Location[] locationsAlsoAdjacentToSpecialFirstTile;

    private Location[] locationsOverlappingSpecialFirstTile;
    private Location[] locationsNotAdjacentToSpecialFirtTile;

    private Location[] locationsOnAHigherLevelAndAreValid;
    private Location[] locationsAlsoOnAHigherLevelAndAreValid;
    private Location[] locationsOnAHigherLevelWhereOneCoversAVolcanoAndAnotherDoesNot;

    private Location[] locationsOnAnEvenHigherLevelAndAreValid;

    private Location[] locationOnAHigherLevelAndOverlaps;
    private Location[] locationsOnAHigherLevelAndHasGapBelow;
    private Location[] locationsOnAHigherLevelThatCompletelyOverlapsBottomTile;
    private Location[] locationsOnAHigherLevelWhereVolcanoDoesNotCoverLowerOne;

    private Location[] locationsOnEvenHigherLevelAndHaveAirGapBelow;

    @Before
    public void setup() {
        tileManager = new TileManager();
        tileRulesManager = new TileRulesManager(tileManager);

        tileOne = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        tileTwo = new Tile(Terrain.LAKE, Terrain.ROCKY);
        tileThree = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        tileFour = new Tile(Terrain.LAKE, Terrain.ROCKY);
        tileFive = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        tileSix = new Tile(Terrain.LAKE, Terrain.JUNGLE);

        locationsAdjacentToSpecialFirstTile = new Location[] {new Location(1,0,0), new Location(2,0,0), new Location(2,1,0)};
        locationsAlsoAdjacentToSpecialFirstTile = new Location[] {new Location(1,-1,0), new Location(1,-2,0), new Location(0,-2,0)};

        locationsOverlappingSpecialFirstTile = new Location[] {new Location(0,0,0), new Location(1,0,0), new Location(1,1,0)};
        locationsNotAdjacentToSpecialFirtTile = new Location[] {new Location(2,0,0), new Location(3,0,0), new Location(3,1,0)};

        locationsOnAHigherLevelAndAreValid = new Location[] {new Location(0,0,1), new Location(1,0,1), new Location(1,1,1)};
        locationsAlsoOnAHigherLevelAndAreValid = new Location[] {new Location(0,-1,1), new Location(1,-1,1), new Location(0,-2,1)};
        locationsOnAHigherLevelWhereOneCoversAVolcanoAndAnotherDoesNot = new Location[] {new Location(0,0,1), new Location(1,0,1), new Location(0,-1,1)};

        locationsOnAnEvenHigherLevelAndAreValid = new Location[] {new Location(0,-1,2), new Location(1,0,2), new Location(1,-1,2)};

        locationOnAHigherLevelAndOverlaps = new Location[] {new Location(0,-1,1), new Location(1,-1,1), new Location(1,0,1)};
        locationsOnAHigherLevelAndHasGapBelow = new Location[] {new Location(1,-1,1), new Location(2,0,1), new Location(2,-1,1)};
        locationsOnAHigherLevelThatCompletelyOverlapsBottomTile = new Location[] {new Location(0,0,1), new Location(-1,-1,1), new Location(0,-1,1)};
        locationsOnAHigherLevelWhereVolcanoDoesNotCoverLowerOne = new Location[] {new Location(0,-1,1), new Location(1,0,1), new Location(1,-1,1)};

        locationsOnEvenHigherLevelAndHaveAirGapBelow = new Location[] {new Location(0,0,2), new Location(0,-2,2), new Location(-1,-1,2)};


    }

    private void alsoSetUpValidLowestLevelLocations() {
        tileManager.placeFirstTile();

        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileOne, locationsAdjacentToSpecialFirstTile);
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileTwo, locationsAlsoAdjacentToSpecialFirstTile);
    }

    @Test(expected = SpecialFirstTileHasNotBeenPlacedException.class)
    public void testFirstTilePlacedMustBeSpecialTile() throws IllegalTilePlacementException {
        Assert.assertTrue(tileRulesManager.ableToPlaceFirstTile());
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

    @Test
    public void testLegalPlacementOfTileOnAHigherLevelEvenIfOneVolcanoIsUncoveredButAnotherIsCovered() {
        alsoSetUpValidLowestLevelLocations();

        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileThree, locationsOnAHigherLevelWhereOneCoversAVolcanoAndAnotherDoesNot);
    }

    @Test
    public void testLegalPlacementOfTileOnAnEvenHigherLevel() throws IllegalTilePlacementException {
        alsoSetUpValidLowestLevelLocations();

        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileThree, locationsOnAHigherLevelAndAreValid);
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileFour, locationsAlsoOnAHigherLevelAndAreValid);

        Assert.assertTrue(tileRulesManager.ableToPlaceTileAtLocation(tileFive, locationsOnAnEvenHigherLevelAndAreValid));
    }

    @Test(expected = HexAlreadyAtLocationException.class)
    public void testIllegalPlacementOnUpperLevelDueToOverlap() throws IllegalTilePlacementException {
        alsoSetUpValidLowestLevelLocations();

        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileThree, locationsOnAHigherLevelAndAreValid);

        tileRulesManager.ableToPlaceTileAtLocation(tileFour, locationOnAHigherLevelAndOverlaps);

    }

    @Test(expected = AirBelowTileException.class)
    public void testIllegalPlacementOnUpperLevelDueToAirBelow() throws IllegalTilePlacementException {
        alsoSetUpValidLowestLevelLocations();

        tileRulesManager.ableToPlaceTileAtLocation(tileThree, locationsOnAHigherLevelAndHasGapBelow);
    }

    @Test(expected = TileCompletelyOverlapsAnotherException.class)
    public void testIllegalPlacementOnUpperLevelDueToCompleteCoverageOfTile() throws IllegalTilePlacementException {
        alsoSetUpValidLowestLevelLocations();

        tileRulesManager.ableToPlaceTileAtLocation(tileThree, locationsOnAHigherLevelThatCompletelyOverlapsBottomTile);
    }

    @Test(expected = TopVolcanoDoesNotCoverBottomVolcanoException.class)
    public void testIllegalPlacementOnUpperLevelDueToVolcanoNotCoveringABottomOne() throws IllegalTilePlacementException {
        alsoSetUpValidLowestLevelLocations();

        tileRulesManager.ableToPlaceTileAtLocation(tileThree, locationsOnAHigherLevelWhereVolcanoDoesNotCoverLowerOne);

    }

    @Test(expected = AirBelowTileException.class)
    public void testIllegalPlacementOnEvenHigherLevelDueToAirGapBelow() throws IllegalTilePlacementException {
        alsoSetUpValidLowestLevelLocations();

        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileThree, locationsOnAHigherLevelAndAreValid);
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileFour, locationsAlsoOnAHigherLevelAndAreValid);

        tileRulesManager.ableToPlaceTileAtLocation(tileSix, locationsOnEvenHigherLevelAndHaveAirGapBelow);

    }

    @Test
    public void testAbleToPlaceTileNextToCliff() throws Exception {
        tileManager.placeFirstTile();

        Location[] locationOnFirstLevel = new Location[] {new Location(1,-1,0), new Location(1,-2,0), new Location(0,-2,0)};
        Location[] anotherLocationOnFirstLevel = new Location[] {new Location(1,0,0), new Location(2,0,0), new Location(2,1,0)};

        Location[] locationOnSecondLevel = new Location[] {new Location(1,-1,1), new Location(2,0,1), new Location(1,0,1)};

        Location[] locationNextToCliff = new Location[] {new Location(3,0,0), new Location(4,0,0), new Location(3,-1,0)};

        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileOne, locationOnFirstLevel);
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileTwo, anotherLocationOnFirstLevel);
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileThree, locationOnSecondLevel );

        Assert.assertTrue(tileRulesManager.ableToPlaceTileAtLocation(tileFour, locationNextToCliff));

    }

}
