package TSServer;

import TSServer.game.Game;
import TSServer.game.Slot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Given by teacher
public class Server
{
    private static final int BUFSIZE = 32;	// Size of receive buffer

    public static void main(String[] args) throws IOException
    {
        int servPort = 50000;

        // Create a server socket to accept client connection requests
        ServerSocket servSock = new ServerSocket(servPort);
        Packet packet = new Packet();
        Game game = new Game();
        Boolean keepPlaying = true;

        // Run forever, accepting and servicing connections
        for (;;)
        {
            // Get client connection
            Socket clntSock = servSock.accept();

            // TODO: TSServer.playSessions;

            System.out.println("Handling client at " +
                    clntSock.getInetAddress().getHostAddress() + " on port " +
                    clntSock.getPort());


            // Receive until client closes connection, indicated by -1 return
            // TODO: Should while conditional be checking that the opcode is not -1? -> Handle in switch
            while (keepPlaying) {
                byte[] input = packet.read(clntSock);
                byte[] response = new byte[10];

                if(!(input.length == 0)){
                    switch (input[0]) {
                        case 0: game = new Game();
                            response[0] = 0;
                            packet.write(response, clntSock);
                            break;
                        case 1: response[0] = 1;
                            response[1] = game.getPlayerPoints();
                            response[2] = game.getCompPoints();
                            response[3] = 0;
                            response[4] = 0;
                            packet.write(response, clntSock);
                            break;
                        case 2: if(game.addPiece(input[1], input[2], Slot.HUMAN_MOVE)) {
                            game.addPiece(input[1], input[2], Slot.HUMAN_MOVE);
                            response[0] = 2;
                            byte[] move = game.getNextMove();
                            response[1] = move[0];
                            response[2] = move[1];
                            response[3] = input[1];
                            response[4] = input[2];
                            packet.write(response, clntSock);
                            }
                            else {
                            response[0] = 4;
                            response[1] = 0;
                            response[2] = 0;
                            response[3] = 0;
                            response[4] = 0;
                            packet.write(response, clntSock);
                        }
                            break;
                        case 3:
                            response[0] = 3;
                            response[1] = 0;
                            response[2] = 0;
                            response[3] = 0;
                            response[4] = 0;
                            keepPlaying = false;
                            break;
                    }
                }
            }
            // Close the socket. This client is finished.
            clntSock.close();
        }
    /* NOT REACHED */
    }
}