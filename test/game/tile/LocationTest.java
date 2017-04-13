package game.tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocationTest {
    private Location firstLocation;
    private Location secondLocation;
    private Location thirdLocation;

    @Before
    public void setupLocations() {
        firstLocation = new Location(3,4,5);
        secondLocation = new Location(3,4,5);
        thirdLocation = new Location(3,4,6);

    }

    @Test
    public void testLocationsWithSameCoordinatesAreEqual() {
        boolean firstLocationEqualsSecond = firstLocation.equals(secondLocation);

        Assert.assertTrue(firstLocationEqualsSecond);
    }

    @Test
    public void testLocationsWithSameCoordinatesAreEqualSymmetrically() {
        boolean secondLocationEqualsFirst = secondLocation.equals(firstLocation);

        Assert.assertTrue(secondLocationEqualsFirst);
    }

    @Test
    public void testLocationsWithDifferentCoordinatesAreDifferent() {
        boolean firstLocationDoesNotEqualThird = !firstLocation.equals(thirdLocation);

        Assert.assertTrue(firstLocationDoesNotEqualThird);
    }

    @Test
    public void testIncrementZ() {
        Assert.assertEquals(new Location(3,4,6), Location.incrementZ(firstLocation));
        Assert.assertEquals(new Location(3,4,7), Location.incrementZ(thirdLocation));

    }



}
