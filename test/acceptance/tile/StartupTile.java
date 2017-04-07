package acceptance.tile;
import game.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class StartupTile {
    private Start myStarter;

    @Given("^I am player(\\d+)$")
    public void i_am_player(int arg1) throws Throwable {
    }

    @When("^The game starts$")
    public void the_game_starts() throws Throwable {
        this.myStarter = new Start();
        String[] args = new String[2];
        args[0] = "one";
        args[1] = "two";
        this.myStarter.main(args);
    }

    @Then("^My tile should be placed on the board$")
    public void my_tile_should_be_placed_on_the_board() throws Throwable {
        //boolean gameStarted = this.myStarter.myGame.world.getFirstTileHasBeenPlaced();
        Assert.assertEquals(true, false);
    }
}
