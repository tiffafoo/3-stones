package TSServer;

import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// Given by teacher
public class Server
{
    private static final int BUFSIZE = 32;	// Size of receive buffer

    public static void main(String[] args) throws IOException
    {

        if (args.length != 1)					// Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int servPort = Integer.parseInt(args[0]);

        // Create a server socket to accept client connection requests
        ServerSocket servSock = new ServerSocket(servPort);

        // Size of received message
        int recvMsgSize;
        byte[] byteBuffer = new byte[BUFSIZE];	// Receive buffer

        // Run forever, accepting and servicing connections
        for (;;)
        {
            // Get client connection
            Socket clntSock = servSock.accept();

            // TODO: TSServer.playSessions;

            System.out.println("Handling client at " +
                    clntSock.getInetAddress().getHostAddress() + " on port " +
                    clntSock.getPort());

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            // Receive until client closes connection, indicated by -1 return
            while ((recvMsgSize = in.read(byteBuffer)) != -1) {
                out.write(byteBuffer, 0, recvMsgSize);
            }
            // Close the socket. This client is finished.
            clntSock.close();
        }
    /* NOT REACHED */
    }
}