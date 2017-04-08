package thread;

import game.Game;
import game.tile.Location;
import game.tile.orientation.TileOrientation;

import java.util.concurrent.BlockingQueue;

public class GameThread extends Thread {
    public static final int MILLISECONDS_PER_SECOND = 1000;
    public static final double TIME_TO_TAKE_ACTION_SAFETY_BUFFER = 0.8;

    private BlockingQueue<Message> gameMessageQueue;
    private BlockingQueue<Message> gameResponseQueue;

    private String gameId;

    private Game game;

    public GameThread(BlockingQueue<Message> gameMessageQueue, BlockingQueue<Message> gameResponseQueue, String gameId) {
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

        if (message instanceof GameActionMessage) {
            processOpponentAction((GameActionMessage) message);
        }

        gameResponseQueue.add(response);


    }



    private Message processCommand(GameCommandMessage message) {
        double secondsToTakeAction = message.getMoveTime();

        int millisecondsToTakeAction = (int) Math.floor(secondsToTakeAction * MILLISECONDS_PER_SECOND);
        int safeMillisecondsToTakeAction = (int) Math.floor(millisecondsToTakeAction * TIME_TO_TAKE_ACTION_SAFETY_BUFFER);

        try {
            Thread.sleep(safeMillisecondsToTakeAction);
        }
        catch (InterruptedException e) {

        }

        Message response = new GameActionMessage("PlaterID", message.getTileToPlace(), new Location(1,0,0), TileOrientation.EAST_NORTHEAST, BuildAction.BUILT_TIGER_PLAYGROUND, new Location(3,0,0), null);

        return response;
    }

    private void processOpponentAction(GameActionMessage message) {

    }

    public String getGameId() {
        return this.gameId;
    }


}
