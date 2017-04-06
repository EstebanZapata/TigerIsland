package acceptance.tile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.world.exceptions.HexAlreadyAtLocationException;
import game.world.exceptions.NoHexAtLocationException;
import game.world.World;
import org.junit.Assert;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientation;

public class IllegallyPlacingAnotherTileOnTheFirstLayerViaOverlappingAnExistingTile {
    private World world;
    private Tile tileOne;
    private Tile tileTwo;

    @Given("^a board with at least one tile on it$")
    public void a_board_with_at_least_one_tile_on_it() throws Throwable {
        world = new World();
        tileOne = new Tile(Terrain.LAKE, Terrain.JUNGLE);
        world.placeFirstTile(tileOne, TileOrientation.SOUTHWEST_SOUTHEAST);

    }

    @When("^I attempt to place my tile on the first layer$")
    public void i_attempt_to_place_my_tile_on_the_first_layer() throws Throwable {
        tileTwo = new Tile(Terrain.ROCKY, Terrain.GRASSLANDS);
        try {
            world.insertTileIntoWorld(tileTwo, new Location(1,0,0), TileOrientation.SOUTHWEST_SOUTHEAST);
        }
        catch (HexAlreadyAtLocationException e) {

        }
    }


    @When("^it overlaps an existing tile$")
    public void it_overlaps_an_existing_tile() throws Throwable {
        boolean overlapsAnExistingTile = false;

        try {
            world.insertTileIntoWorld(tileTwo, new Location(1,0,0), TileOrientation.SOUTHWEST_SOUTHEAST);
        }
        catch (HexAlreadyAtLocationException e) {
            overlapsAnExistingTile = true;
        }

        Assert.assertTrue(overlapsAnExistingTile);
    }

    @Then("^it should not be placed on the board in that location$")
    public void it_should_not_be_placed_on_the_board_in_that_location() throws Throwable {
        boolean tileWasNotPlaced = false;

        try {
            world.getHexByCoordinate(1,0,0);
        }
        catch (NoHexAtLocationException e) {
            tileWasNotPlaced = true;
        }

        Assert.assertTrue(tileWasNotPlaced);
    }

}
