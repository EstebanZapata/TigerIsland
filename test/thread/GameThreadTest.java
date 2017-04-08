package thread;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import game.tile.Terrain;
import game.tile.Tile;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameThreadTest {
    private String gameId;

    private BlockingQueue<Message> gameMessageQueue;
    private BlockingQueue<Message> gameResponseQueue;

    private GameThread gameThread;

    private GameCommandMessage message;

    private double moveTimeInSeconds;

    private Tile tileToPlace;

    private GameCommandMessage gameCommandMessage;

    @Before
    public void setupGameThread() {
        gameId = "1";

        gameMessageQueue = new LinkedBlockingQueue<>();
        gameResponseQueue = new LinkedBlockingQueue<>();

        gameThread = new GameThread(gameMessageQueue, gameResponseQueue, gameId);

        moveTimeInSeconds = 0.10;

        tileToPlace = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);

        gameCommandMessage = new GameCommandMessage(gameId, moveTimeInSeconds, tileToPlace);
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
}
