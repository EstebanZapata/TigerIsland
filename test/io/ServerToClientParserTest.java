package io;

import cucumber.api.java.bm.Tetapi;
import game.tile.Terrain;
import game.tile.Tile;
import org.junit.Assert;
import org.junit.Test;
import thread.message.*;

public class ServerToClientParserTest {


    @Test
    public void testWelcomeMessageReturnsCorrectClientMessage() {
        String welcome = "WELCOME TO ANOTHER EDITION OF THUNDERDOME!";

        ClientMessage messageParsed = (ClientMessage) ServerToClientParser.parseServerInputAndComposeAction(welcome);

        String message = messageParsed.getInformation();

        Assert.assertTrue(message.contains("ENTER THUNDERDOME"));
        Assert.assertTrue(message.split(" ").length == 3);
    }

    @Test
    public void testTwoShallEnterMessageReturnsCorrectClientMessage() {
        String twoShallEnter = "TWO SHALL ENTER, ONE SHALL LEAVE";

        ClientMessage messageParsed = (ClientMessage) ServerToClientParser.parseServerInputAndComposeAction(twoShallEnter);

        String message = messageParsed.getInformation();

        Assert.assertTrue(message.contains("I AM"));
        Assert.assertTrue(message.split(" ").length == 4);
    }

    @Test
    public void testWaitForTournamentReturnsPlayerId() {
        String waitForTournament = "WAIT FOR THE TOURNAMENT TO BEGIN spagett";

        PlayerIdMessage messageParsed = (PlayerIdMessage) ServerToClientParser.parseServerInputAndComposeAction(waitForTournament);

        Assert.assertTrue(messageParsed.getPlayerId().equals("spagett"));
    }

    @Test
    public void testNewChallengeReturnsNoAction() {
        String newChallenge = "NEW CHALLENGE 69 YOU WILL PLAY 3 MATCHES";

        Message messageParsed = ServerToClientParser.parseServerInputAndComposeAction(newChallenge);

        Assert.assertEquals(messageParsed, Message.NO_ACTION);
    }

    @Test
    public void testBeginRoundReturnsNoAction() {
        String beginRound = "BEGIN ROUND 2 OF 3";

        Message messageParsed = ServerToClientParser.parseServerInputAndComposeAction(beginRound);

        Assert.assertEquals(messageParsed, Message.NO_ACTION);
    }

    @Test
    public void testNewMatchReturnsNewMatchMessage() {
        String newMatch = "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER yoyo";

        NewMatchMessage messageParsed = (NewMatchMessage) ServerToClientParser.parseServerInputAndComposeAction(newMatch);

        Assert.assertTrue(messageParsed.getOpponentId().equals("yoyo"));
    }

    @Test
    public void testMakeYourMoveReturnsCommandMessage() {
        String makeYourMove = "MAKE YOUR MOVE IN GAME six WITHIN 3.5 SECONDS: MOVE 5 PLACE JUNGLE+LAKE";

        GameCommandMessage messageParsed = (GameCommandMessage) ServerToClientParser.parseServerInputAndComposeAction(makeYourMove);

        Assert.assertEquals("six", messageParsed.getGameId());
        Assert.assertEquals(5, messageParsed.getMoveNumber());

        double expected = 3.5;
        double actual = messageParsed.getMoveTime();
        double delta = Math.abs(expected - actual);

        Assert.assertTrue(delta < 0.001);

        Assert.assertEquals(Terrain.LAKE, messageParsed.getTileToPlace().getLeftHexRelativeToVolcano().getTerrain());
        Assert.assertEquals(Terrain.JUNGLE, messageParsed.getTileToPlace().getRightHexRelativeToVolcano().getTerrain());
    }

    
}
