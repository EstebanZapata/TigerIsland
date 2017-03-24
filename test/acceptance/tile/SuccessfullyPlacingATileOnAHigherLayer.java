package acceptance.tile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.World;
import org.junit.Assert;
import tile.Hex;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientationRelativeToVolcano;

public class SuccessfullyPlacingATileOnAHigherLayer {
    private World world;
    private Tile upperTile;
    private Location[] locationOfHexes;


    @Given("^a board with at least two tiles$")
    public void a_board_with_at_least_two_tiles() throws Throwable {
        world = new World();
        Tile tileOne = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tileTwo = new Tile(Terrain.LAKE, Terrain.ROCKY);
        world.placeFirstTile(tileOne, TileOrientationRelativeToVolcano.NORTHEAST_NORTHWEST);
        world.insertTileIntoWorld(tileTwo, new Location(1,0,0), TileOrientationRelativeToVolcano.EAST_NORTHEAST);
    }

    @When("^I place the tile on the layer higher than those$")
    public void i_place_the_tile_on_the_layer_higher_than_those() throws Throwable {
        upperTile = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);
        world.insertTileIntoWorld(upperTile, new Location(0,0,1), TileOrientationRelativeToVolcano.EAST_NORTHEAST);

        locationOfHexes = new Location[3];
        locationOfHexes[0] = upperTile.getVolcanoHex().getLocation();
        locationOfHexes[1] = upperTile.getLeftHexRelativeToVolcano().getLocation();
        locationOfHexes[2] = upperTile.getRightHexRelativeToVolcano().getLocation();
    }

    @When("^it does not completely overlap a tile$")
    public void it_does_not_completely_overlap_a_tile() throws Throwable {
        Assert.assertEquals(true, world.tileDoesNotLieCompletelyOnAnother(locationOfHexes));
    }

    @When("^the upper tile volcano covers a lower volcano$")
    public void the_upper_tile_volcano_covers_a_lower_volcano() throws Throwable {
        Assert.assertEquals(true, world.topVolcanoCoversOneBelow(upperTile.getVolcanoHex().getLocation()));
    }

    @When("^there is no air gap below the tile$")
    public void there_is_no_air_gap_below_the_tile() throws Throwable {
        Assert.assertEquals(true, world.noAirBelowTile(locationOfHexes));
    }

    @Then("^The tile should be placed on the board on the higher layer$")
    public void the_should_be_placed_on_the_board() throws Throwable {
        Hex volcanoHex = upperTile.getVolcanoHex();
        Hex leftHex = upperTile.getLeftHexRelativeToVolcano();
        Hex rightHex = upperTile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(0,0,1));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(1,0,1));
        Assert.assertEquals(rightHex, world.getHexByCoordinate(1,1,1));
    }

}
