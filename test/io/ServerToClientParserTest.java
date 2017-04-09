package io;

import game.settlements.BuildAction;
import game.tile.Location;
import game.tile.Terrain;
import game.tile.orientation.TileOrientation;
import org.junit.Assert;
import org.junit.Test;
import thread.message.*;

public class ServerToClientParserTest {


    @Test
    public void testWelcomeMessageReturnsCorrectClientMessage() {
        String welcome = "WELCOME TO ANOTHER EDITION OF THUNDERDOME!";

        ClientMessage messageParsed = (ClientMessage) ServerToClientParser.parseServerInputAndComposeMessage(welcome);

        String message = messageParsed.getInformation();

        Assert.assertTrue(message.contains("ENTER THUNDERDOME"));
        Assert.assertTrue(message.split(" ").length == 3);
    }

    @Test
    public void testTwoShallEnterMessageReturnsCorrectClientMessage() {
        String twoShallEnter = "TWO SHALL ENTER, ONE SHALL LEAVE";

        ClientMessage messageParsed = (ClientMessage) ServerToClientParser.parseServerInputAndComposeMessage(twoShallEnter);

        String message = messageParsed.getInformation();

        Assert.assertTrue(message.contains("I AM"));
        Assert.assertTrue(message.split(" ").length == 4);
    }

    @Test
    public void testWaitForTournamentReturnsPlayerId() {
        String waitForTournament = "WAIT FOR THE TOURNAMENT TO BEGIN spagett";

        PlayerIdMessage messageParsed = (PlayerIdMessage) ServerToClientParser.parseServerInputAndComposeMessage(waitForTournament);

        Assert.assertTrue(messageParsed.getPlayerId().equals("spagett"));
    }

    @Test
    public void testNewChallengeReturnsNoAction() {
        String newChallenge = "NEW CHALLENGE 69 YOU WILL PLAY 3 MATCHES";

        Message messageParsed = ServerToClientParser.parseServerInputAndComposeMessage(newChallenge);

        Assert.assertEquals(messageParsed, Message.NO_ACTION);
    }

    @Test
    public void testBeginRoundReturnsNoAction() {
        String beginRound = "BEGIN ROUND 2 OF 3";

        Message messageParsed = ServerToClientParser.parseServerInputAndComposeMessage(beginRound);

        Assert.assertEquals(messageParsed, Message.NO_ACTION);
    }

    @Test
    public void testNewMatchReturnsNewMatchMessage() {
        String newMatch = "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER yoyo";

        NewMatchMessage messageParsed = (NewMatchMessage) ServerToClientParser.parseServerInputAndComposeMessage(newMatch);

        Assert.assertTrue(messageParsed.getOpponentId().equals("yoyo"));
    }

    @Test
    public void testMakeYourMoveReturnsCommandMessage() {
        String makeYourMove = "MAKE YOUR MOVE IN GAME six WITHIN 3.5 SECONDS: MOVE 5 PLACE JUNGLE+LAKE";

        GameCommandMessage messageParsed = (GameCommandMessage) ServerToClientParser.parseServerInputAndComposeMessage(makeYourMove);

        Assert.assertEquals("six", messageParsed.getGameId());
        Assert.assertEquals(5, messageParsed.getMoveNumber());

        double expected = 3.5;
        double actual = messageParsed.getMoveTime();
        double delta = Math.abs(expected - actual);

        Assert.assertTrue(delta < 0.001);

        Assert.assertEquals(Terrain.LAKE, messageParsed.getTileToPlace().getLeftHexRelativeToVolcano().getTerrain());
        Assert.assertEquals(Terrain.JUNGLE, messageParsed.getTileToPlace().getRightHexRelativeToVolcano().getTerrain());
    }

    @Test
    public void testServerCoordinateToClientFromOrigin() {
        Location parsedLocation = ServerToClientParser.convertServerCoordinatesToClientLocation("0", "0", "0");

        Assert.assertEquals(new Location(0,0,0), parsedLocation);
    }

    @Test
    public void testServerCoordinateToClientFromArbitrary() {
        Location parsedLocation = ServerToClientParser.convertServerCoordinatesToClientLocation("1", "-3", "2");

        Assert.assertEquals(new Location(1,-2,0), parsedLocation);
    }

    @Test
    public void testServerCoordinateToClientFromAnotherArbitrary() {
        Location parsedLocation = ServerToClientParser.convertServerCoordinatesToClientLocation("-1", "3", "-2");

        Assert.assertEquals(new Location(-1,2,0), parsedLocation);
    }

