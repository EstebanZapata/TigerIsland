package io;

import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;

public class Client extends Thread {
    private String hostName;
    private int portNumber;
    private BlockingQueue<String> stringsFromServerQueue;
    private BlockingQueue<String> stringsToServerQueue;

    public Client(String hostName, int portNumber,
                  BlockingQueue<String> stringsFromServerQueue, BlockingQueue<String> stringsToServerQueue) throws IOException {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.stringsFromServerQueue = stringsFromServerQueue;
        this.stringsToServerQueue = stringsToServerQueue;
    }

    @Override
    public void run() {
        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        ) {
            String stringFromServer;
            while ((stringFromServer = in.readLine()) != null) {
                System.out.println("Server: " + stringFromServer);

                stringsFromServerQueue.add(stringFromServer);

                String responseToServer = waitForResponseToServer();
                if (responseToServer.equals("")) {
                    continue;
                }

                System.out.println("Client: " + responseToServer);
                out.println(responseToServer);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host STRANGER DANGER" + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

    private String waitForResponseToServer() {
        while(true) {
            String responseToServer = null;

            try {
                responseToServer = stringsToServerQueue.take();
            }
            catch(InterruptedException e) {
            }

            if (responseToServer != null) {
                return responseToServer;
            }
        }


    }


}
