package game;

import game.player.Player;
import game.world.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    Player player;

    @Before
    public void setupPlayer(){
        World world = new World();
        player = new Player(world);
    }

    @Test
    public void startUpScore(){
        Assert.assertEquals(0, player.getScore());
    }
}
