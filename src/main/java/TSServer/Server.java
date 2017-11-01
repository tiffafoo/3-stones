package TSServer;

import TSServer.game.Game;
import TSServer.game.Slot;

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
        Packet packet = new Packet();
        Game game = new Game();

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
            // TODO: Should while conditional be checking that the opcode is not -1? -> Handle in switch
            while ((recvMsgSize = in.read(byteBuffer)) != -1) {
                byte[] input = packet.read(clntSock);

                if(!(input.length == 0)){
                    switch (input[0]) {
                        case -1: continue;

                        case 0: game = new Game();
                                break;
                        case 1: //End game logic
                                break;
                        case 2: game.addPiece(input[1], input[2], Slot.HUMAN_MOVE);
                                //return a message indicating that it worked or not
                                break;
                        case 3: game.
                    }
                }
            }
            // Close the socket. This client is finished.
            clntSock.close();
        }
    /* NOT REACHED */
    }
}