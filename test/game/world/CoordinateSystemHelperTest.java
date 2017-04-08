package game.world;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tile.Location;
import tile.orientation.HexOrientation;
import tile.orientation.TileOrientation;

public class CoordinateSystemHelperTest {
    private Location origin;
    private Location arbitraryLocation;

    @Before
    public void setupOriginAndArbitraryLocation() {
        origin = new Location(0, 0, 0);
        arbitraryLocation = new Location(3,4,2);
    }

    @Test
    public void testGetSouthwestLocationFromOrigin() {
        Location southwest = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(origin, HexOrientation.SOUTHWEST);

        Assert.assertEquals(new Location(-1,-1,0), southwest);
    }

    @Test
    public void testGetSouthWestLocationFromArbitraryLocation() {
        Location southwest = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(arbitraryLocation, HexOrientation.SOUTHWEST);

        Assert.assertEquals(new Location(2,3,2), southwest);
    }

    @Test
    public void testGetWestLocationFromOrigin() {
        Location west = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(origin, HexOrientation.WEST);

        Assert.assertEquals(new Location(-1,0,0), west);
    }

    @Test
    public void testGetWestLocationFromArbitraryLocation() {
        Location west = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(arbitraryLocation, HexOrientation.WEST);

        Assert.assertEquals(new Location(2,4,2), west);
    }

    @Test
    public void testGetNorthwestLocationFromOrigin() {
        Location northwest = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(origin, HexOrientation.NORTHWEST);

        Assert.assertEquals(new Location(0,1,0), northwest);
    }

    @Test
    public void testGetNorthwestLocationFromArbitraryLocation() {
        Location northwest = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(arbitraryLocation, HexOrientation.NORTHWEST);

        Assert.assertEquals(new Location(3,5,2), northwest);
    }

    @Test
    public void testGetNortheastLocationFromOrigin() {
        Location northeast = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(origin, HexOrientation.NORTHEAST);

        Assert.assertEquals(new Location(1,1,0), northeast);
    }

    @Test
    public void testGetNortheastLocationFromArbitraryLocation() {
        Location northeast = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(arbitraryLocation, HexOrientation.NORTHEAST);

        Assert.assertEquals(new Location(4,5,2), northeast);
    }

    @Test
    public void testGetEastLocationFromOrigin() {
        Location east = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(origin, HexOrientation.EAST);

        Assert.assertEquals(new Location(1,0,0), east);
    }

    @Test
    public void testGetEastLocationFromArbitraryLocation() {
        Location east = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(arbitraryLocation, HexOrientation.EAST);

        Assert.assertEquals(new Location(4,4,2), east);
    }

    @Test
    public void testGetSoutheastLocationFromOrigin() {
        Location southeast = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(origin, HexOrientation.SOUTHEAST);

        Assert.assertEquals(new Location(0,-1,0), southeast);
    }

    @Test
    public void testGetSoutheastLocationFromArbitraryLocation() {
        Location southeast = CoordinateSystemHelper.getHexLocationRelativeToOrientationAndCenter(arbitraryLocation, HexOrientation.SOUTHEAST);

        Assert.assertEquals(new Location(3,3,2), southeast);
    }

    @Test
    public void testGetLocationsAdjacentToOrigin() {
        Location[] adjacentLocationsToOrigin = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(origin);

        Assert.assertEquals(new Location(-1,-1,0), adjacentLocationsToOrigin[0]);
        Assert.assertEquals(new Location(-1,0,0), adjacentLocationsToOrigin[1]);
        Assert.assertEquals(new Location(0,1,0), adjacentLocationsToOrigin[2]);
        Assert.assertEquals(new Location(1,1,0), adjacentLocationsToOrigin[3]);
        Assert.assertEquals(new Location(1,0,0), adjacentLocationsToOrigin[4]);
        Assert.assertEquals(new Location(0,-1,0), adjacentLocationsToOrigin[5]);

    }

