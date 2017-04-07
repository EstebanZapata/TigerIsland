package AcceptanceTests;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tile.Hex;
import tile.Terrain;
import tile.Tile;

public class Habitability {
    private Tile myTile;
    private boolean isHabitable;
    @Given("^I have a new tile$")
    public void i_have_a_tile() throws Throwable {
        this.myTile = new Tile(Terrain.JUNGLE, Terrain.LAKE );
    }

    @When("^I check if a volcano is habitable$")
    public void i_check_if_a_volcano_is_habitable() throws Throwable {
        Hex myVolcano = myTile.getVolcanoHex();
        this.isHabitable = myVolcano.getTerrain().isHabitable();
    }

    @Then("^it is not habitable$")
    public void it_is_not_habitable() throws Throwable {
        Assert.assertEquals(false, this.isHabitable);
    }

}
