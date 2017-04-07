package acceptance.java.world;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.world.CoordinateSystemHelper;
import game.world.World;
import org.junit.Assert;
import tile.Hex;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientation;

public class SuccessfullyPlacingATileOnAHigherLayer {
    private World world;
    private Tile upperTile;
    private Location[] locationOfHexes;


    @Given("^a board with at least two tiles$")
    public void a_board_with_at_least_two_tiles() throws Throwable {
        world = new World();
        Tile tileOne = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tileTwo = new Tile(Terrain.LAKE, Terrain.ROCKY);
        world.placeFirstTile();
        world.insertTileIntoTileManager(tileTwo, new Location(1,0,0), TileOrientation.EAST_NORTHEAST);
    }

    @When("^I attempt to place the tile on the layer higher than those two tiles$")
    public void i_place_the_tile_on_the_layer_higher_than_those() throws Throwable {
        upperTile = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);

        Location locationOfVolcano = new Location(0,0,1);

        locationOfHexes = new Location[3];
        locationOfHexes[0] = locationOfVolcano;
        locationOfHexes[1] = CoordinateSystemHelper.getTentativeLeftHexLocation(locationOfVolcano, TileOrientation.EAST_NORTHEAST);
        locationOfHexes[2] = CoordinateSystemHelper.getTentativeRightHexLocation(locationOfVolcano, TileOrientation.EAST_NORTHEAST);
    }

    @When("^it does not completely overlap a tile$")
    public void it_does_not_completely_overlap_a_tile() throws Throwable {
         Assert.assertEquals(true, world.tileRulesManager.tileDoesNotLieCompletelyOnAnother(locationOfHexes));
    }

    @When("^the upper tile volcano covers a lower volcano$")
    public void the_upper_tile_volcano_covers_a_lower_volcano() throws Throwable {
        Assert.assertEquals(true, world.tileRulesManager.topVolcanoCoversOneBelow(locationOfHexes[0]));
    }

    @When("^there is no air gap below the tile$")
    public void there_is_no_air_gap_below_the_tile() throws Throwable {
        Assert.assertEquals(true, world.tileRulesManager.noAirBelowTile(locationOfHexes));
    }

    @Then("^The tile should be placed on the board on the higher layer$")
    public void the_should_be_placed_on_the_board() throws Throwable {
        world.insertTileIntoTileManager(upperTile, new Location(0,0,1), TileOrientation.EAST_NORTHEAST);

        Hex volcanoHex = upperTile.getVolcanoHex();
        Hex leftHex = upperTile.getLeftHexRelativeToVolcano();
        Hex rightHex = upperTile.getRightHexRelativeToVolcano();

        Assert.assertEquals(volcanoHex, world.getHexByCoordinate(0,0,1));
        Assert.assertEquals(leftHex, world.getHexByCoordinate(1,0,1));
        Assert.assertEquals(rightHex, world.getHexByCoordinate(1,1,1));
    }

}
