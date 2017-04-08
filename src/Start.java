import io.Client;
import io.MessageParser;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Start {
    private static BlockingQueue<String> stringsFromServerQueue;
    private static BlockingQueue<String> stringsToServerQueue;

    public static void main(String[] args) throws Exception {
        stringsFromServerQueue = new LinkedBlockingQueue<>();
        stringsToServerQueue = new LinkedBlockingQueue<>();

        Client client = new Client("DESKTOP-6EG7I90", 9999, stringsFromServerQueue, stringsToServerQueue);
        client.start();

        while(true) {
            runTournamentLoop();
        }

    }

    private static void runTournamentLoop() {
        String stringFromServer = waitForMessageFromServer();
        String actionToTake = MessageParser.parseServerInputAndComposeAction(stringFromServer);
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
                System.out.println("Message from server!");

                return stringFromServer;
            }
        }
    }




}
