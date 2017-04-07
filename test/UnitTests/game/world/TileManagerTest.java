package UnitTests.game.world;

import game.world.TileManager;
import game.world.rules.exceptions.NoHexAtLocationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.*;

import java.util.ArrayList;

public class TileManagerTest {
    private TileManager tileManager;

    private Tile tileZero;

    private Location[] locationsOnBottomLayerAdjacentToFirstTile;
    private Location[] locationsOnUpperLayer;
    private Location[] locationsOnEvenHigherLayer;

    @Before
    public void setup() {
        tileManager = new TileManager();

        tileZero = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);

        locationsOnBottomLayerAdjacentToFirstTile = new Location[] {new Location(1,0,0), new Location(2,0,0), new Location(2,1,0)};

        locationsOnUpperLayer = new Location[] {new Location(1,0,1), new Location(2,0,1), new Location(2,1,1)};

        locationsOnEvenHigherLayer = new Location[] {new Location(1,0,2), new Location(2,0,2), new Location(2,1,2)};

    }

    @Test
    public void testFirstTileHasBeenPlaced() {
        Assert.assertTrue(!tileManager.getFirstTileHasBeenPlaced());
        tileManager.placeFirstTile();
        Assert.assertTrue(tileManager.getFirstTileHasBeenPlaced());
    }

    @Test
    public void testHexesOfFirstTileAreAccurate() throws NoHexAtLocationException {
        Location locationOfJungle = new Location(0,1,0);
        Location locationOfLake = new Location(1,1,0);
        Location locationOfRocky = new Location(-1,-1,0);
        Location locationOfGrasslands = new Location(0,-1,0);
        Location locationOfVolcano = new Location(0,0,0);

        tileManager.placeFirstTile();

        Assert.assertEquals(Terrain.JUNGLE, tileManager.getHexByLocation(locationOfJungle).getTerrain());
        Assert.assertEquals(Terrain.LAKE, tileManager.getHexByLocation(locationOfLake).getTerrain());
        Assert.assertEquals(Terrain.ROCKY, tileManager.getHexByLocation(locationOfRocky).getTerrain());
        Assert.assertEquals(Terrain.GRASSLANDS, tileManager.getHexByLocation(locationOfGrasslands).getTerrain());
        Assert.assertEquals(Terrain.VOLCANO, tileManager.getHexByLocation(locationOfVolcano).getTerrain());
    }

    @Test
    public void testLocationsOfHexesHaveBeenAssigned() {
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileZero, locationsOnBottomLayerAdjacentToFirstTile);

        Assert.assertEquals(locationsOnBottomLayerAdjacentToFirstTile[0], tileZero.getVolcanoHex().getLocation());
        Assert.assertEquals(locationsOnBottomLayerAdjacentToFirstTile[1], tileZero.getLeftHexRelativeToVolcano().getLocation());
        Assert.assertEquals(locationsOnBottomLayerAdjacentToFirstTile[2], tileZero.getRightHexRelativeToVolcano().getLocation());
    }

    @Test
    public void testGetHexViaLocation() throws NoHexAtLocationException{
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileZero, locationsOnBottomLayerAdjacentToFirstTile);

        Assert.assertEquals(tileZero.getVolcanoHex(), tileManager.getHexByLocation(locationsOnBottomLayerAdjacentToFirstTile[0]));
        Assert.assertEquals(tileZero.getLeftHexRelativeToVolcano(), tileManager.getHexByLocation(locationsOnBottomLayerAdjacentToFirstTile[1]));
        Assert.assertEquals(tileZero.getRightHexRelativeToVolcano(), tileManager.getHexByLocation(locationsOnBottomLayerAdjacentToFirstTile[2]));
    }

    @Test
    public void testGetHexViaCreatingNewLocation() throws NoHexAtLocationException {
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileZero, locationsOnBottomLayerAdjacentToFirstTile);

        Assert.assertEquals(tileZero.getVolcanoHex(), tileManager.getHexByLocation(new Location(1,0,0)));
        Assert.assertEquals(tileZero.getLeftHexRelativeToVolcano(), tileManager.getHexByLocation(new Location(2,0,0)));
        Assert.assertEquals(tileZero.getRightHexRelativeToVolcano(), tileManager.getHexByLocation(new Location(2,1,0)));

    }

    @Test
    public void testGetHexViaCoordinates() throws NoHexAtLocationException {
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileZero, locationsOnBottomLayerAdjacentToFirstTile);

        int volcanoX = locationsOnBottomLayerAdjacentToFirstTile[0].getxCoordinate();
        int volcanoY = locationsOnBottomLayerAdjacentToFirstTile[0].getyCoordinate();
        int volcanoZ = locationsOnBottomLayerAdjacentToFirstTile[0].getzCoordinate();

        int leftX = locationsOnBottomLayerAdjacentToFirstTile[1].getxCoordinate();
        int leftY = locationsOnBottomLayerAdjacentToFirstTile[1].getyCoordinate();
        int leftZ = locationsOnBottomLayerAdjacentToFirstTile[1].getzCoordinate();

        int rightX = locationsOnBottomLayerAdjacentToFirstTile[2].getxCoordinate();
        int rightY = locationsOnBottomLayerAdjacentToFirstTile[2].getyCoordinate();
        int rightZ = locationsOnBottomLayerAdjacentToFirstTile[2].getzCoordinate();


        Assert.assertEquals(tileZero.getVolcanoHex(), tileManager.getHexByCoordinate(volcanoX,volcanoY,volcanoZ));
        Assert.assertEquals(tileZero.getLeftHexRelativeToVolcano(), tileManager.getHexByCoordinate(leftX,leftY,leftZ));
        Assert.assertEquals(tileZero.getRightHexRelativeToVolcano(), tileManager.getHexByCoordinate(rightX,rightY, rightZ));
    }

    @Test
    public void testFirstTileHexesHasBeenAddedToAllHexesList() throws NoHexAtLocationException {
        ArrayList<Hex> allHexes = tileManager.getAllHexesInWorld();

        tileManager.placeFirstTile();

        FirstTile firstTile = (FirstTile) tileManager.getHexByCoordinate(0,0,0).getOwner();

        Hex volcanoHex = firstTile.getVolcanoHex();
        Hex grasslandsHex = firstTile.getGrasslandsHex();
        Hex jungleHex = firstTile.getJungleHex();
        Hex lakeHex = firstTile.getLakeHex();
        Hex rockyHex = firstTile.getRockyHex();

        Assert.assertTrue(allHexes.contains(volcanoHex));
        Assert.assertTrue(allHexes.contains(grasslandsHex));
        Assert.assertTrue(allHexes.contains(jungleHex));
        Assert.assertTrue(allHexes.contains(lakeHex));
        Assert.assertTrue(allHexes.contains(rockyHex));

    }

    @Test
    public void testSubsequentTileHexesHaveBeenAddedToAllHexesList() {
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileZero, locationsOnBottomLayerAdjacentToFirstTile);

        ArrayList<Hex> allHexes = tileManager.getAllHexesInWorld();

        Assert.assertTrue(allHexes.contains(tileZero.getVolcanoHex()));
        Assert.assertTrue(allHexes.contains(tileZero.getLeftHexRelativeToVolcano()));
        Assert.assertTrue(allHexes.contains(tileZero.getRightHexRelativeToVolcano()));
    }

    @Test
    public void testPlacementOfHexOnHigherLevel() throws NoHexAtLocationException {
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileZero, locationsOnUpperLayer);

        Assert.assertEquals(tileZero.getLeftHexRelativeToVolcano(), tileManager.getHexByCoordinate(2,0,1));
        Assert.assertEquals(tileZero.getRightHexRelativeToVolcano(), tileManager.getHexByLocation(new Location(2,1,1)));
    }

    @Test(expected = NoHexAtLocationException.class)
    public void testAttemptToGetHexViaCoordinatesWithNoHexThereThrowsException() throws NoHexAtLocationException {
        tileManager.getHexByCoordinate(0,0,0);
    }

    @Test(expected = NoHexAtLocationException.class)
    public void testAttemptToGetHexViaLocationWithNoHexThereThrowsException() throws NoHexAtLocationException {
        tileManager.getHexByLocation(new Location(0,0,0));
    }

    @Test(expected = NoHexAtLocationException.class)
    public void testAttemptToGetHexAtUpperLevelButNoneIsThereThrowsException() throws NoHexAtLocationException {
        tileManager.getHexByLocation(new Location(0,0,1));
    }

    @Test(expected = NoHexAtLocationException.class)
    public void testAttemptToGetHexAtUpperLevelButNoneIsThereAndOneIsAboveThatLocationThrowsException() throws NoHexAtLocationException {
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileZero, locationsOnEvenHigherLayer);

        tileManager.getHexByCoordinate(2,0,1);
    }

    @Test(expected = NoHexAtLocationException.class)
    public void testAttemptToGetHexAtUpperLevelButNoneIsThereAndOneIsBelowThatLocationThrowsException() throws NoHexAtLocationException {
        tileManager.insertTileIntoCoordinateSystemAndAddHexesToList(tileZero, locationsOnUpperLayer);

        tileManager.getHexByCoordinate(2,0,2);
    }

}
