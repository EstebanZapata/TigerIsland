package acceptance.java.world;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.world.World;
import game.world.rules.exceptions.IllegalTilePlacementException;
import org.junit.Assert;
import tile.FirstTile;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientation;

public class PlacingAFirstTileThatIsNotTheSpecialFirstTile {
    private World world;
    private Tile tile;

    @Given("^No tiles have been placed$")
    public void no_tiles_have_been_placed() throws Throwable {
        world = new World();

        Assert.assertTrue(world.getAllHexesInWorld().isEmpty());
    }

    @When("^I place the first tile on the board$")
    public void i_place_the_first_tile_on_the_board() throws Throwable {
        tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);

        try {
            world.insertTileIntoTileManager(tile, new Location(0,0,0), TileOrientation.SOUTHWEST_SOUTHEAST);
        }
        catch (IllegalTilePlacementException e) {

        }
    }

    @When("^it is not the special first tile$")
    public void it_is_not_the_special_first_tile() throws Throwable {
        Assert.assertFalse(tile instanceof FirstTile);
    }

    @Then("^that tile cannot be placed on the board$")
    public void that_tile_cannot_be_placed_on_the_board() throws Throwable {
        Assert.assertTrue(world.getAllHexesInWorld().isEmpty());
    }

}
