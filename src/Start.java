import io.Client;
import io.MessageParser;
import thread.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Start {
    private static BlockingQueue<Message> messagesFromServerQueue;
    private static BlockingQueue<Message> messagesToServerQueue;

    public static void main(String[] args) throws Exception {
        String playerId;

        MessageParser messageParser = new MessageParser();

        messagesFromServerQueue = new LinkedBlockingQueue<>();
        messagesToServerQueue = new LinkedBlockingQueue<>();

        Client client = new Client("DESKTOP-6EG7I90", 9999, messagesFromServerQueue, messagesToServerQueue);
        client.start();

        Message messageFromServer = waitForMessageFromServer();
/*
        BlockingQueue<Message> gameOneMessageQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Message> gameOneResponseQueue = new LinkedBlockingQueue<>();

        GameThread gameOne = new GameThread(gameOneMessageQueue, gameOneResponseQueue, "1");

        gameOne.start();

        GameCommandMessage debug = new GameCommandMessage("1", 5.0, new Tile(Terrain.GRASSLANDS, Terrain.LAKE));
        gameOneMessageQueue.add(client.parser.commandMessage);

        while(true) {
            Message msg = null;

            msg = gameOneResponseQueue.take();

            if (msg != null) {
                System.out.println("io.Server received mesage");
            }
        }
*/
    }

    private static Message waitForMessageFromServer() {
        while(true) {
            Message messageFromServer = null;

            try {
                messageFromServer = messagesFromServerQueue.take();
            }
            catch (InterruptedException e) {
            }

            if (messageFromServer != null) {
                System.out.println("Message from server!");

                return messageFromServer;
            }
        }
    }




}
