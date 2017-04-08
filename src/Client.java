import java.io.*;
import java.net.*;

public class Client {
    private static MessageParser parser = new MessageParser();
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            String fromServer;
            while ((fromServer = in.readLine()) != null) {
                String clientResponse = parser.parseServerInput(fromServer);
                if (!clientResponse.equals("WAITING")) {
                    System.out.println(clientResponse);
                    out.println(clientResponse);
                }
                else
                    out.println();
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
}
