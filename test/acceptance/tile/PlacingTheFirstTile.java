package acceptance.tile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tile.Hex;
import tile.Terrain;
import tile.Tile;

import game.world.World;
import tile.orientation.TileOrientation;

public class PlacingTheFirstTile {
    private World world;

    @Given("^No other tiles have been placed$")
    public void no_other_tiles_have_been_placed() {
        world = new World();

        Assert.assertEquals(0, world.getAllHexesInWorld().size());
    }

    @When("^I place the first tile$")
    public void i_place_the_tile() throws Throwable {
        world.placeFirstTile();
    }

    @Then("^The tile should be placed on the board with the volcano at the origin$")
    public void the_should_be_placed_on_the_board() throws Throwable {
        Assert.assertEquals(Terrain.VOLCANO, world.getHexByCoordinate(0,0,0).getTerrain());

    }
}
