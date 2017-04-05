package pieces;

import game.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PieceContainerTest {
    private PieceContainer testBag;
    Player player1 = new Player();
    @Before
    public void setUpPieceContainer(){this.testBag = new PieceContainer();}

    @Test
    public void tigersStartingCount(){
        Assert.assertEquals(2,testBag.getRemainingTigerCount());
    }
    @Test
    public void totoroStartingCount(){
        Assert.assertEquals(3,testBag.getRemainingTotoroCount());
    }
    @Test
    public void villagersStartingCount(){
        Assert.assertEquals(20,testBag.getRemainingVillagerCount());
    }

    @Test
    public void playingATiger(){
        this.testBag.playATiger();
        Assert.assertEquals(1,this.testBag.getRemainingTigerCount());
    }
    @Test
    public void playingATotoro(){
        this.testBag.playATotoro();
        Assert.assertEquals(2,this.testBag.getRemainingTotoroCount());
    }
    @Test
    public void playingAVillager(){
        this.testBag.playAVillager();
        Assert.assertEquals(19,this.testBag.getRemainingVillagerCount());
    }

}
