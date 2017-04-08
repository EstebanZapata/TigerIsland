package thread;

import game.Game;
import game.settlements.BuildAction;
import game.tile.Location;
import game.tile.orientation.TileOrientation;
import thread.message.GameActionMessage;
import thread.message.GameCommandMessage;
import thread.message.Message;

import java.util.concurrent.BlockingQueue;

public class GameThread extends Thread {
    public static final int MILLISECONDS_PER_SECOND = 1000;
    public static final double TIME_TO_TAKE_ACTION_SAFETY_BUFFER = 0.8;

    private BlockingQueue<Message> gameMessageQueue;
    private BlockingQueue<Message> gameResponseQueue;

    private Game game;

    private String myPlayerId;
    private String opponentPlayerId;

    private boolean opponentPlayerIdHasNotBeenSet;

    public GameThread(GameThreadCommunication communication, String myPlayerId) {
        super();

        this.gameMessageQueue = communication.getGameMessageQueue();
        this.gameResponseQueue = communication.getGameResponseQueue();

        this.game = new Game();

        this.myPlayerId = myPlayerId;

        opponentPlayerIdHasNotBeenSet = false;
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

        int moveNumber = message.getMoveNumber();

        int millisecondsToTakeAction = (int) Math.floor(secondsToTakeAction * MILLISECONDS_PER_SECOND);
        int safeMillisecondsToTakeAction = (int) Math.floor(millisecondsToTakeAction * TIME_TO_TAKE_ACTION_SAFETY_BUFFER);

        try {
            Thread.sleep(safeMillisecondsToTakeAction);
        }
        catch (InterruptedException e) {

        }

        Message response = new GameActionMessage("PlaterID", moveNumber, myPlayerId, message.getTileToPlace(), new Location(1,0,0), TileOrientation.EAST_NORTHEAST, BuildAction.BUILT_TIGER_PLAYGROUND, new Location(3,0,0), null);

        return response;
    }

    private boolean opponentPlayerIdHasNotBeenSet() {
        return opponentPlayerIdHasNotBeenSet;
    }

    public void setOpponentPlayerId(String opponentPlayerId) {
        this.opponentPlayerId = opponentPlayerId;
        opponentPlayerIdHasNotBeenSet = false;
    }

    private void processOpponentAction(GameActionMessage message) {

    }
}
