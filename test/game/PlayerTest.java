package game;

import game.world.*;
import game.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    Player myPlayer;
    World myWorld;

    @Before
    public void setupPlayer(){
        this.myWorld = new World();
        this.myPlayer = new Player(this.myWorld);
    }

    @Test
    public void startUpScore(){
        Assert.assertEquals(0, myPlayer.getScore());
    }

}
