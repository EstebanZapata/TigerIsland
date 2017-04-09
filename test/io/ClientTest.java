package io;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientTest {
    private Client client;
    private Client secondClient;
    private Server server;

    private BlockingQueue<String> stringsFromServerQueue;
    private BlockingQueue<String> stringsToServerQueue;

    private int port = 9999;

    @Before
    public void setup() {
        server = new Server(port);
        server.start();

        stringsFromServerQueue = new LinkedBlockingQueue<>();
        stringsToServerQueue = new LinkedBlockingQueue<>();

        client = new Client("Alix", port, stringsFromServerQueue, stringsToServerQueue);
        secondClient = new Client("Alix", port-1, stringsFromServerQueue, stringsToServerQueue);
    }

    @Test
    public void testClientCanReceiveStringFromServer() throws Exception {
        Assert.assertTrue(stringsFromServerQueue.isEmpty());

        secondClient.start();

        Thread.sleep(100);
        Assert.assertFalse(stringsFromServerQueue.isEmpty());

    }

    @Test
    public void testClientCanSendStringToServer() throws Exception {
        String stringToServer = "Spaghetti";

        Assert.assertTrue(stringsToServerQueue.isEmpty());
        stringsToServerQueue.add(stringToServer);

        Assert.assertFalse(stringsToServerQueue.isEmpty());

        client.start();

        Thread.sleep(100);

        Assert.assertTrue(stringsToServerQueue.isEmpty());

    }


    @After
    public void teardown() throws Exception{
        server = null;
        client = null;
    }
}
