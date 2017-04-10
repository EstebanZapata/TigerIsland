package acceptance.java.world;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.tile.FirstTile;
import game.tile.Tile;
import org.junit.Assert;
import game.tile.Terrain;

import game.world.World;

public class FirstTileIsAlreadyPlaced {
    private World world;
    private Tile firstTile;

    @Given("^A new board$")
    public void no_other_tiles_have_been_placed() {
        world = new World();

    }

    @When("^I check that the fist tile exists$")
    public void i_place_the_tile() throws Throwable {
        firstTile = world.getHexByCoordinate(0,0,0).getOwner();
    }

    @Then("^Then the special tile should exist with the volcano at the center$")
    public void the_should_be_placed_on_the_board() throws Throwable {
        Assert.assertTrue(firstTile instanceof FirstTile);
        Assert.assertEquals(Terrain.VOLCANO, world.getHexByCoordinate(0,0,0).getTerrain());

    }
}
