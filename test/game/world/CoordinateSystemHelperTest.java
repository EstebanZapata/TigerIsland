package game.world;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tile.Location;
import tile.orientation.HexOrientation;

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

    
}
