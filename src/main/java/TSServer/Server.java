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
                byte[] response = new byte[];

                if(!(input.length == 0)){
                    switch (input[0]) {
                        case 0: game = new Game();
                                response[0] = 0;
                                packet.write(response, clntSock);
                                break;
                        case 1: response[0] = 1;
                                packet.write(response, clntSock);
                                break;
                        case 2: if(game.addPiece(input[1], input[2], Slot.HUMAN_MOVE));
                                game.addPiece(input[1], input[2], Slot.HUMAN_MOVE);
                                response[0] = 2;
                                //set response[1]&[2] to the server row and column
                                packet.write(response, clntSock);
                                break;
                        case 3:
                    }
                }
            }
            // Close the socket. This client is finished.
            clntSock.close();
        }
    /* NOT REACHED */
    }
}