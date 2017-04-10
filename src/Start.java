import io.Client;
import io.ClientToServerParser;
import io.ServerToClientParser;
import thread.GameThread;
import thread.GameThreadCommunication;
import thread.message.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Start {
    private static BlockingQueue<String> stringsFromServerQueue;
    private static BlockingQueue<String> stringsToServerQueue;

    private static String myPlayerId;
    private static String currentOpponentPlayerId;

    private static HashMap<String, GameThread> getGameFromGameId;
    private static HashMap<String, GameThreadCommunication> getGameThreadCommunicationFromId;

    private static Client client;

    public static void main(String[] args) throws Exception {
        stringsFromServerQueue = new LinkedBlockingQueue<>();
        stringsToServerQueue = new LinkedBlockingQueue<>();

        getGameFromGameId = new HashMap<>();
        getGameThreadCommunicationFromId = new HashMap<>();

        client = new Client(args[0], Integer.parseInt(args[1]), stringsFromServerQueue, stringsToServerQueue);
        client.start();

        while(true) {
            runTournamentLoop();
        }

    }

    private static void runTournamentLoop() {
        String stringFromServer = waitForMessageFromServer();
        Message actionToTake = ServerToClientParser.parseServerInputAndComposeMessage(stringFromServer);
    }

    private static String waitForMessageFromServer() {
        while(true) {
            String stringFromServer = null;

            try {
                stringFromServer = stringsFromServerQueue.take();
            }
            catch (InterruptedException e) {
            }

            if (stringFromServer != null) {
                Message actionToTake = ServerToClientParser.parseServerInputAndComposeMessage(stringFromServer);
                handleActionToTake(actionToTake);
            }
        }
    }

    private static void handleActionToTake(Message actionToTake) {
        if (messageIsEmpty(actionToTake)) {
            addEmptyStringToServerQueue();
        }

        else if (actionToTake instanceof ClientMessage) {
            stringsToServerQueue.add(((ClientMessage) actionToTake).getInformation());
            return;
        }

        else if (actionToTake instanceof PlayerIdMessage) {
            myPlayerId = ((PlayerIdMessage) actionToTake).getPlayerId();
            addEmptyStringToServerQueue();
            return;
        }

        else if (actionToTake instanceof NewMatchMessage) {
            currentOpponentPlayerId = ((NewMatchMessage) actionToTake).getOpponentId();
            addEmptyStringToServerQueue();
        }

        else if (actionToTake instanceof GameCommandMessage) {
            GameCommandMessage gameCommandMessage = (GameCommandMessage) actionToTake;

            String gameId = gameCommandMessage.getGameId();

            if (gameHasNotBeenCreated(gameId)) {
                createGameAndUpdateMaps(gameId);
            }

            GameThreadCommunication communication = getGameThreadCommunicationFromId.get(gameId);
            communication.getGameMessageQueue().add(actionToTake);

            Message gameResponseMessage = waitForMessageFromGame(gameId);

            String stringToServer = ClientToServerParser.getStringFromGameActionMessage((GameActionMessage) gameResponseMessage);

            stringsToServerQueue.add(stringToServer);

        }

        else if (actionToTake instanceof GameEndMessage) {
            String gameToEnd = ((GameEndMessage) actionToTake).getGameId();

            getGameFromGameId.remove(gameToEnd);
            getGameThreadCommunicationFromId.remove(gameToEnd);

            addEmptyStringToServerQueue();
        }

        else if (actionToTake instanceof GameActionMessage) {
            GameActionMessage gameActionMessage = (GameActionMessage) actionToTake;

            if (gameActionMessage.getPlayerId().equals(myPlayerId)) {

            }
            else  {
                String gameId = gameActionMessage.getGameId();

                if (gameHasNotBeenCreated(gameId)) {
                    createGameAndUpdateMaps(gameId);
                }

                GameThreadCommunication gameThreadCommunication = getGameThreadCommunicationFromId.get(gameActionMessage.getGameId());
                gameThreadCommunication.getGameMessageQueue().add(actionToTake);
            }

            addEmptyStringToServerQueue();
        }

        else if (actionToTake instanceof DisconnectMessage) {
            client.disconnect();
            addEmptyStringToServerQueue();

            Collection gameThreads = getGameFromGameId.values();

            for (Object gameThreadObject:gameThreads) {
                GameThread gamethread = (GameThread) gameThreadObject;
                gamethread.stopThread();
            }

            getGameFromGameId.clear();
            getGameThreadCommunicationFromId.clear();


            System.exit(0);
        }

    }

    private static Message waitForMessageFromGame(String gameId) {
        while(true) {
            Message messageFromGame = null;

            try {
                messageFromGame = getGameThreadCommunicationFromId.get(gameId).getGameResponseQueue().take();
            }
            catch (InterruptedException e) {
            }

            if (messageFromGame != null) {
                return messageFromGame;
            }
        }
    }

    private static void createGameAndUpdateMaps(String gameId) {
        BlockingQueue<Message> gameMessageQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Message> gameResponseQueue = new LinkedBlockingQueue<>();

        GameThreadCommunication gameThreadCommunication = new GameThreadCommunication(gameMessageQueue, gameResponseQueue);

        GameThread game = new GameThread(gameThreadCommunication,  myPlayerId, currentOpponentPlayerId, gameId);

        getGameFromGameId.put(gameId, game);
        getGameThreadCommunicationFromId.put(gameId, gameThreadCommunication);

        game.start();
    }

    private static boolean gameHasNotBeenCreated(String gameId) {
        return !getGameFromGameId.containsKey(gameId);
    }

    private static void addEmptyStringToServerQueue() {
        stringsToServerQueue.add("");
    }

    private static boolean messageIsEmpty(Message message) {
        return message == Message.NO_ACTION;
    }


}