    @Test
    public void testFoundSettlementActionMessage() {
        String gameMove = "GAME 3 MOVE 5 PLAYER spagett PLACE GRASS+JUNGLE AT 1 -1 3 5 FOUND SETTLEMENT AT 3 4 5";

        GameActionMessage gameActionMessage = (GameActionMessage) ServerToClientParser.parseServerInputAndComposeMessage(gameMove);

        Assert.assertEquals("3", gameActionMessage.getGameId());
        Assert.assertEquals(5, gameActionMessage.getMoveNumber());
        Assert.assertEquals("spagett", gameActionMessage.getPlayerId());

        Assert.assertEquals(Terrain.JUNGLE, gameActionMessage.getTilePlaced().getLeftHexRelativeToVolcano().getTerrain());
        Assert.assertEquals(Terrain.GRASSLANDS, gameActionMessage.getTilePlaced().getRightHexRelativeToVolcano().getTerrain());

        Assert.assertEquals(new Location(1,-3,0), gameActionMessage.getLocationOfVolcano());
        Assert.assertEquals(TileOrientation.WEST_SOUTHWEST, gameActionMessage.getTileOrientationPlaced());

        Assert.assertEquals(BuildAction.FOUNDED_SETTLEMENT, gameActionMessage.getBuildActionPerformed());
        Assert.assertEquals(new Location(3,-5, 0), gameActionMessage.getLocationOfBuildAction());

    }

    @Test
    public void testExpandSettlementActionMessage() {
        String gameMove = "GAME ght MOVE 8 PLAYER yoyo PLACE ROCK+LAKE AT 1 1 0 1 EXPAND SETTLEMENT AT 2 -1 2 GRASS";

        GameActionMessage gameActionMessage = (GameActionMessage) ServerToClientParser.parseServerInputAndComposeMessage(gameMove);

        Assert.assertEquals("ght",gameActionMessage.getGameId());
        Assert.assertEquals(8, gameActionMessage.getMoveNumber());
        Assert.assertEquals("yoyo", gameActionMessage.getPlayerId());

        Assert.assertEquals(Terrain.LAKE, gameActionMessage.getTilePlaced().getLeftHexRelativeToVolcano().getTerrain());
        Assert.assertEquals(Terrain.ROCKY, gameActionMessage.getTilePlaced().getRightHexRelativeToVolcano().getTerrain());

        Assert.assertEquals(new Location(1,0,0), gameActionMessage.getLocationOfVolcano());
        Assert.assertEquals(TileOrientation.NORTHEAST_NORTHWEST, gameActionMessage.getTileOrientationPlaced());

        Assert.assertEquals(BuildAction.EXPANDED_SETTLEMENT, gameActionMessage.getBuildActionPerformed());
        Assert.assertEquals(new Location(2,-2,0), gameActionMessage.getLocationOfBuildAction());

        Assert.assertEquals(Terrain.GRASSLANDS, gameActionMessage.getTerrainExpandedOnto());
    }

    @Test
    public void testBuildTotoroActionMessage() {
        String gameMove = "GAME ght MOVE 8 PLAYER yoyo PLACE ROCK+LAKE AT 1 1 0 2 BUILD TOTORO SANCTUARY AT 2 -1 2";

        GameActionMessage gameActionMessage = (GameActionMessage) ServerToClientParser.parseServerInputAndComposeMessage(gameMove);

        Assert.assertEquals("ght",gameActionMessage.getGameId());
        Assert.assertEquals(8, gameActionMessage.getMoveNumber());
        Assert.assertEquals("yoyo", gameActionMessage.getPlayerId());

        Assert.assertEquals(Terrain.LAKE, gameActionMessage.getTilePlaced().getLeftHexRelativeToVolcano().getTerrain());
        Assert.assertEquals(Terrain.ROCKY, gameActionMessage.getTilePlaced().getRightHexRelativeToVolcano().getTerrain());

        Assert.assertEquals(new Location(1,0,0), gameActionMessage.getLocationOfVolcano());
        Assert.assertEquals(TileOrientation.EAST_NORTHEAST, gameActionMessage.getTileOrientationPlaced());

        Assert.assertEquals(BuildAction.BUILT_TOTORO_SANCTUARY, gameActionMessage.getBuildActionPerformed());
        Assert.assertEquals(new Location(2,-2,0), gameActionMessage.getLocationOfBuildAction());


    }

    @Test
    public void testBuildTigerActionMessage() {
        String gameMove = "GAME ght MOVE 8 PLAYER yoyo PLACE ROCK+LAKE AT 2 1 3 2 BUILD TIGER PLAYGROUND AT 2 -1 -1";

        GameActionMessage gameActionMessage = (GameActionMessage) ServerToClientParser.parseServerInputAndComposeMessage(gameMove);

        Assert.assertEquals("ght",gameActionMessage.getGameId());
        Assert.assertEquals(8, gameActionMessage.getMoveNumber());
        Assert.assertEquals("yoyo", gameActionMessage.getPlayerId());

        Assert.assertEquals(Terrain.LAKE, gameActionMessage.getTilePlaced().getLeftHexRelativeToVolcano().getTerrain());
        Assert.assertEquals(Terrain.ROCKY, gameActionMessage.getTilePlaced().getRightHexRelativeToVolcano().getTerrain());

        Assert.assertEquals(new Location(2,-3,0), gameActionMessage.getLocationOfVolcano());
        Assert.assertEquals(TileOrientation.EAST_NORTHEAST, gameActionMessage.getTileOrientationPlaced());

        Assert.assertEquals(BuildAction.BUILT_TIGER_PLAYGROUND, gameActionMessage.getBuildActionPerformed());
        Assert.assertEquals(new Location(2,1,0), gameActionMessage.getLocationOfBuildAction());

    }