    @Test
    public void testGetLocationsAdjacentToArbitraryLocation() {
        Location[] adjacentLocationsToOrigin = CoordinateSystemHelper.getHexLocationsAdjacentToCenter(arbitraryLocation);

        Assert.assertEquals(new Location(2,3,2), adjacentLocationsToOrigin[0]);
        Assert.assertEquals(new Location(2,4,2), adjacentLocationsToOrigin[1]);
        Assert.assertEquals(new Location(3,5,2), adjacentLocationsToOrigin[2]);
        Assert.assertEquals(new Location(4,5,2), adjacentLocationsToOrigin[3]);
        Assert.assertEquals(new Location(4,4,2), adjacentLocationsToOrigin[4]);
        Assert.assertEquals(new Location(3,3,2), adjacentLocationsToOrigin[5]);

    }

    @Test
    public void testGetLocationsAdjacentToArbitraryTile() {
        Location volcanoLocation = arbitraryLocation;
        Location leftHex = CoordinateSystemHelper.getTentativeLeftHexLocation(volcanoLocation, TileOrientation.EAST_NORTHEAST);
        Location rightHex = CoordinateSystemHelper.getTentativeRightHexLocation(volcanoLocation, TileOrientation.EAST_NORTHEAST);

        Location[] tileLocations = {volcanoLocation, leftHex, rightHex};

        Location[] adjacentLocations = CoordinateSystemHelper.getAdjacentHexLocationsToTile(tileLocations);

        Location[] expectedAdjacentLocations =
                {new Location(2,3,2), new Location(3,3,2), new Location(4,3,2),
                 new Location(5,4,2), new Location(5,5,2), new Location(5,6,2),
                 new Location(2,4,2), new Location(3,5,2), new Location(4,6,2)};

        int foundLocations = 0;

        for (Location expectedLocation:expectedAdjacentLocations) {
            for (Location returnedLocation:adjacentLocations) {
                if (expectedLocation.equals(returnedLocation)) {
                    foundLocations++;
                }
            }
        }

        Assert.assertEquals(9, foundLocations);

    }


    @Test
    public void testGetLeftHexLocationFromOriginAndOrientation() {
        Location locationZero = CoordinateSystemHelper.getTentativeLeftHexLocation(origin, TileOrientation.SOUTHWEST_SOUTHEAST);
        Location locationOne = CoordinateSystemHelper.getTentativeLeftHexLocation(origin, TileOrientation.WEST_SOUTHWEST);
        Location locationTwo = CoordinateSystemHelper.getTentativeLeftHexLocation(origin, TileOrientation.NORTHWEST_WEST);
        Location locationThree = CoordinateSystemHelper.getTentativeLeftHexLocation(origin, TileOrientation.NORTHEAST_NORTHWEST);
        Location locationFour = CoordinateSystemHelper.getTentativeLeftHexLocation(origin, TileOrientation.EAST_NORTHEAST);
        Location locationFive = CoordinateSystemHelper.getTentativeLeftHexLocation(origin, TileOrientation.SOUTHEAST_EAST);

        Assert.assertEquals(new Location(-1,-1,0), locationZero);
        Assert.assertEquals(new Location(-1,0,0), locationOne);
        Assert.assertEquals(new Location(0,1,0), locationTwo);
        Assert.assertEquals(new Location(1,1,0), locationThree);
        Assert.assertEquals(new Location(1,0,0), locationFour);
        Assert.assertEquals(new Location(0,-1,0), locationFive);

    }

