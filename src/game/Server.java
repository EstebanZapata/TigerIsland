package game;
import game.Protocol;

import java.net.*;
import java.io.*;

public class Server {
    public static
    void main(String[] args) throws IOException {
        int startup = 0;
        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
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
            //Startup Loop. Sends messages without client input
            while (startup<5) {
                outputLine = kkp.processInput(null);
                out.println(outputLine);
                startup++;
            }
            //Client feedback loop
            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
