package game;

import game.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    Player myPlayer;
    @Before
    public void setupPlayer(){
        myPlayer = new Player();
    }

    @Test
    public void startUpScore(){
        Assert.assertEquals(0, myPlayer.getScore());
    }

}
