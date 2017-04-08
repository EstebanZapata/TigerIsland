package acceptance.java.world;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.world.World;
import game.world.rules.exceptions.SpecialFirstTileHasAlreadyBeenPlacedExeption;
import org.junit.Assert;
import game.tile.FirstTile;

public class PlacingTheSpecialFirstTileAgain {
    private World world;
    private boolean tileWasNotPlaced;

    @Given("^The special first tile has been placed$")
    public void the_special_first_tile_has_been_placed() throws Throwable {
        world = new World();
        world.placeFirstTile();
        tileWasNotPlaced = false;

        Assert.assertTrue(world.getHexByCoordinate(0,0,0).getOwner() instanceof FirstTile);
    }

    @When("^I place another special first tile$")
    public void i_place_another_special_first_tile() throws Throwable {
        try {
            world.placeFirstTile();
        }
        catch (SpecialFirstTileHasAlreadyBeenPlacedExeption e) {
            tileWasNotPlaced = true;
        }


    }

    @Then("^that tile is not placed on the board$")
    public void that_tile_is_not_placed_on_the_board() throws Throwable {
        Assert.assertTrue(tileWasNotPlaced);
    }
}
