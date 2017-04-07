package AcceptanceTests.tile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.world.rules.exceptions.NoHexAtLocationException;
import game.world.rules.exceptions.TileNotAdjacentToAnotherException;
import game.world.World;
import org.junit.Assert;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientation;

public class IllegallyPlacingAnotherTileOnTheFirstLayerViaNoAdjacency {
    private World world;
    private Tile tileOne;
    private Tile tileTwo;
    private Location[] locationOfTileTwo;

    @Given("^a board with at least one tile$")
    public void a_board_with_at_least_one_tile() throws Throwable {
        world = new World();
        tileOne = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);
        world.placeFirstTile();
    }

    @When("^I attempt to place the tile on the first layer$")
    public void i_attempt_to_place_the_tile_on_the_first_layer() throws Throwable {
        try {
            world.attemptToInsertTileIntoTileManager(tileTwo, new Location(2,0,0), TileOrientation.EAST_NORTHEAST);
        }
        catch (TileNotAdjacentToAnotherException e) {
        }

    }

    @When("^it is not adjacent to an existing tile$")
    public void it_is_not_adjacent_to_an_existing_tile() throws Throwable {
        boolean notAdjacent = false;

        try {
            world.attemptToInsertTileIntoTileManager(tileTwo, new Location(2,0,0), TileOrientation.EAST_NORTHEAST);
        }
        catch (TileNotAdjacentToAnotherException e) {
            notAdjacent = true;
        }

        Assert.assertTrue(notAdjacent);
    }

    @Then("^it should not be placed on the board$")
    public void it_should_not_be_placed_on_the_board() throws Throwable {
        boolean noTileAtLocation = false;
        try {
            world.getHexByCoordinate(2,0,0);
        }
        catch (NoHexAtLocationException e) {
            noTileAtLocation = true;
        }

        Assert.assertTrue(noTileAtLocation);
    }
}
