package pieces;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Location;

/**
 * Created by thomasbaldwin on 3/24/17.
 */

public class TigerTest {
    private tiger testTiger;

    @Before
    public void setupTiger() {
        testTiger = new tiger(1, "blue", new Location(3,4,5), "in_play");
    }

    @Test
    public void testSettingTigerId() throws Throwable {
        int tigerId = 1;
        testTiger.setTigerID(tigerId);
        Assert.assertEquals(tigerId, testTiger.getTigerID());
    }

    @Test
    public void testSettingTigerColor() throws Throwable {
        String tigerColor = "blue";
        testTiger.setTigerColor(tigerColor);
        Assert.assertEquals(tigerColor, testTiger.getTigerColor());
    }

    @Test
    public void testSettingTigerLocation() throws Throwable {
        Location tigerLocation = new Location(3,4,5);
        testTiger.setTigerLocation(tigerLocation);
        Assert.assertEquals(tigerLocation, testTiger.getTigerLocation());
    }

    @Test
    public void testSetTigerStatus() throws Throwable {
        String status = "not_yet_played";
        testTiger.setTigerStatus(status);
        Assert.assertEquals(status, testTiger.getTigerStatus());
    }
}
