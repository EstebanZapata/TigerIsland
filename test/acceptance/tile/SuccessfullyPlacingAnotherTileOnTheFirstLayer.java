package acceptance.tile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.world.*;
import game.world.rules.exceptions.*;
import org.junit.Assert;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientation;

public class SuccessfullyPlacingAnotherTileOnTheFirstLayer {
    private World world;
    private Tile tileOne;
    private Tile tileTwo;
    private Location[] locationOfTile;

    @Given("^a non-empty board$")
    public void a_non_empty_board() throws IllegalTilePlacementException {
        world = new World();
        tileOne = new Tile(Terrain.GRASSLANDS,Terrain.JUNGLE);
        world.placeFirstTile();
    }

    @When("^I place the tile on  the first layer$")
    public void i_place_the_tile_on_the_first_layer() throws IllegalTilePlacementException {
        tileTwo = new Tile(Terrain.LAKE, Terrain.ROCKY);
        world.attemptToInsertTileIntoTileManager(tileTwo, new Location(2,0,0), TileOrientation.EAST_NORTHEAST);
    }

    @When("^it is adjacent to an existing tile$")
    public void it_is_adjacent_to_an_existing_tile() throws IllegalTilePlacementException {
        locationOfTile = new Location[3];
        locationOfTile[0] = tileTwo.getVolcanoHex().getLocation();
        locationOfTile[1] = tileTwo.getLeftHexRelativeToVolcano().getLocation();
        locationOfTile[2] = tileTwo.getRightHexRelativeToVolcano().getLocation();

        Assert.assertEquals(true, world.tileRulesManager.tileIsAdjacentToAnExistingTile(locationOfTile));
    }

    @When("^it is not overlapping another tile$")
    public void it_is_not_overlapping_another_tile() throws HexAlreadyAtLocationException {

    }

    @Then("^the tile should be placed on the board adjacent to an existing tile$")
    public void the_tile_should_be_placed_on_the_board_adjacent_to_an_existing_tile() throws NoHexAtLocationException {
        Assert.assertEquals(tileTwo.getVolcanoHex(), world.getHexByCoordinate(2,0,0));
    }

}
