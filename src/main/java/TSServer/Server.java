package TSServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 */
public class Server {

    private final int servPort = 50000;

    public void runServer() throws IOException {
        // Create a server socket to accept client connection requests
        ServerSocket servSock = new ServerSocket(servPort);
        System.out.println("Server has been started!");

        Session session = new Session();

        // Run forever, accepting and servicing connections
        for (;;) {
            try {
                // Get client connection
                Socket clntSock = servSock.accept();

                ThreeStonesThread stoneThread = new ThreeStonesThread(clntSock);
                Thread thread = new Thread(stoneThread);
                thread.start();

                System.out.println("Created and started Thread = " + thread.getName());
            } catch (IOException e) {
                System.out.println("Exception = " + e.getMessage());
            }
        }
    }
}
