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
    private Tile tile;
    private World world;

    @Given("^I have a tile$")
    public void i_have_a_tile() {
        world = new World();
        tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
    }

    @Given("^No other tiles have been placed$")
    public void no_other_tiles_have_been_placed() {
        Assert.assertEquals(0, world.getAllHexesInWorld().size());
    }

    @When("^I place the tile$")
    public void i_place_the_tile() throws Throwable {
        world.placeFirstTile();
    }

    @Then("^The tile should be placed on the board$")
    public void the_should_be_placed_on_the_board() throws Throwable {
        Hex volcanoHex = tile.getVolcanoHex();
        Hex leftHex = tile.getLeftHexRelativeToVolcano();
        Hex rightHex = tile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(0,0,0));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(1,1,0));
        Assert.assertEquals(rightHex, world.getHexByCoordinate(0,1,0));
    }
}
