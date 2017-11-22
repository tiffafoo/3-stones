package TSServer;

import java.io.IOException;
import java.net.Socket;

/**
 *
 *
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 */
public class ThreeStonesThread implements Runnable {

    private Socket clntSocket;

    public ThreeStonesThread(Socket clntSocket) {
        this.clntSocket = clntSocket;
    }

    @Override
    /**
     *
     */
    public void run() {
        try {
            Session session = new Session();
            session.playSession(clntSocket);
        } catch (IOException e) {
            System.out.println("Exception = " + e.getMessage());
        }
    }

}
