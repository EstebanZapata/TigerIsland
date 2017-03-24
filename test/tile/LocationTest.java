package tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocationTest {
    private Location testLocation;

    @Before
    public void setupLocation() {
        testLocation = new Location(3,4,5);
    }

    @Test
    public void testGetRow() {
        Assert.assertEquals(testLocation.getxCoordinate(), 3);
    }

    @Test
    public void testGetCol() {
        Assert.assertEquals(testLocation.getyCoordinate(),4);
    }

    @Test
    public void testGetHeight() {
        Assert.assertEquals(testLocation.getzCoordinate(),5);
    }

}
