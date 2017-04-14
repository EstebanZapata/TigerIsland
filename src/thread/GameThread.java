package thread;

import game.Game;
import game.player.Player;
import game.settlements.BuildAction;
import game.settlements.Settlement;
import game.tile.Hex;
import game.tile.Location;
import game.tile.Terrain;
import game.tile.Tile;
import game.tile.orientation.TileOrientation;
import game.world.CoordinateSystemHelper;
import game.world.World;
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
        }
        catch (IllegalTilePlacementException e) {
            e.printStackTrace();
            return aiResponse;
        }
    }

    private void processOpponentAction(GameActionMessage message) throws IllegalTilePlacementException {
        World world = game.world;
        Player playerMakingMove = game.opponent;

        Tile tilePlaced = message.getTilePlaced();
        Location locationOfVolcano = message.getLocationOfVolcano();
        TileOrientation orientationOfTilePlaced = message.getTileOrientationPlaced();
        world.insertTileIntoTileManager(tilePlaced, locationOfVolcano, orientationOfTilePlaced);

        Location fakeLocationOfBuildAction = message.getLocationOfBuildAction();

        int xCoordinateOfBuild = fakeLocationOfBuildAction.getxCoordinate();
        int yCoordinateOfBuild = fakeLocationOfBuildAction.getyCoordinate();

        Hex hexBeingBuiltOn = world.getHexRegardlessOfHeight(xCoordinateOfBuild, yCoordinateOfBuild);
        try {
            switch (message.getBuildActionPerformed()) {
                case FOUNDED_SETTLEMENT:
                    game.foundSettlement(playerMakingMove, hexBeingBuiltOn);
                    break;
                case EXPANDED_SETTLEMENT:
                    Settlement settlementBeingExpanded = playerMakingMove.settlementManager.getSettlementFromHex(hexBeingBuiltOn);
                    Terrain terrainExpandedOnto = message.getTerrainExpandedOnto();
                    game.expandSettlement(playerMakingMove, settlementBeingExpanded, terrainExpandedOnto);
                    break;
                case BUILT_TIGER_PLAYGROUND:
                    game.buildTigerPlayground(playerMakingMove, hexBeingBuiltOn);
                    break;
                case BUILT_TOTORO_SANCTUARY:
                    game.buildTotoroSanctuary(playerMakingMove, hexBeingBuiltOn);
                    break;
                case UNABLE_TO_BUILD:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
