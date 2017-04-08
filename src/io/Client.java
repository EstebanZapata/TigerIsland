package io;

import thread.InformationMessage;
import thread.Message;

import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;

public class Client extends Thread {
    private String hostName;
    private int portNumber;
    private BlockingQueue<Message> messagesFromServerQueue;
    private BlockingQueue<Message> messagesToServerQueue;


    public Client(String hostName, int portNumber,
                  BlockingQueue<Message> messagesFromServerQueue, BlockingQueue<Message> messagesToServerQueue) throws IOException {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.messagesFromServerQueue = messagesFromServerQueue;
        this.messagesToServerQueue = messagesToServerQueue;
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

                InformationMessage messageFromServer = new InformationMessage(stringFromServer);

                messagesFromServerQueue.add(messageFromServer);

                InformationMessage responseToServer = waitForResponseToServer();

                System.out.println("Client: " + responseToServer.getInformation());
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

    private InformationMessage waitForResponseToServer() {
        while(true) {
            InformationMessage responseToServer = null;

            try {
                responseToServer = (InformationMessage) messagesToServerQueue.take();
            }
            catch(InterruptedException e) {
            }

            if (responseToServer != null) {
                return responseToServer;
            }
        }


    }


}
