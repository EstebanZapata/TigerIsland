package io;

import java.net.*;
import java.io.*;

public class Server extends Thread {
    private int portNumber;

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    public static void main(String[] args) throws IOException {
        int startup = 0;
        if (args.length != 1) {
            System.err.println("Usage: java io.Server <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);

            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {

            String inputLine, outputLine;
            // Initiate conversation with client
            Protocol kkp = new Protocol();
            //Startup condition. Sends message without client input
            if (startup<1) {
                outputLine = kkp.processInput(null);
                out.println(outputLine);
               startup++;
            }
            //io.Client feedback loop
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client: " + inputLine);


                if (inputLine.contains("I AM")) {

                    for (int i = 0; i < 5; i++) {
                        outputLine = kkp.processInput(inputLine);
                        out.println(outputLine);
                        System.out.println("Server: " + outputLine);
                    }
                }
                if (inputLine.contains("GAME") && inputLine.contains("MOVE")) {
                    outputLine = kkp.processInput(inputLine);
                    out.println(outputLine);
                    System.out.println("Server: " + outputLine);

                    outputLine = kkp.processInput(inputLine);
                    out.println(outputLine);
                    System.out.println("Server: " + outputLine);
                }
                else {
                    outputLine = kkp.processInput(inputLine);
                    out.println(outputLine);
                    System.out.println("Server: " + outputLine);
                }

            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            main(new String[]{"9999"});
        }
        catch (Exception e) {

        }
    }

    public int getPortNumber() {
        return this.portNumber;
    }
}
