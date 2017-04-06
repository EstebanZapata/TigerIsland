package acceptance.tile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.world.exceptions.NoHexAtLocationException;
import game.world.exceptions.TopVolcanoDoesNotCoverBottomVolcanoException;
import game.world.World;
import org.junit.Assert;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientation;

public class IllegallyPlacingATileOnAHigherLayerDueToNotCoveringVolcano {
    private World world;
    private Tile tileOne;
    private Tile tileTwo;
    private Tile tileThree;

    @Given("^a board with two or more tiles$")public void a_board_with_two_or_more_tiles() throws Throwable {
        world = new World();
        tileOne = new Tile(Terrain.JUNGLE, Terrain.ROCKY);
        tileTwo = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);

        world.placeFirstTile(tileOne, TileOrientation.SOUTHWEST_SOUTHEAST);
        world.insertTileIntoWorld(tileTwo, new Location(1,0,0), TileOrientation.SOUTHEAST_EAST);
    }

    @When("^I attempt to place the tile on the layer higher than those two$")
    public void i_attempt_to_place_the_tile_on_the_layer_higher_than_those_two() throws Throwable {    // Write code here that turns the phrase above into concrete actions
        tileThree = new Tile(Terrain.LAKE, Terrain.ROCKY);
        try {
            world.insertTileIntoWorld(tileThree, new Location(2,0,1), TileOrientation.NORTHWEST_WEST);
        }
        catch (Exception e) {

        }
    }

    @When("^the upper volcano does not cover the lower volcano$")
    public void the_upper_volcano_does_not_cover_the_lower_volcano() throws Throwable {
        boolean topVolcanoDoesNotCoverABottomVolcano = false;
        try {
            world.insertTileIntoWorld(tileThree, new Location(2,0,1), TileOrientation.WEST_SOUTHWEST);
        }
        catch (TopVolcanoDoesNotCoverBottomVolcanoException e) {
            topVolcanoDoesNotCoverABottomVolcano = true;
        }

        Assert.assertTrue(topVolcanoDoesNotCoverABottomVolcano);
    }

    @Then("^the tile should not be placed$")
    public void the_tile_should_not_be_placed() throws Throwable {
        boolean tileWasNotPlaced = false;
        try {
            world.getHexByCoordinate(2,0,1);
        }
        catch (NoHexAtLocationException e) {
            tileWasNotPlaced = true;
        }

        Assert.assertTrue(tileWasNotPlaced);
    }
}
