import game.Game;
import tile.Terrain;
import tile.Tile;

import java.util.concurrent.BlockingQueue;

public class GameThread extends Thread {
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final double TIME_TO_TAKE_ACTION_SAFETY_BUFFER = 0.9;

    private BlockingQueue<Message> gameMessageQueue;
    private BlockingQueue<Message> gameResponseQueue;

    private String gameId;

    private Game game;

    GameThread(BlockingQueue<Message> gameMessageQueue, BlockingQueue<Message> gameResponseQueue, String gameId) {
        super();

        this.gameMessageQueue = gameMessageQueue;
        this.gameResponseQueue = gameResponseQueue;

        this.gameId = gameId;

        this.game = new Game();
    }

    @Override
    public void run()  {
        while(true) {
            Message message = null;
            try {
                message = gameMessageQueue.take();
            }
            catch(Exception e) {

            }

            if(message != null) {
                processMessage(message);
            }
        }
    }

    private void processMessage(Message message) {
        System.out.println("Game received message");

        Message response = null;
        if (message instanceof GameCommandMessage) {
            response = processCommand((GameCommandMessage) message);
        }

        gameResponseQueue.add(response);


    }

    private Message processCommand(GameCommandMessage message) {
        double secondsToTakeAction = message.getMoveTime();

        int millisecondsToTakeAction = (int) Math.floor(secondsToTakeAction * MILLISECONDS_PER_SECOND);
        int safeMillisecondsToTakeAction = (int) Math.floor(millisecondsToTakeAction * TIME_TO_TAKE_ACTION_SAFETY_BUFFER);

        try {
            Thread.sleep(millisecondsToTakeAction);
        }
        catch (InterruptedException e) {

        }

        Message response = new GameCommandMessage("fs", 2.0, new Tile(Terrain.GRASSLANDS,Terrain.LAKE));

        return response;
    }

    public String getGameId() {
        return this.gameId;
    }


}
