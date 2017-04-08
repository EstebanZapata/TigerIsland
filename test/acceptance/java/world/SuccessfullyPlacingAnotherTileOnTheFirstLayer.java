package acceptance.java.world;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.world.*;
import game.world.rules.exceptions.*;
import org.junit.Assert;
import game.tile.Location;
import game.tile.Terrain;
import game.tile.Tile;
import game.tile.orientation.TileOrientation;

public class SuccessfullyPlacingAnotherTileOnTheFirstLayer {
    private World world;
    private Tile tile;
    private Location[] locationOfTile;

    @Given("^a non-empty board$")
    public void a_non_empty_board() throws IllegalTilePlacementException {
        world = new World();
        world.placeFirstTile();
    }

    @When("^I place the tile on the first layer$")
    public void i_place_the_tile_on_the_first_layer() throws IllegalTilePlacementException {
        tile = new Tile(Terrain.LAKE, Terrain.ROCKY);
        world.insertTileIntoTileManager(tile, new Location(1,0,0), TileOrientation.EAST_NORTHEAST);
    }

    @When("^it is adjacent to an existing tile$")
    public void it_is_adjacent_to_an_existing_tile() throws IllegalTilePlacementException {
        locationOfTile = new Location[3];
        locationOfTile[0] = tile.getVolcanoHex().getLocation();
        locationOfTile[1] = tile.getLeftHexRelativeToVolcano().getLocation();
        locationOfTile[2] = tile.getRightHexRelativeToVolcano().getLocation();

        Assert.assertEquals(true, world.tileRulesManager.tileIsAdjacentToAnExistingTile(locationOfTile));
    }

    @When("^it is not overlapping another tile$")
    public void it_is_not_overlapping_another_tile() throws IllegalTilePlacementException {
        Assert.assertEquals(true, world.tileRulesManager.tileIsAdjacentToAnExistingTile(locationOfTile));
    }

    @Then("^the tile should be placed on the board adjacent to an existing tile$")
    public void the_tile_should_be_placed_on_the_board_adjacent_to_an_existing_tile() throws NoHexAtLocationException {
        Assert.assertEquals(tile.getVolcanoHex(), world.getHexByCoordinate(1,0,0));
    }

}
