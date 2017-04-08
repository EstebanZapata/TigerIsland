package thread;

import thread.message.Message;

import java.util.concurrent.BlockingQueue;

public class GameThreadCommunication {
    private BlockingQueue<Message> gameMessageQueue;
    private BlockingQueue<Message> gameResponseQueue;

    public GameThreadCommunication(BlockingQueue<Message> gameMessageQueue, BlockingQueue<Message> gameResponseQueue) {
        this.gameMessageQueue = gameMessageQueue;
        this.gameResponseQueue = gameResponseQueue;
    }

    public BlockingQueue<Message> getGameMessageQueue() {
        return gameMessageQueue;
    }

    public BlockingQueue<Message> getGameResponseQueue() {
        return gameResponseQueue;
    }
}
