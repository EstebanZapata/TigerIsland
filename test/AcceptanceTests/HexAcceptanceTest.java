package AcceptanceTests;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tile.Hex;
import tile.Tile;

import static tile.Terrain.JUNGLE;
import static tile.Terrain.LAKE;
import static tile.Terrain.VOLCANO;


public class HexAcceptanceTest {
    private Tile testHexOwner;
    private Hex[] hexes;

    @Given("^Hex Terrain type is volcano$")
    public void hex_Terrain_type_is_volcano() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        testHexOwner = new Tile(JUNGLE, LAKE);
    }

    @When("^I check the Terrain type$")
    public void i_check_the_Terrain_type() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        hexes[0] = testHexOwner.getVolcanoHex();
    }

    @Then("^I see type is volcano$")
    public void i_see_type_is_volcano() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        final boolean b = hexes[0].getTerrain() == VOLCANO;
        Assert.assertEquals(true, b);
    }

}
