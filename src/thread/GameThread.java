package thread;

import game.Game;
import game.settlements.BuildAction;
import game.tile.Location;
import game.tile.orientation.TileOrientation;
import game.world.rules.exceptions.IllegalTilePlacementException;
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


        this.myPlayerId = myPlayerId;
        this.opponentPlayerId = opponentPlayerId;
        this.game = new Game(myPlayerId, opponentPlayerId);

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
            try {
                processOpponentAction((GameActionMessage) message);
            } catch (IllegalTilePlacementException e) {
                e.printStackTrace();
            }
        }


    }

    private Message processCommand(GameCommandMessage message) {
        String gameId = message.getGameId();

        int moveNumber = message.getMoveNumber();

            Message aiResponse = null;
            try {
                aiResponse = game.ai.chooseMove(gameId, moveNumber, myPlayerId, message.getTileToPlace());
                return aiResponse;

            } catch (IllegalTilePlacementException e) {
                e.printStackTrace();
                return aiResponse;
            }


    }

    private void processOpponentAction(GameActionMessage message) throws IllegalTilePlacementException {
        game.world.insertTileIntoTileManager(message.getTilePlaced(),message.getLocationOfVolcano(),message.getTileOrientationPlaced());

    }

}
