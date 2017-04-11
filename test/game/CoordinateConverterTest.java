package game;

import game.*;
import org.junit.Assert;
import org.junit.Test;
import tile.Location;

/**
 * Created by Liam on 4/6/2017.
 */
public class CoordinateConverterTest {
    @Test
    public void ConvertClientCoordinatesToServerCoordinatesTest() {
        Location firstTestLocation = new Location(1, -2, 4);
        String convertedLocation = Start.convertClientCoordinatesToServerCoordinates(firstTestLocation);
        Assert.assertEquals("1 1 -2", convertedLocation);

        Location secondTestLocation = new Location(-3, 3, 17);
        convertedLocation = Start.convertClientCoordinatesToServerCoordinates(secondTestLocation);
        Assert.assertEquals("-3 0 3", convertedLocation);

        Location thirdTestLocation = new Location(2, 1, 0);
        convertedLocation = Start.convertClientCoordinatesToServerCoordinates(thirdTestLocation);
        Assert.assertEquals("2 -3 1", convertedLocation);
    }
}
