package io;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientTest {
    private Client client;
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

        client = new Client("DESKTOP-6EG7I90", port, stringsFromServerQueue, stringsToServerQueue);
    }

    @Test
    public void testClientCanReceiveStringFromServer() throws Exception {
        Assert.assertTrue(stringsFromServerQueue.isEmpty());

        client.start();

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
