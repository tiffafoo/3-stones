package TSServer;

import java.io.IOException;

/**
 * 
 *
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 */
public class ServerApp
{
    public static void main(String[] args) throws IOException
    {
        Server server = new Server();
        server.runServer();        
    }
}
       