    @Test
    public void testUnableToBuildMessage() {
        String gameMove = "GAME ght MOVE 8 PLAYER yoyo PLACE ROCK+LAKE AT 2 1 3 2 UNABLE TO BUILD";

        GameActionMessage gameActionMessage = (GameActionMessage) ServerToClientParser.parseServerInputAndComposeMessage(gameMove);

        Assert.assertEquals("ght",gameActionMessage.getGameId());
        Assert.assertEquals(8, gameActionMessage.getMoveNumber());
        Assert.assertEquals("yoyo", gameActionMessage.getPlayerId());

        Assert.assertEquals(Terrain.LAKE, gameActionMessage.getTilePlaced().getLeftHexRelativeToVolcano().getTerrain());
        Assert.assertEquals(Terrain.ROCKY, gameActionMessage.getTilePlaced().getRightHexRelativeToVolcano().getTerrain());

        Assert.assertEquals(new Location(2,-3,0), gameActionMessage.getLocationOfVolcano());
        Assert.assertEquals(TileOrientation.EAST_NORTHEAST, gameActionMessage.getTileOrientationPlaced());

        Assert.assertEquals(BuildAction.UNABLE_TO_BUILD, gameActionMessage.getBuildActionPerformed());

    }

    @Test
    public void testForfeitIllegalTileReturnsGameEndMessage() {
        String message = "GAME 3 MOVE 5 PLAYER spagett FORFEITED: ILLEGAL TILE PLACEMENT";

        GameEndMessage gameEndMessage = (GameEndMessage) ServerToClientParser.parseServerInputAndComposeMessage(message);

        Assert.assertEquals("3", gameEndMessage.getGameId());

    }

    @Test
    public void testForfeitIllegalBuildReturnsGameEndMessage() {
        String message = "GAME ionio MOVE 5 PLAYER spagett FORFEITED: ILLEGAL BUILD";

        GameEndMessage gameEndMessage = (GameEndMessage) ServerToClientParser.parseServerInputAndComposeMessage(message);

        Assert.assertEquals("ionio", gameEndMessage.getGameId());

    }

    @Test
    public void testForfeitTimeoutReturnsGameEndMessage() {
        String message = "GAME niojk MOVE 5 PLAYER spagett FORFEITED: TIMEOUT";

        GameEndMessage gameEndMessage = (GameEndMessage) ServerToClientParser.parseServerInputAndComposeMessage(message);

        Assert.assertEquals("niojk", gameEndMessage.getGameId());

    }

    @Test
    public void testLostReturnsGameEndMessage() {
        String message = "GAME niniooojk MOVE 5 PLAYER spagett LOST: UNABLE TO BUILD";

        GameEndMessage gameEndMessage = (GameEndMessage) ServerToClientParser.parseServerInputAndComposeMessage(message);

        Assert.assertEquals("niniooojk", gameEndMessage.getGameId());

    }

    @Test
    public void testGameOverReturnsGameEndMessage() {
        String message = "GAME yee OVER PLAYER 69 23 PLAYER 90 90";

        GameEndMessage gameEndMessage = (GameEndMessage) ServerToClientParser.parseServerInputAndComposeMessage(message);

        Assert.assertEquals("yee", gameEndMessage.getGameId());
    }


    @Test
    public void testEndOfRoundReturnsNoAction() {
        String message = "END OF ROUND 3 OF 4";

        Message messageParsed = ServerToClientParser.parseServerInputAndComposeMessage(message);

        Assert.assertEquals(Message.NO_ACTION, messageParsed);
    }

    @Test
    public void testWaitForNextChallengeReturnsNoAction() {
        String message = "WAIT FOR THE NEXT CHALLENGE TO BEGIN";

        Message messageParsed = ServerToClientParser.parseServerInputAndComposeMessage(message);

        Assert.assertEquals(Message.NO_ACTION, messageParsed);
    }

    @Test
    public void testThankYouReturnsDisconnect() {
        String message = "THANK YOU FOR PLAYING! GOODBYE";

        Message messageParsed = ServerToClientParser.parseServerInputAndComposeMessage(message);

        Assert.assertTrue(messageParsed instanceof DisconnectMessage);
    }
}
