package pieces;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.Location;

/**
 * Created by thomasbaldwin on 3/24/17.
 */

public class VillagerTest {
    private villager testVillager;

    @Before
    public void setupVillager() {
        testVillager = new villager(1, "blue", new Location(3,4,5), "in_play");
    }

    @Test
    public void testSettingVillagerId() throws Throwable {
        int villagerId = 1;
        testVillager.setVillagerID(villagerId);
        Assert.assertEquals(villagerId, testVillager.getVillagerID());
    }

    @Test
    public void testSettingVillagerColor() throws Throwable {
        String totoroColor = "blue";
        testVillager.setVillagerColor(totoroColor);
        Assert.assertEquals(totoroColor, testVillager.getVillagerColor());
    }

    @Test
    public void testSettingVillagerLocation() throws Throwable {
        Location villagerLocation = new Location(3,4,5);
        testVillager.setVillagerLocation(villagerLocation);
        Assert.assertEquals(villagerLocation, testVillager.getVillagerLocation());
    }

    @Test
    public void testSetVillagerStatus() throws Throwable {
        String status = "not_yet_played";
        testVillager.setVillagerStatus(status);
        Assert.assertEquals(status, testVillager.getVillagerStatus());
    }
}
