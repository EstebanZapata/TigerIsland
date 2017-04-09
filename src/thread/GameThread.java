package thread;

import game.Game;
import game.settlements.BuildAction;
import game.tile.Location;
import game.tile.orientation.TileOrientation;
import thread.message.GameActionMessage;
import thread.message.GameCommandMessage;
import thread.message.Message;

import java.util.concurrent.BlockingQueue;

public class GameThread extends MyThread {
    public static final int MILLISECONDS_PER_SECOND = 1000;
    public static final double TIME_TO_TAKE_ACTION_SAFETY_BUFFER = 0.8;

    private BlockingQueue<Message> gameMessageQueue;
    private BlockingQueue<Message> gameResponseQueue;

    private Game game;

    private String myPlayerId;
    private String opponentPlayerId;

    private String gameId;

    public GameThread(GameThreadCommunication communication, String myPlayerId, String opponentPlayerId, String gameId) {
        super();

        this.gameMessageQueue = communication.getGameMessageQueue();
        this.gameResponseQueue = communication.getGameResponseQueue();

        this.game = new Game();

        this.myPlayerId = myPlayerId;
        this.opponentPlayerId = opponentPlayerId;

        this.gameId = gameId;
    }

    @Override
    public void run()  {
        while(!stop) {
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
        Message response = null;
        if (message instanceof GameCommandMessage) {
            System.out.println("Game " + gameId + " received command");
            response = processCommand((GameCommandMessage) message);

            gameResponseQueue.add(response);
        }

        if (message instanceof GameActionMessage) {
            System.out.println("Game " + gameId + " received opponent action");
            processOpponentAction((GameActionMessage) message);
        }


    }

    private Message processCommand(GameCommandMessage message) {
        String gameId = message.getGameId();

        double secondsToTakeAction = message.getMoveTime();

        int moveNumber = message.getMoveNumber();

        int millisecondsToTakeAction = (int) Math.floor(secondsToTakeAction * MILLISECONDS_PER_SECOND);
        int safeMillisecondsToTakeAction = (int) Math.floor(millisecondsToTakeAction * TIME_TO_TAKE_ACTION_SAFETY_BUFFER);

        try {
            Thread.sleep(safeMillisecondsToTakeAction);
        }
        catch (InterruptedException e) {

        }

        Message mockResponse = new GameActionMessage(gameId, moveNumber, myPlayerId, message.getTileToPlace(), new Location(1,0,0), TileOrientation.EAST_NORTHEAST, BuildAction.BUILT_TIGER_PLAYGROUND, new Location(3,0,0), null);

        return mockResponse;
    }

    private void processOpponentAction(GameActionMessage message) {


    }

}
