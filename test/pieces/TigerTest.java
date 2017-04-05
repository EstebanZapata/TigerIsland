package pieces;

import game.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Location;

/**
 * Created by thomasbaldwin on 3/24/17.
 */

public class TigerTest {
    private Tiger testTiger;
    private Player player1 = new Player();
    @Before
    public void setupTiger() {
        testTiger = new Tiger(player1, new Location(3,4,5), true);
    }


    @Test
    public void testSettingTigerLocation() throws Throwable {
        Location tigerLocation = new Location(3,4,5);
        testTiger.setTigerLocation(tigerLocation);
        Assert.assertEquals(tigerLocation, testTiger.getTigerLocation());
    }

    @Test
    public void testSetTigerStatus() throws Throwable {
        boolean status = false;
        testTiger.setTigerStatus(false);
        Assert.assertEquals(status, testTiger.getTigerStatus());
    }
}
