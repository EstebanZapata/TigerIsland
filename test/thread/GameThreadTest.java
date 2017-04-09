package thread;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import game.tile.Terrain;
import game.tile.Tile;
import thread.message.GameActionMessage;
import thread.message.GameCommandMessage;
import thread.message.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameThreadTest {
    private String gameId;
    private String myPlayerId;
    private String opponentPlayerId;

    private int moveNumber;

    private BlockingQueue<Message> gameMessageQueue;
    private BlockingQueue<Message> gameResponseQueue;

    private GameThread gameThread;

    private GameCommandMessage message;

    private double moveTimeInSeconds;

    private Tile tileToPlace;

    private GameCommandMessage gameCommandMessage;

    @Before
    public void setupGameThread() {
        gameId = "Spaghetti";
        myPlayerId = "Spagett";
        opponentPlayerId = "yoyo";

        moveNumber = 1;

        gameMessageQueue = new LinkedBlockingQueue<>();
        gameResponseQueue = new LinkedBlockingQueue<>();

        GameThreadCommunication gameThreadCommunication = new GameThreadCommunication(gameMessageQueue, gameResponseQueue);

        gameThread = new GameThread(gameThreadCommunication, myPlayerId, opponentPlayerId, gameId);

        moveTimeInSeconds = 0.05;

        tileToPlace = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);

        gameCommandMessage = new GameCommandMessage(gameId, moveTimeInSeconds, moveNumber, tileToPlace);
    }

    @Test
    public void testGameThreadObtainsMessage() throws InterruptedException{
        Assert.assertTrue(gameMessageQueue.isEmpty());

        gameMessageQueue.add(gameCommandMessage);

        gameThread.start();

        Thread.sleep(50);

        Assert.assertTrue(gameMessageQueue.isEmpty());

    }

    @Test
    public void testGameThreadReturnsResponseWithinSafetyTimeframe() throws InterruptedException {
        Assert.assertTrue(gameMessageQueue.isEmpty());
        Assert.assertTrue(gameResponseQueue.isEmpty());

        gameMessageQueue.add(gameCommandMessage);

        double moveTimeInMilliseconds = moveTimeInSeconds * GameThread.MILLISECONDS_PER_SECOND;
        double safetyMarginWithTestOverhead = GameThread.TIME_TO_TAKE_ACTION_SAFETY_BUFFER * 1.1;
        double moveTimeWithSafetyAndTestOverhead = moveTimeInMilliseconds * safetyMarginWithTestOverhead;

        gameThread.start();

        Thread.sleep((int) Math.floor(moveTimeWithSafetyAndTestOverhead));

        Assert.assertFalse(gameResponseQueue.isEmpty());
    }


    @Test
    public void testSameGameIdPassedIsReturned() {
        gameThread.start();
        gameMessageQueue.add(gameCommandMessage);

        Message gameResponse = waitForMessage();

        GameActionMessage gameActionMessage = (GameActionMessage)  gameResponse;
        Assert.assertTrue(gameId.equals(gameActionMessage.getGameId()));
    }

    @Test
    public void testSameMoveNumberPassedIsReturned() {
        gameThread.start();
        gameMessageQueue.add(gameCommandMessage);

        GameActionMessage gameResponse = (GameActionMessage) waitForMessage();

        Assert.assertTrue(moveNumber == gameResponse.getMoveNumber());
    }

    private Message waitForMessage() {
        Message gameResponse = null;
        while(true) {
            try {
                gameResponse = gameResponseQueue.take();
            }
            catch (Exception e) {

            }
            if (gameResponse != null) {
                break;
            }
        }

        return gameResponse;
    }
}