    @Test
    public void testGetRightHexLocationFromOriginAndOrientation() {
        Location locationZero = CoordinateSystemHelper.getTentativeRightHexLocation(origin, TileOrientation.SOUTHWEST_SOUTHEAST);
        Location locationOne = CoordinateSystemHelper.getTentativeRightHexLocation(origin, TileOrientation.WEST_SOUTHWEST);
        Location locationTwo = CoordinateSystemHelper.getTentativeRightHexLocation(origin, TileOrientation.NORTHWEST_WEST);
        Location locationThree = CoordinateSystemHelper.getTentativeRightHexLocation(origin, TileOrientation.NORTHEAST_NORTHWEST);
        Location locationFour = CoordinateSystemHelper.getTentativeRightHexLocation(origin, TileOrientation.EAST_NORTHEAST);
        Location locationFive = CoordinateSystemHelper.getTentativeRightHexLocation(origin, TileOrientation.SOUTHEAST_EAST);

        Assert.assertEquals(new Location(0,-1,0), locationZero);
        Assert.assertEquals(new Location(-1,-1,0), locationOne);
        Assert.assertEquals(new Location(-1,0,0), locationTwo);
        Assert.assertEquals(new Location(0,1,0), locationThree);
        Assert.assertEquals(new Location(1,1,0), locationFour);
        Assert.assertEquals(new Location(1,0,0), locationFive);

    }

    @Test
    public void testGetLeftHexLocationFromArbitraryLocationAndOrientation() {
        Location locationZero = CoordinateSystemHelper.getTentativeLeftHexLocation(arbitraryLocation, TileOrientation.SOUTHWEST_SOUTHEAST);
        Location locationOne = CoordinateSystemHelper.getTentativeLeftHexLocation(arbitraryLocation, TileOrientation.WEST_SOUTHWEST);
        Location locationTwo = CoordinateSystemHelper.getTentativeLeftHexLocation(arbitraryLocation, TileOrientation.NORTHWEST_WEST);
        Location locationThree = CoordinateSystemHelper.getTentativeLeftHexLocation(arbitraryLocation, TileOrientation.NORTHEAST_NORTHWEST);
        Location locationFour = CoordinateSystemHelper.getTentativeLeftHexLocation(arbitraryLocation, TileOrientation.EAST_NORTHEAST);
        Location locationFive = CoordinateSystemHelper.getTentativeLeftHexLocation(arbitraryLocation, TileOrientation.SOUTHEAST_EAST);

        Assert.assertEquals(new Location(2,3,2), locationZero);
        Assert.assertEquals(new Location(2,4,2), locationOne);
        Assert.assertEquals(new Location(3,5,2), locationTwo);
        Assert.assertEquals(new Location(4,5,2), locationThree);
        Assert.assertEquals(new Location(4,4,2), locationFour);
        Assert.assertEquals(new Location(3,3,2), locationFive);

    }

    @Test
    public void testGetRightHexLocationFromArbitraryLocationAndOrientation() {
        Location locationZero = CoordinateSystemHelper.getTentativeRightHexLocation(arbitraryLocation, TileOrientation.SOUTHWEST_SOUTHEAST);
        Location locationOne = CoordinateSystemHelper.getTentativeRightHexLocation(arbitraryLocation, TileOrientation.WEST_SOUTHWEST);
        Location locationTwo = CoordinateSystemHelper.getTentativeRightHexLocation(arbitraryLocation, TileOrientation.NORTHWEST_WEST);
        Location locationThree = CoordinateSystemHelper.getTentativeRightHexLocation(arbitraryLocation, TileOrientation.NORTHEAST_NORTHWEST);
        Location locationFour = CoordinateSystemHelper.getTentativeRightHexLocation(arbitraryLocation, TileOrientation.EAST_NORTHEAST);
        Location locationFive = CoordinateSystemHelper.getTentativeRightHexLocation(arbitraryLocation, TileOrientation.SOUTHEAST_EAST);

        Assert.assertEquals(new Location(3,3,2), locationZero);
        Assert.assertEquals(new Location(2,3,2), locationOne);
        Assert.assertEquals(new Location(2,4,2), locationTwo);
        Assert.assertEquals(new Location(3,5,2), locationThree);
        Assert.assertEquals(new Location(4,5,2), locationFour);
        Assert.assertEquals(new Location(4,4,2), locationFive);

    }

}
