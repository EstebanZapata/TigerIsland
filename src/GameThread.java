import game.Game;
import tile.Terrain;
import tile.Tile;

import java.util.concurrent.BlockingQueue;

public class GameThread extends Thread {
    private BlockingQueue<GameMessage> gameMessageQueue;
    private BlockingQueue<Message> gameResponseQueue;

    private String gameId;

    private Game game;

    GameThread(BlockingQueue<GameMessage> gameMessageQueue, BlockingQueue<Message> gameResponseQueue, String gameId) {
        super();

        this.gameMessageQueue = gameMessageQueue;
        this.gameResponseQueue = gameResponseQueue;

        this.gameId = gameId;

        this.game = new Game();
    }

    @Override
    public void run()  {
        while(true) {
            Message msg = null;
            try {
                msg = gameMessageQueue.take();
            }
            catch(Exception e) {

            }

            if(msg != null) {
                System.out.println("Game received message");

                try {
                    Thread.sleep(2000);
                }
                catch (Exception e) {

                }

                gameResponseQueue.add(new GameMessage("fs", 2.0, new Tile(Terrain.GRASSLANDS,Terrain.LAKE)));


            }
        }
    }

    public String getGameId() {
        return this.gameId;
    }


}
