package UnitTests.tile;

import game.Start;
import game.world.rules.exceptions.SpecialFirstTileHasAlreadyBeenPlacedExeption;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StartTest {
    private Start starter;
    @Before
    public void runStarter(){
        this.starter = new Start();
        String[] args = new String[2];
        args[0] = "one";
        args[1] = "two";
        this.starter.main(args);
    }

    @Test
    public void testStarter(){
        boolean gameStarted = false;
        try {
            gameStarted = this.starter.myGame.world.tileRulesManager.ableToPlaceFirstTile();
        } catch (SpecialFirstTileHasAlreadyBeenPlacedExeption specialFirstTileHasAlreadyBeenPlacedExeption) {
            specialFirstTileHasAlreadyBeenPlacedExeption.printStackTrace();
        }
        Assert.assertEquals(true, gameStarted);
    }

}
