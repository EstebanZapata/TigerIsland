package acceptance.java;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.Game;
import game.settlements.BuildAction;
import game.tile.Location;
import game.tile.Terrain;
import game.tile.Tile;
import game.tile.orientation.TileOrientation;
import game.world.rules.exceptions.IllegalTilePlacementException;
import org.junit.Assert;
import thread.message.GameActionMessage;
import thread.message.GameCommandMessage;
import thread.message.Message;

import static game.settlements.BuildAction.FOUNDED_SETTLEMENT;

public class AiDecision {
    private Game myGame = new Game();
    private Tile myTile = new Tile(Terrain.JUNGLE,Terrain.ROCKY);
    private GameCommandMessage message = new GameCommandMessage("GAMEID",1.5,1,myTile);
    private GameActionMessage aiResponse;

    @Given("^a tile to place into a board$")
    public void a_tile_to_place_into_a_board()  {
        Assert.assertNotEquals(null,myTile.toString());
    }

    @When("^a board exists$")
    public void a_board_exists() {
        Assert.assertEquals(false,myGame.world.getAllHexesInWorld().isEmpty());
    }

    @Then("^place the tile and found a settlement$")
    public void place_the_tile_and_found_a_settlement() throws IllegalTilePlacementException {
        String gameId = "GAMEID";
        int moveNumber = 1;
        String myPlayerId = "PLAYERID";

        aiResponse = myGame.ai.chooseMove(gameId, moveNumber, myPlayerId, message.getTileToPlace());
        Location myLocation = new Location(-3,-1,0);
        Assert.assertEquals(FOUNDED_SETTLEMENT,aiResponse.getBuildActionPerformed());
        Assert.assertEquals("GAMEID",aiResponse.getGameId());
        Assert.assertEquals(myLocation,aiResponse.getLocationOfBuildAction());
        Assert.assertEquals(1,aiResponse.getMoveNumber());
        Assert.assertEquals(TileOrientation.NORTHWEST_WEST,aiResponse.getTileOrientationPlaced());

    }



}
