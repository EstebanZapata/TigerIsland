import game.Game;
import tile.Terrain;
import tile.Tile;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Start {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<GameMessage> gameOneMessageQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Message> gameOneResponseQueue = new LinkedBlockingQueue<>();

        GameThread gameOne = new GameThread(gameOneMessageQueue, gameOneResponseQueue, "1");

        gameOne.start();

        Thread.sleep(1000);

        gameOneMessageQueue.add(new GameMessage("1", 2.0, new Tile(Terrain.GRASSLANDS, Terrain.LAKE)));

        while(true) {
            Message msg = null;

            msg = gameOneResponseQueue.take();

            if (msg != null) {
                System.out.println("Server received mesage");
            }
        }

    }


}
