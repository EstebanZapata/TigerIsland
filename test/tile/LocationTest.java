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
    public void testSetCol() {
        testLocation.setCol(6);
        Assert.assertEquals(testLocation.getCol(), 6);
    }

    @Test
    public void testGetCol() {
        Assert.assertEquals(testLocation.getCol(),4);
    }

    @Test
    public void testSetRow() {
        testLocation.setRow(17);
        Assert.assertEquals(testLocation.getRow(),17);
    }

    @Test
    public void testGetRow() {
        Assert.assertEquals(testLocation.getRow(), 3);
    }

    @Test
    public void testSetHeight() {
        testLocation.setHeight(65);
        Assert.assertEquals(testLocation.getHeight(), 65);
    }

    @Test
    public void testGetHeight() {
        Assert.assertEquals(testLocation.getHeight(),5);
    }

}
