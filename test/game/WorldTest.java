package game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Hex;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientationRelativeToVolcano;

public class WorldTest {
    private World world;
    private Tile tile;
    private Tile tileTwo;
    private Tile tileThree;
    private Tile tileFour;
    private Tile tileFive;
    private Tile tileSix;

    @Before
    public void setupWorldAndTiles() {
        world = new World();
        tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        tileTwo = new Tile(Terrain.JUNGLE, Terrain.ROCKY);
        tileThree = new Tile(Terrain.LAKE, Terrain.ROCKY);
        tileFour = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);
        tileFive = new Tile(Terrain.ROCKY, Terrain.LAKE);
        tileSix = new Tile(Terrain.LAKE, Terrain.JUNGLE);
    }

    @Test(expected = NoHexAtLocationException.class)
    public void testHexFromCoordinateWithoutHexThrowsException() throws NoHexAtLocationException {
        world.getHexByCoordinate(0, 1, 2);
    }

    // TODO: Test each orientation
    @Test
    public void testAfterPlacingTileWithOrientationSouthwestSoutheastGetHexesByCoordinate() throws HexAlreadyAtLocationException, NoHexAtLocationException, AirBelowTileException, TopVolcanoDoesNotCoverBottomVolcanoException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.placeFirstTile(tile,  TileOrientationRelativeToVolcano.SOUTHWEST_SOUTHEAST);

        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHexightHex = tile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(0,0,0));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(-1,-1,0));
        Assert.assertEquals(rightHexightHex, world.getHexByCoordinate(0,-1,0));
    }

    @Test
    public void testAfterPlacingTileWithOrientationWestSouthwestGetHexesByCoordinate() throws HexAlreadyAtLocationException, NoHexAtLocationException, AirBelowTileException, TopVolcanoDoesNotCoverBottomVolcanoException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.placeFirstTile(tile,  TileOrientationRelativeToVolcano.WEST_SOUTHWEST);

        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHexightHex = tile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(0,0,0));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(-1,0,0));
        Assert.assertEquals(rightHexightHex, world.getHexByCoordinate(-1,-1,0));
    }

    @Test
    public void testAfterPlacingTileWithOrientationNorthwestWestGetHexesByCoordinate() throws HexAlreadyAtLocationException, NoHexAtLocationException, AirBelowTileException, TopVolcanoDoesNotCoverBottomVolcanoException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.placeFirstTile(tile,  TileOrientationRelativeToVolcano.NORTHWEST_WEST);

        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHexightHex = tile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(0,0,0));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(0,1,0));
        Assert.assertEquals(rightHexightHex, world.getHexByCoordinate(-1,0,0));
    }

    @Test
    public void testAfterPlacingTileWithOrientationNortheastNorthwestGetHexesByCoordinate() throws HexAlreadyAtLocationException, NoHexAtLocationException, AirBelowTileException, TopVolcanoDoesNotCoverBottomVolcanoException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.placeFirstTile(tile,  TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST);

        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHexightHex = tile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(0,0,0));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(1,1,0));
        Assert.assertEquals(rightHexightHex, world.getHexByCoordinate(0,1,0));
    }

    @Test
    public void testAfterPlacingTileWithOrientationEastNortheastGetHexesByCoordinate() throws HexAlreadyAtLocationException, NoHexAtLocationException, AirBelowTileException, TopVolcanoDoesNotCoverBottomVolcanoException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        Location locationOfVolcano = new Location(3,4,0);
        world.insertTileIntoWorld(tile, locationOfVolcano, TileOrientationRelativeToVolcano.EAST_NORTHEAST);

        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHex = tile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(3,4,0));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(4,4,0));
        Assert.assertEquals(rightHex, world.getHexByCoordinate(4,5,0));
    }

    @Test
    public void testAfterPlacingTileWithOrientationSoutheastEastGetHexesByCoordinate() throws HexAlreadyAtLocationException, NoHexAtLocationException, AirBelowTileException, TopVolcanoDoesNotCoverBottomVolcanoException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.placeFirstTile(tile,  TileOrientationRelativeToVolcano.SOUTHEAST_EAST);

        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHexightHex = tile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(0,0,0));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(0,-1,0));
        Assert.assertEquals(rightHexightHex, world.getHexByCoordinate(1,0,0));
    }

    @Test(expected = HexAlreadyAtLocationException.class)
    public void testCannotPlaceTileOverlappingAnotherTile() throws HexAlreadyAtLocationException, AirBelowTileException, NoHexAtLocationException, TopVolcanoDoesNotCoverBottomVolcanoException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.insertTileIntoWorld(tile, new Location(1,2,0), TileOrientationRelativeToVolcano.EAST_NORTHEAST);
        world.insertTileIntoWorld(new Tile(Terrain.JUNGLE, Terrain.ROCKY), new Location(2,4,0), TileOrientationRelativeToVolcano.SOUTHWEST_SOUTHEAST);
    }

    @Test
    public void testSuccessfullyPlaceTileOnFirstLayerAdjacentToAnotherTile() throws HexAlreadyAtLocationException, TopVolcanoDoesNotCoverBottomVolcanoException, NoHexAtLocationException, AirBelowTileException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.placeFirstTile(tile, TileOrientationRelativeToVolcano.EAST_NORTHEAST);
        world.insertTileIntoWorld(tileTwo, new Location(2,0,0), TileOrientationRelativeToVolcano.EAST_NORTHEAST);

        Assert.assertEquals(tileTwo.getVolcanoHex(), world.getHexByCoordinate(2, 0, 0));
    }

    @Test(expected = TileNotAdjacentToAnotherException.class)
    public void testCannotPlaceTileOnFirstLayerWhichIsNotAdjacentToAnotherTile() throws HexAlreadyAtLocationException, TopVolcanoDoesNotCoverBottomVolcanoException, NoHexAtLocationException, AirBelowTileException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.placeFirstTile(tile, TileOrientationRelativeToVolcano.NORTHWEST_WEST);
        world.insertTileIntoWorld(tileTwo, new Location(2,0,0), TileOrientationRelativeToVolcano.EAST_NORTHEAST);
    }

    @Test
    public void testSuccessfullyPlaceTileOnAnotherLayer() throws HexAlreadyAtLocationException, AirBelowTileException, NoHexAtLocationException, TopVolcanoDoesNotCoverBottomVolcanoException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.insertTileIntoWorld(tile, new Location(0,0,0), TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST);
        world.insertTileIntoWorld(tileTwo, new Location(1,0,0), TileOrientationRelativeToVolcano.EAST_NORTHEAST);

        world.insertTileIntoWorld(tileThree, new Location(1,0,1),TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST );
    }

    @Test(expected = TopVolcanoDoesNotCoverBottomVolcanoException.class)
    public void testCannotPlaceTileDueToTopVolcanoNotCoveringBottomVolcano() throws HexAlreadyAtLocationException, TopVolcanoDoesNotCoverBottomVolcanoException, NoHexAtLocationException, AirBelowTileException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.insertTileIntoWorld(tile, new Location(0,0,0), TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST);
        world.insertTileIntoWorld(tileTwo, new Location(1,0,0), TileOrientationRelativeToVolcano.EAST_NORTHEAST);

        world.insertTileIntoWorld(tileThree, new Location(1,1,1),TileOrientationRelativeToVolcano.SOUTHEAST_EAST );
    }

    @Test(expected = AirBelowTileException.class)
    public void testCannotPlaceTileDueToAirBelowTile() throws HexAlreadyAtLocationException, TopVolcanoDoesNotCoverBottomVolcanoException, NoHexAtLocationException, AirBelowTileException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException {
        world.insertTileIntoWorld(tile, new Location(0,0,0), TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST);
        world.insertTileIntoWorld(tileTwo, new Location(1,0,0), TileOrientationRelativeToVolcano.EAST_NORTHEAST);

        world.insertTileIntoWorld(tileThree, new Location(1,1,1),TileOrientationRelativeToVolcano.EAST_NORTHEAST );
    }

    @Test(expected = TileCompletelyOverlapsAnotherException.class)
    public void testCannotPlaceTileDueToTopTileCompletelyOverlappingBottomTile() throws HexAlreadyAtLocationException, TopVolcanoDoesNotCoverBottomVolcanoException, NoHexAtLocationException, AirBelowTileException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException{
        world.insertTileIntoWorld(tile, new Location(0,0,0), TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST);
        world.insertTileIntoWorld(tileTwo, new Location(1,0,0), TileOrientationRelativeToVolcano.EAST_NORTHEAST);

        world.insertTileIntoWorld(tileThree, new Location(1,0,1),TileOrientationRelativeToVolcano.EAST_NORTHEAST );

    }

    @Test
    public void testPlacingTileTwoLayersUp () throws NoHexAtLocationException, HexAlreadyAtLocationException, TileNotAdjacentToAnotherException, TileCompletelyOverlapsAnotherException, AirBelowTileException, TopVolcanoDoesNotCoverBottomVolcanoException {
        world.insertTileIntoWorld(tile, new Location(0,0,0), TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST);
        world.insertTileIntoWorld(tileTwo, new Location(2,0,0), TileOrientationRelativeToVolcano.NORTHWEST_WEST);
        world.insertTileIntoWorld(tileThree, new Location(3,0,0),TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST );

        world.insertTileIntoWorld(tileFour, new Location(0,0,1), TileOrientationRelativeToVolcano.EAST_NORTHEAST);
        world.insertTileIntoWorld(tileFive, new Location(2,0,1), TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST);

        world.insertTileIntoWorld(tileSix, new Location(2,0,2), TileOrientationRelativeToVolcano.NORTHWEST_WEST);


    }







}