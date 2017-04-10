package acceptance.java.settlements;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.Game;
import game.settlements.Settlement;
import game.settlements.SettlementManager;
import game.settlements.exceptions.*;
import game.tile.Hex;
import game.tile.Terrain;
import org.junit.Assert;

public class FoundASettlement {
    private Game myGame;

    @Given("^a tile is on the board$")
    public void a_tile_is_on_the_board()  {
    myGame = new Game();
        Assert.assertEquals(false, myGame.world.getAllHexesInWorld().isEmpty());
    }

    @When("^I want to found a settlement$")
    public void i_want_to_found_a_settlement()  {
        String desire = "I wish to found a settlement";
    }

    @Then("^the settlement is founded$")
    public void the_settlement_is_founded() throws SettlementAlreadyExistsOnHexException, NoSettlementOnHexException, SettlementCannotBeBuiltOnVolcanoException, NoHexesToExpandToException, NoPlayableHexException, NoSettlementOnAdjacentHexesException, SettlementAlreadyHasTigerPlaygroundException, SettlementAlreadyHasTotoroSanctuaryException, SettlementDoesNotSizeRequirementsException {
        SettlementManager manager = new SettlementManager(myGame.world);
        Hex target = myGame.world.getAllHexesInWorld().get(0);
        manager.foundSettlement(target);
        Settlement mysettle = manager.getSettlementFromHex(target);
        Assert.assertEquals(true,mysettle.containsHex(target));
        Assert.assertEquals(1, manager.getNumberOfVillagersRequiredToExpand(mysettle, Terrain.JUNGLE));
        Hex nextTarget = myGame.world.getAllHexesInWorld().get(1);
        Assert.assertEquals(nextTarget, manager.chooseHexForSettlement());
        manager.buildTigerPlayground(nextTarget);
        manager.buildTotoroSanctuary(nextTarget);
        Assert.assertEquals(target,mysettle.getHexesFromSettlement().get(0));

    }
}
