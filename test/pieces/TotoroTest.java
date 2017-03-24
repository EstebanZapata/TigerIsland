package pieces;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Location;

/**
 * Created by thomasbaldwin on 3/24/17.
 */

public class TotoroTest {
    private totoro testTotoro;

    @Before
    public void setupTotoro() {
        testTotoro = new totoro(1, "blue", new Location(3,4,5), "in_play");
    }

    @Test
    public void testSettingTotoroId() throws Throwable {
        int totoroId = 1;
        testTotoro.setTotoroID(totoroId);
        Assert.assertEquals(totoroId, testTotoro.getTotoroID());
    }

    @Test
    public void testSettingTigerColor() throws Throwable {
        String totoroColor = "blue";
        testTotoro.setTotoroColor(totoroColor);
        Assert.assertEquals(totoroColor, testTotoro.getTotoroColor());
    }

    @Test
    public void testSettingTigerLocation() throws Throwable {
        Location totoroLocation = new Location(3,4,5);
        testTotoro.setTotoroLocation(totoroLocation);
        Assert.assertEquals(totoroLocation, testTotoro.getTotoroLocation());
    }

    @Test
    public void testSetTigerStatus() throws Throwable {
        String status = "not_yet_played";
        testTotoro.setTotoroStatus(status);
        Assert.assertEquals(status, testTotoro.getTotoroStatus());
    }
}
